package com.yxb.cms.model;

public class StatusResult {
    private String status,log,filePath ;
    public StatusResult(String s,String l, String f){
        status = s;
        log = l;
        filePath = f;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
