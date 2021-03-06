package com.yxb.cms.dao.input;

import java.util.List;
import java.util.Map;

import com.data.util.MyBatisRepository;
import com.yxb.cms.model.input.TbUtmSource;

@MyBatisRepository
public interface TbUtmSourceMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_utm_source
     * @mbggenerated  Wed Jun 24 18:30:38 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_utm_source
     * @mbggenerated  Wed Jun 24 18:30:38 CST 2015
     */
    int insert(TbUtmSource record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_utm_source
     * @mbggenerated  Wed Jun 24 18:30:38 CST 2015
     */
    TbUtmSource selectByPrimaryKey(Integer id);
    
    TbUtmSource selectByPrimaryKeyByDescription(String description);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_utm_source
     * @mbggenerated  Wed Jun 24 18:30:38 CST 2015
     */
    List<TbUtmSource> selectAll();

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table tb_utm_source
     * @mbggenerated  Wed Jun 24 18:30:38 CST 2015
     */
    int updateByPrimaryKey(TbUtmSource record);
    
    int selectCount(Map<String, Object> paramMap);
    
    List<TbUtmSource> select(Map<String, Object> paramMap);
}