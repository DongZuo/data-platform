package com.yxb.cms.service;

import com.yxb.cms.architect.constant.BussinessCode;
import com.yxb.cms.architect.utils.BussinessMsgUtil;
import com.yxb.cms.domain.bo.BussinessMsg;
import com.yxb.cms.model.StatusResult;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {
    public List<String> createTableList(){
        List<String> res = new ArrayList<>();
        res.add("renrendai_0513");res.add("data_bi");res.add("reports");res.add("prodsjlc");
        res.add("rts");res.add("test");res.add("rule_engine");res.add("message_center");
        res.add("marketing");
        return res;
    }
    public BussinessMsg downloadFromHive(String db, String table, String part, Logger log) throws IOException {
        boolean tableNotExist = false;
        if (part == null||part.equals("全部"))
            part = "";
        //this is a folder!!! We need to merge all files in this folder;
        String filePath = "/tmp/dp/download/hive/"+db+"_"+table+"_"+part;
        String log_file = "/tmp/dp/download/hivelog/"+db+"_"+table+"_"+part;
        String csv_file = "/tmp/dp/download/result/"+db+"_"+table+"_"+part.replace("=","")+".csv";
        String status = "loading";
        File logFile = new File(log_file);
        File dir = new File(filePath);
        //if dir exists, just return log
        if(logFile.exists()) {//||dir.exists()
            log.info("marker file exists, just return log");
            StringBuilder sb = new StringBuilder();
            if (logFile.exists()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(logFile));
                    String st;
                    while ((st = br.readLine()) != null) {

                        sb.append(st).append("\n");
                        if(st.contains("Table not found"))
                            tableNotExist = true;
                        if (st.contains("download_task finished"))
                            status = "end";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String url = "";
            //merge files in folder, delete folder and return download url
            File csv = new File(csv_file);
            if(status.equals("end")) {
                logFile.delete();
                if(!tableNotExist)
                    url = "http://peppa.youxin.com/dataDemand/downloadFile?filename="+db+"_"+table+"_"+part.replace("=","")+".csv";
            }
            String returnLog = sb.toString();
            StatusResult sr = new StatusResult(status,returnLog,url);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
        }
        //第一次请求，执行hive load命令，导出hive数据到文件夹

        String downloadCmd = "";
        if (part.equals(""))
            downloadCmd = "insert overwrite local directory '" + filePath +
                    "' row format delimited fields terminated by ',' select * from " + db + "." + table ;
        else if(part.split("=").length == 2){
            downloadCmd = "insert overwrite local directory '" + filePath +
                    "' row format delimited fields terminated by ',' select * from " + db + "." + table + " where " +
                    table + "." + part.split("=")[0] + "='" + part.split("=")[1] + "'";
        }
        log.info("hive command" + downloadCmd);
        //执行脚本，把db，command传进去，并建立log文件。
        //执行bash 脚本
        ProcessBuilder pb = new ProcessBuilder("sh","/home/hdfs/zuodong/dp/download_hive.sh",downloadCmd,log_file,filePath,csv_file);
        pb.redirectErrorStream(true);
        Process ps = null;
        try {
            ps = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return empty log, and status "loading"
        log.info("脚本执行");
        StatusResult sr = new StatusResult("loading","",null);
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
    }
    public BussinessMsg downloadFromMySQL(String db, String table,Logger log) throws IOException {

        String filePath = "/tmp/dp/download/result/"+db+"_"+table+".csv";
        String log_file = "/tmp/dp/download/mysqllog/"+db+"_"+table;
        String status = "loading";
        File logFile = new File(log_file);
        File f = new File(filePath);
        boolean tableNotExist = false;
        //if dir exists, just return log
        if(logFile.exists()) {
            log.info("marker file exists, just return log");
            StringBuilder sb = new StringBuilder();
            if (logFile.exists()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(logFile));
                    String st;
                    while ((st = br.readLine()) != null) {
                        sb.append(st).append("\n");
                        if(st.contains("doesn't exist"))
                            tableNotExist = true;
                        if (st.contains("download_task finished"))
                            status = "end";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String url = "";
            //merge files in folder, delete folder and return download url
            if(status.equals("end")) {
                logFile.delete();
                if(!tableNotExist)
                    url = "http://peppa.youxin.com/dataDemand/downloadFile?filename="+db+"_"+table+".csv";
            }
            String returnLog = sb.toString();
            StatusResult sr = new StatusResult(status,returnLog,url);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
        }
        //第一次请求，执行mysql load命令，导出mysql数据到文件夹
        String fn = db+"_"+table+".csv";

        //执行脚本，把db，command传进去，并建立log文件。
        //执行bash 脚本

        Map<String,String[]> hm = createMap();
        String domain = hm.get(db)[0];
        String uname = hm.get(db)[1];
        String pword = hm.get(db)[2];
        ProcessBuilder pb = new ProcessBuilder("sh","/home/hdfs/zuodong/dp/download_mysql.sh",domain,uname,pword,db,table,fn,log_file);
        pb.redirectErrorStream(true);
        Process ps = null;
        try {
            ps = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return empty log, and status "loading"
        log.info("脚本执行");
        StatusResult sr = new StatusResult("loading","",null);
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);

    }

    public BussinessMsg uploadFileToHive(String db, String path, String log_path, String tableName, Logger log) throws IOException {
        File toBeUpload = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(toBeUpload));
        //读第一行，获取字段名
        String line = null;
        if((line = reader.readLine()) != null) {
            String [] cols = line.split(",");
            if(cols.length==0)
                return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_UP_ERROR);
            //拼接建表语句
            StringBuilder command = new StringBuilder("CREATE TABLE IF NOT EXISTS "
                    + db+"."+tableName
                    +" (");
            for(String col : cols){
                command.append(col.replace("\"","")+" String, ");
            }
            command.setLength(command.length()-2);
            command.append(")");
            command.append(" ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde' " +
                    "STORED AS TEXTFILE tblproperties('skip.header.line.count'='1')")  ;
            log.info("command:"+command);
            String cmd = command.toString();

            //执行脚本，把db，command传进去，并建立log文件。
            //执行bash 脚本
            ProcessBuilder pb = new ProcessBuilder("sh","/home/hdfs/zuodong/dp/upload_hive.sh",cmd,db,tableName,path,log_path);
            pb.redirectErrorStream(true);
            Process ps = null;
            try {
                ps = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //return empty log, and status "loading"
            log.info("脚本执行");
            StatusResult sr = new StatusResult("loading","",null);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
        }else{
            log.info("空文件");
            StatusResult sr = new StatusResult("loading","",null);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
        }
    }
    public BussinessMsg importMySQLtoHive(String MysqlDB, String MysqlTable, Logger log){

        String HiveDB="tmp";
        String HiveTable=MysqlTable;
        String marker = "/tmp/sqoop/sqoop_"+HiveTable;
        File f = new File(marker);
        log.info("marker path:"+f.getAbsoluteFile());

        String status = "loading";
        if(f.exists() && !f.isDirectory()) {
            // marker文件存在，说明此表正在被操作，只需返回log以及status即可
            log.info("marker 存在，返回log");
            //if log contains "sqoop_task finished",status = "end"
            File logFile = new File("/tmp/sqoop/sqoop_"+HiveTable+".log");
            StringBuilder sb = new StringBuilder();
            if(logFile.exists()){
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(logFile));
                    String st;
                    while ((st = br.readLine()) != null){
                        sb.append(st).append("\n");
                        if(st.contains("sqoop_task finished"))
                            status = "end";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String returnLog = sb.toString();

            //remove marker file
            boolean deleted = false;
            if(status.equals("end")&&f.exists())
                deleted = f.delete();
//            log.info("delete marker file:"+f.getName()+"successfully deleted:"+deleted);
            StatusResult sr = new StatusResult(status,returnLog,null);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
        }else{
            // marker文件不存在，说明是首次操作数据库，需要执行sqoop命令
            // 先建立markerfile
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            try {
                boolean created =  f.createNewFile();
                log.info("create marker file:"+f.getName()+"created:"+created);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //执行bash 脚本
            ProcessBuilder pb = new ProcessBuilder("sh","/home/hdfs/zuodong/dp/sqoop_hive.sh",MysqlDB,MysqlTable,HiveDB,HiveTable);
            pb.redirectErrorStream(true);
            Process ps = null;
            try {
                ps = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //return empty log, and status "loading"
            log.info("脚本执行");
            StatusResult sr = new StatusResult(status,"",null);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_OP_SUCCESS,sr);
        }
    }

    private Map<String,String[]> createMap (){

        Map<String,String[]> hm = new HashMap<>();
        hm.put("renrendai_0513",new String[]{"192.168.1.43","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        hm.put("data_bi",new String[]{"192.168.1.43","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        hm.put("reports",new String[]{"192.168.1.48","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        hm.put("prodsjlc",new String[]{"192.168.1.48","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        hm.put("rts",new String[]{"192.168.1.48","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        hm.put("test",new String[]{"192.168.1.48","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        hm.put("rule_engine",new String[]{"192.168.1.82","rrd_sjlc","vl9MCOhrTqTsOFfG1MaO"});
        hm.put("marketing",new String[]{"192.168.1.82","rrd_sjlc","vl9MCOhrTqTsOFfG1MaO"});
        hm.put("message_center",new String[]{"192.168.1.78","rrd_sjlc","Vpov9BBvxReqCZQxw3YS"});
        return hm;
    }
}
