package com.yxb.cms.controller;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 *  Spring-boot server启动时，初始化运行的命令。
 *  主要功能是删除log file 和 marker file所在的文件夹。
 *  避免永久阻塞某个表的更新
 */
@Component
public class InitialCommandLineRunner implements CommandLineRunner{
    protected Logger log = LogManager.getLogger(getClass());
    @Override
    public void run(String... var1) throws Exception{
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
        File dir7 = new File("/tmp/dp/upload");
        if(!dir7.exists())
            dir7.mkdir();
        else{
            FileUtils.deleteDirectory(dir7);
            dir7.mkdir();
        }
        log.info("directory created:"+dir7.getAbsolutePath());
        File dir8 = new File("/tmp/dp/upload/log");
        if(!dir8.exists())
            dir8.mkdir();
        else{
            FileUtils.deleteDirectory(dir8);
            dir8.mkdir();
        }
        log.info("directory created:"+dir8.getAbsolutePath());
    }

}