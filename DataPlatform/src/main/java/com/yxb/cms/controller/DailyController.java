package com.yxb.cms.controller;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.DailyReport;
import com.yxb.cms.model.GeneralList;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日报列表/下载 Controller
 * @author zd
 * @date 2018/06/10
 *
 */
@RestController
@RequestMapping("dailydownload")
public class DailyController extends BasicController {
    /**
     *  日报下载列表
     * @param request 请求body中包含
     *  "startTime":string,--------------“2018-06-05”
     *  "endTime":string,---------------”2018-06-06“
     *  "page":num,---------------------2
     *  "pageSize":num,---------------10
     * @return
     * total:符合查询条件的总数据条数
     * rows:符合查询条件，并且符合分页条件的日报数组，例如第11-20条数据
     */
    @RequestMapping("/getDailyList")
    @ResponseBody
    @SystemControllerLog(description="日报下载")
    public BussinessMsg dailyList(HttpServletRequest request, @RequestBody Map<String,String> body) throws IOException {
        log.info("请求日报列表");
        Subject currentUser = SecurityUtils.getSubject();


        if(!currentUser.isAuthenticated()){
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else {
            String order = body.get("sortOrder");
            if(order==null)
                order = "descend";
            String search = "";
            if(body.get("fileName")!=null){
                search=(String)body.get("fileName");
            }
            //get the authorize map 调用python脚本，获得日报下载权限列表
            String email = getCurrentLoginName();
            Process p = null;
            try {
                p = Runtime.getRuntime().exec("python /home/hdfs/zuodong/dp/authorized.py");
                p.waitFor();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            File authorized = new File("authorized");
            String reportsMap = " ";
            if(authorized.exists()){
                BufferedReader br = new BufferedReader(new FileReader(authorized));
                String st;
                while ((st = br.readLine()) != null){
                    if(st.startsWith(email)){
                        reportsMap = st;
                        break;
                    }
                }
            }
            String[] permissions = new String[0];
            if(!reportsMap.equals(" "))
                permissions = reportsMap.substring(reportsMap.indexOf(":")+1).trim().split(",");
            String startTime = body.get("startTime");
            if(startTime==null|| startTime.equals(""))
                startTime="00000000";
            else
                startTime=startTime.replace("-","");
            String endTime =  body.get("endTime");
            if(endTime==null|| endTime.equals(""))
                endTime="99999999";
            else
                endTime=endTime.replace("-","");

            List<Object> arr = new ArrayList<>();
            int total =0;
            Object[] rows = new Object[0];

            File folder = new File("/tmp/generated_report"); //for production
            log.info("path"+folder.getAbsolutePath());
            //for test
//            File folder = new File("./logs"); //for test, to be changed
            File[] listOfFiles = folder.listFiles();
            log.info("folder:"+folder);
            //get daily report list from the local filesystem
            if(listOfFiles!=null){
                for (File curFile : listOfFiles) {
                    //检查用户是否有下载此日报的权限
                    String fileName = curFile.getName();
                    boolean contain = false;
                    for(String permission : permissions){
                        if(fileName.startsWith(permission)&&fileName.contains(search))
                            contain=true;
                    }
                    if(!contain)
                        continue;
                    if (curFile.isFile()) {
                        //filter by start time / end time
                        String fileTime = fileName.
                                substring(fileName.lastIndexOf(" ")+1,fileName.lastIndexOf("."));
                        if(fileTime.compareTo(endTime)>0||fileTime.compareTo(startTime)<0){
                            //compare string is actually compare date when the format is same
                            continue;
                        }
                        arr.add(new DailyReport());

                        //cast object to DailyReport
                        DailyReport dp = (DailyReport) arr.get(arr.size() - 1);
                        dp.setFileName(curFile.getName());
                        //get last modified time as "uploadTime"
                        long time = curFile.lastModified();
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cal.setTimeInMillis(time);
                        dp.setUploadTime(formatter.format(cal.getTime()));
                        //get owner of the file
                        try {
                            String owner = Files.getOwner(curFile.toPath()).toString();
                            dp.setOwner(owner);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //get filepath (url) to downloadhttp:
                        String url = "http://peppa.youxin.com/dailydownload/downloadFile?filename="+curFile.getName();
                        //for test
//                        String url = "//10.10.21.76:8087/dailydownload/downloadFile?filename="+curFile.getName();
                        dp.setFilePath(url);
                        dp.setKey(curFile.getName());
//                        System.out.println("File: " + curFile.getName());
                    } else if (curFile.isDirectory()) {
                        log.info("Not file but a directory: " + curFile.getName());
                    }
                }
                total = arr.size();
                if(order.equals("ascend"))
                    arr.sort(Comparator.comparing((Object o) -> ((DailyReport) o).getUploadTime()));
                else{
                    arr.sort((Object a,Object b)->((DailyReport) b).getUploadTime().compareTo(((DailyReport) a).getUploadTime()));
                }
                //filter data using paging information

                int page = Integer.parseInt(body.get("page"));
                int pageSize = Integer.parseInt(body.get("pageSize"));
                if (page==0)
                    page = 1;
                int from = (page-1)*pageSize;
                if(from>=arr.size()&&arr.size()>0){
                    //when out of bound ,return to last page
                    from = ((arr.size()-1)/pageSize)*pageSize;
                }
                int to = Math.min(from+pageSize,arr.size());
                if(arr.size()!=0)
                    arr = arr.subList(from,to);
                rows = arr.toArray(new Object[0]);
            }
            //create a general return type
            GeneralList gl = new GeneralList(total,rows,order);
            return  BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DAILY_SUCCESS,gl);
        }
    }


    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        log.info("请求下载日报");
        Subject currentUser = SecurityUtils.getSubject();
//        if(!currentUser.isAuthenticated()){
//            log.info("用户未登录,下载失败");
//        }else{
        String targetFile = request.getParameter("filename");
        log.info("用户请求下载 ：" + targetFile);
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
                File file = new File("/tmp/generated_report/"+targetFile);
            //for test
//            File file = new File("./logs/" + targetFile);
            log.info("获取本地文件：" + file.getName());
            fis = new FileInputStream(file);
            String fileName = URLEncoder.encode(file.getName(), "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//    }
    }
}
