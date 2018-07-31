package com.yxb.cms.controller;

import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.StatusResult;
import com.yxb.cms.service.DatabaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MySQL  <-> Hive 操作
 * @author zd
 * @date 2018/06/19
 *
 */
@RestController
@RequestMapping("dataDemand")
public class DatabaseController extends BasicController {

    /**
     * 数据库相关操作
     * 1.数据库之间操作，MySQL 通过Sqoop导入到Hive表中。  selectWay:change
     * 2.下载数据库中的表                              selectWay:download
     * 3.上传表到数据库中                              selectWay:upload
     */


    @RequestMapping("/getMySQLDatabaseList")
    public BussinessMsg getDatabases(HttpServletRequest request) {
        log.info("请求mysql数据库列表");
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录,请求失败");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else{
            DatabaseService ds= new DatabaseService();
            List<String> res = ds.createTableList();
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,res);
        }
    }

    @RequestMapping("/uploadDataDemand")
    public BussinessMsg uploadOperation(@RequestParam("files") MultipartFile file,
                                          HttpServletRequest request) {
        log.info("请求操作数据库");
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录,请求失败");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else{

            String operation = request.getParameter("selectWay");
            if(operation.equals("upload")){ //upload to Hive
                String tableName =file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf('.'));
                String log_file = "/tmp/dp/upload/log/"+tableName;
                String status = "loading";
                //
                String AbsPath = "/tmp/dp/upload/"+file.getOriginalFilename();
                File downloadFile = new File(AbsPath);
                File logFile = new File(log_file);
                if(downloadFile.exists()){
                    log.info("marker file exists, just return log");
                    StringBuilder sb = new StringBuilder();
                    if(logFile.exists()){
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new FileReader(logFile));
                            String st;
                            while ((st = br.readLine()) != null){
                                sb.append(st).append("\n");
                                if(st.contains("upload_task finished"))
                                    status = "end";
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String returnLog = sb.toString();
                    //remove marker file
                    boolean deleted = false;
                    if(status.equals("end")&&downloadFile.exists())
                        deleted = downloadFile.delete();
                    StatusResult sr = new StatusResult(status,returnLog,null);
                    return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
                }
                log.info("marker 不存在，接收上传文件并保存到服务器。");
                String db = request.getParameter("database");
                if (file.isEmpty()) {
                    log.info("upload file is empty");
                    return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_UP_ERROR);
                }
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(AbsPath);//AbsPath
                    log.info("store path:"+path.toString());
                    Files.write(path, bytes);//path
                    log.info("upload to file system successfully");
                    if(db.equals("HIVE")){
                        DatabaseService ds = new DatabaseService();
                        return ds.uploadFileToHive("tmp", AbsPath,log_file,tableName,log);
                    } else   //目前不支持upload到mysql数据库，因为不确定建表时字段的长度。
                        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
                } catch (IOException e) {
                    e.printStackTrace();
                    return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_UP_ERROR);
                }
            }
            return null;
        }
    }

    @RequestMapping("/postDataDemand")
    public BussinessMsg databaseOperation(HttpServletRequest request) {
        log.info("请求操作数据库");
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            log.info("用户未登录,请求失败");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_NOT_LOGIN);
        }else {

            String operation = request.getParameter("selectWay");
            if (operation.equals("change")) {//MySQL -> Hive
                String MysqlDB = request.getParameter("databaseName");
                String MysqlTable = request.getParameter("fileName");

                if (MysqlDB == null || MysqlTable == null) {
                    log.info("用户请求参数缺失");
                    return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_DB_ERROR);
                } else {
                    DatabaseService ds = new DatabaseService();
                    BussinessMsg res = ds.importMySQLtoHive(MysqlDB, MysqlTable,log);
                    return res;
                }
            } else if (operation.equals("download")) {
                String platform = request.getParameter("database");
                String db = request.getParameter("databaseName");
                String table = request.getParameter("fileName");
                String part = request.getParameter("part");
                if (platform.equals("HIVE")) {
                    try {
                        DatabaseService ds = new DatabaseService();
                        return ds.downloadFromHive(db, table, part,log);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(platform.equals("MySQL")){
                    try {
                        DatabaseService ds = new DatabaseService();
                        return ds.downloadFromMySQL(db, table,log);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            return null;
        }
    }

    @RequestMapping("/getHIVEPartList")
    public BussinessMsg getParititions(@RequestBody Map<String,String> body) {
        String db = body.get("databaseName");
        String table = body.get("fileName");
        String driverName = "org.apache.hive.jdbc.HiveDriver";
        Connection con = null;
        List<String> partitions = new ArrayList<>();
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:hive2://ZW0804-hadoop-96:10000/"+db, "hdfs", "hdfs");
            Statement stmt = con.createStatement();
            String sql = "show partitions "+table;
            log.info("Running sql: " + sql);
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()) {
                partitions.add(res.getString(1).trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,partitions);

    }


    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        log.info("请求下载csv");
        String targetFile = request.getParameter("filename");
        log.info("用户请求下载 ：" + targetFile);
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            File file = new File("/tmp/dp/download/result/"+targetFile);
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
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
