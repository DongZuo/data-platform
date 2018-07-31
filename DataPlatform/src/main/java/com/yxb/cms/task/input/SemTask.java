package com.yxb.cms.task.input;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;    
import org.springframework.stereotype.Component;

import com.yxb.cms.dao.input.SemStatisticsMapper;

@Component("SemTask")
public class SemTask {
	@Resource
	private SemStatisticsMapper semStatisticsMapper;
	private static final Logger log = LoggerFactory.getLogger(SemTask.class);
	
	//@Scheduled(cron="0 5 6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?")  //每小时第59分钟 = 0 10 * * * ? 每隔10分钟 = 0 0/10 * * * ?
	public void jobByHour() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		log.info("~~~~~~~~~~~~开始跑数~~~~~~~~~~~"+df.format(new Date()));
		int countDay = semStatisticsMapper.insertSelectiveDay(null);
		log.info("共插入缓存表"+countDay+"条理财数据,任务进行中。。。"+df.format(new Date()));
		int delcount = semStatisticsMapper.deleteByPrimaryKey(0);
		log.info("共删除主表"+delcount+"条数据,任务进行中。。。"+df.format(new Date()));
		int count = semStatisticsMapper.insertSelective(null);
		log.info("共插入主表"+count+"条理财数据,任务进行中。。。"+df.format(new Date()));
		int delcountDay = semStatisticsMapper.deleteByPrimaryKeyDay(0);
		log.info("共删除缓存"+delcountDay+"条数据,任务进行中。。。"+df.format(new Date()));
		int delCountTime = semStatisticsMapper.deleteByCountTime(0);
		log.info("共删除当前小时"+delCountTime+"条数据,任务结束"+df.format(new Date()));
		//int count2 = semStatisticsMapper.insertSelective2(null);
		//log.info("共插入"+count2+"条基金数据,任务进行中。。。"+df.format(new Date()));
        //System.out.println("共插入"+count+"条数据,任务进行中。。。"+df.format(new Date()));  
    }
	
	//@Scheduled(cron="0 10 1 * * ?")  //每小时第59分钟 = 0 10 * * * ? 每隔10分钟 = 0 0/10 * * * ?
    public void jobByDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		log.info("~~~~~~~~~~~~开始跑数~~~~~~~~~~~"+df.format(new Date()));
		int delcount = semStatisticsMapper.deleteByPrimaryKeyEnd(0);
		log.info("共删除主表"+delcount+"条数据,任务进行中。。。"+df.format(new Date()));
		int countDay = semStatisticsMapper.insertSelectiveEnd(null);
		log.info("共插入每日更新表"+countDay+"条理财数据,任务进行中。。。"+df.format(new Date())); 
    }
	
//	@Scheduled(cron="0 0/1 * * * ?")  //60秒执行一次
//    public void job2() {
////				//int count = semStatisticsMapper.insertSelective(null);
////				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
////		        System.out.println("共插入"+1+"条数据,任务进行中。。。"+df.format(new Date()));    
//    } 
}
