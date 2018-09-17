package com.yxb.cms.controller;

import com.yxb.cms.architect.annotation.SystemControllerLog;
import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.BacktrackResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("dataBack")
public class BacktrackController extends BasicController {

    @RequestMapping("/postDataBackLogList")
    @ResponseBody
    @SystemControllerLog(description="数据回溯")
    public BussinessMsg backtrackData(HttpServletRequest request, @RequestBody Map<String,String> body) {
        log.info("请求数据回溯");
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else {
            String database = body.get("database");//currently useless
            String startTime = body.get("startTime");
            String endTime = body.get("endTime");
            String tableName = body.get("tableOption");
            if(startTime==null||tableName==null){
                log.info("用户请求参数缺失");
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
            }
            else{
                BussinessMsg res = backtrack(startTime,endTime,tableName,database);
                return res;
            }
        }

    }


    private BussinessMsg backtrack(String startTime, String endTime, String tableName, String database) {
        String marker = "/tmp/backtrack/hive_" + tableName+"#"+startTime+"#"+endTime;

        File f = new File(marker);
        log.info("marker path:" + f.getAbsoluteFile());
        String logFileName = "/tmp/backtrack/hive_" + tableName +"#"+startTime+"#"+endTime+ ".log";
        File logFile = new File(logFileName);
        //calculate total days
        long totalDays = 1;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startdate = myFormat.parse(startTime);
            Date enddate = myFormat.parse(endTime);
            long diff = enddate.getTime() - startdate.getTime();
            totalDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("请求回溯日期:" + startTime + "to" + endTime + ", 总天数：" + totalDays);

        if (f.exists() && !f.isDirectory()) {
            // marker文件存在，说明此表正在被操作，只需返回percent和date list
            log.info("marker 存在，返回log");
            List<String> finishedDays = new ArrayList<>();
            List<String> failureDay = new ArrayList<>(); //虽然是List，但是实际上只有一天，就是失败开始的那天。
            int percent = 0;
            if (logFile.exists()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(logFile));
                    String st;
                    while ((st = br.readLine()) != null) {

//                        log.info("hive log:::::" + st);
                        //记录下完成回溯的日期
                        if (st.contains("-----执行结束 ")) {
                            String Day = st.trim().split(" ")[1].trim();

                            finishedDays.add(Day);
                            percent = (int) ((finishedDays.size() * 100) / totalDays);
                            if (percent > 100)
                                percent = 100;
                            if (Day.equals(endTime)) {
                                percent = 100;
                            }
                        }
                        if (st.contains("return code: 1")) {//script failed
                            String failday = startTime;
                            if (finishedDays.size() > 0) {
                                String[] lastDay = finishedDays.get(finishedDays.size() - 1).split("-");
                                StringBuilder strDay = new StringBuilder();
                                strDay.append(lastDay[0]).append("-").append(lastDay[1]).append("-").append(Integer.parseInt(lastDay[2]) + 1);
                                failday = strDay.toString();
                            }
                            failureDay.add(failday);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //remove marker file
            boolean deleted = false;
            if (percent == 100 && f.exists()){
                deleted = f.delete();
                if(logFile.exists())
                    logFile.delete();
                log.info("delete marker file:" + f.getName() + "successfully deleted:" + deleted);
            }
            BacktrackResult br = new BacktrackResult(finishedDays, failureDay, percent);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_BACK_SUCCESS, br);
        } else {
            // marker文件不存在，说明是首次操作数据库，需要执行hive脚本
            // 先建立markerfile
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            try {
                boolean created = f.createNewFile();
                log.info("create marker file:" + f.getName() + "created:" + created);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //执行bash 脚本
            boolean deleted=false;
            if(logFile.exists()){
                deleted = logFile.delete();
                log.info("delete log file:" + logFile.getName() + "successfully deleted:" + deleted);
            }
            //get the folder name 18.xxx
            String parentFolder = "/software/servers/azkaban-executor-2.5.0/bin/projects";
            File folder = new File(parentFolder);
            File[] listOfFiles = folder.listFiles();
            double max18 = 18.0;
            String folder18 = "";
            if(listOfFiles!=null) {
                for (File curFile : listOfFiles) {
                    if (curFile.isDirectory()&&curFile.getName().startsWith("18.")) {
                        if(Double.parseDouble(curFile.getName())>=max18){
                            folder18 = curFile.getName();
                            max18 = Double.parseDouble(curFile.getName());
                        }
                    }
                }
            }

//            ProcessBuilder pb = new ProcessBuilder("sh", "data_back.sh", "-y 2018", "-m 06","-s 01","-e 14","-t report_daily_sales_summary" ,"-d "+parentFolder+"/"+folder18+"/pipeline/script/query");
            ProcessBuilder pb = new ProcessBuilder("sh", "/home/hdfs/zuodong/dp/data_back_new.sh", "-s "+startTime,"-e "+endTime,"-t "+tableName ,"-p "+parentFolder+"/"+folder18+"/pipeline/script/query","-d "+database);

            pb.redirectErrorStream(true);
            pb.redirectOutput(logFile); //direct log to a file
            Process ps = null;
            try {
                ps = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("脚本执行");
            BacktrackResult br = new BacktrackResult(new ArrayList<>(), new ArrayList<>(), 0);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_BACK_SUCCESS, br);
        }
    }
    @RequestMapping("/getTableListOption")
    @ResponseBody
    @SystemControllerLog(description="获取Hive数据库表名列表")
    public BussinessMsg tableList(HttpServletRequest request, @RequestBody Map<String,String> body) {
        String database = body.get("database");//currently useless
        String driverName = "org.apache.hive.jdbc.HiveDriver";
        List<String> tables = new ArrayList<>();
        tables.add("请选择表");
//        tables.add("test");
        try {
             Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if(database==null)
                database="default";
            Connection con = DriverManager.getConnection("jdbc:hive2://ZW0804-hadoop-96:10000/"+database, "hdfs", "hdfs");
            Statement stmt = con.createStatement();
            String sql = "show tables";
            log.info("Running sql: " + sql);
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()) {
//                    log.info(res.getString(1));
                    tables.add(res.getString(1).trim());
            }
        } catch (SQLException e) {
            log.info("connection to hive failed");
            e.printStackTrace();
        }
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_LIST_SUCCESS, tables);
    }

}
