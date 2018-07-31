package com.yxb.cms.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 0 9 * * ?")
    public void clearDir() throws IOException {

        File dir = new File("/tmp/sqoop/");
        FileUtils.deleteDirectory(dir);
        log.info("directory deleted:"+dir.getAbsolutePath());
        File dir2 = new File("/tmp/backtrack");
        FileUtils.deleteDirectory(dir2);
        log.info("directory deleted:"+dir2.getAbsolutePath());
        File dir3 = new File("/tmp/dp/download/hivelog");
        if(!dir3.exists())
            dir3.mkdir();
        else{
            FileUtils.deleteDirectory(dir3);
            dir3.mkdir();
        }
        log.info("directory created:"+dir3.getAbsolutePath());
        File dir4 = new File("/tmp/dp/download/hive");
        if(!dir4.exists())
            dir4.mkdir();
        else{
            FileUtils.deleteDirectory(dir4);
            dir4.mkdir();
        }
        log.info("directory created:"+dir4.getAbsolutePath());
        File dir5 = new File("/tmp/dp/download/mysqllog");
        if(!dir5.exists())
            dir5.mkdir();
        else{
            FileUtils.deleteDirectory(dir5);
            dir5.mkdir();
        }
        log.info("directory created:"+dir5.getAbsolutePath());
        File dir6 = new File("/tmp/dp/download/result");
        if(!dir6.exists())
            dir6.mkdir();
        else{
            FileUtils.deleteDirectory(dir6);
            dir6.mkdir();
        }
        log.info("directory created:"+dir6.getAbsolutePath());

        ProcessBuilder pb = new ProcessBuilder("sh","/home/hdfs/zuodong/dp/scheduled_task.sh");
        pb.redirectErrorStream(true);
        Process ps = null;
        try {
            ps = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("scheduled task executed");
    }
}