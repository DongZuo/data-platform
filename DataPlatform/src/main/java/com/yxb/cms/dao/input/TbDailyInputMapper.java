package com.yxb.cms.dao.input;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.data.util.MyBatisRepository;
import com.yxb.cms.model.input.TbDailyInput;

@MyBatisRepository
public interface TbDailyInputMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_daily_input
     * @mbggenerated  Tue Jun 30 10:13:43 CST 2015
     */
    int deleteByPrimaryKey(@Param("date") Date date, @Param("utmSourceId") Integer utmSourceId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_daily_input
     * @mbggenerated  Tue Jun 30 10:13:43 CST 2015
     */
    int insert(TbDailyInput record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_daily_input
     * @mbggenerated  Tue Jun 30 10:13:43 CST 2015
     */
    TbDailyInput selectByPrimaryKey(@Param("date") String date, @Param("utmSourceId") Integer utmSourceId);

    TbDailyInput selectByPrimaryKeyByDescription(@Param("date") Date date, @Param("description") String description);
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_daily_input
     * @mbggenerated  Tue Jun 30 10:13:43 CST 2015
     */
    List<TbDailyInput> selectAll();

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_daily_input
     * @mbggenerated  Tue Jun 30 10:13:43 CST 2015
     */
    int updateByPrimaryKey(TbDailyInput record);
    
    int selectCount();
    
    List<TbDailyInput> select(Map<String, Object> paramMap);
    
    List<TbDailyInput> selectByDate(Date date);
}