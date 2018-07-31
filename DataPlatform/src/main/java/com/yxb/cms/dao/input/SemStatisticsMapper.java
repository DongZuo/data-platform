package com.yxb.cms.dao.input;

import java.util.List;
import java.util.Map;

import com.data.util.MyBatisRepository;
import com.yxb.cms.model.input.SemStatistics;
@MyBatisRepository
public interface SemStatisticsMapper {
    int deleteByPrimaryKey(Integer statisticsId);
    
    int deleteByCountTime(Integer statisticsId);
    
    int deleteByPrimaryKeyDay(Integer statisticsId);

    int insert(SemStatistics record);

    int insertSelective(SemStatistics record);
    
    int insertSelectiveDay(SemStatistics record);
    
    int insertSelectiveEnd(SemStatistics record);
    
    int deleteByPrimaryKeyEnd(Integer statisticsId);
       
    int insertSelective2(SemStatistics record);

    SemStatistics selectByPrimaryKey(Integer statisticsId);

    int updateByPrimaryKeySelective(SemStatistics record);

    int updateByPrimaryKey(SemStatistics record);
    
    List<SemStatistics> getByPage(Map<String, Object> Map);
    
    int getCount(Map<String, Object> Map);
}