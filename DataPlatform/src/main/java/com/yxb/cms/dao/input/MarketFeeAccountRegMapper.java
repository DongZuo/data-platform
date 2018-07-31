package com.yxb.cms.dao.input;

import java.util.List;
import java.util.Map;

import com.data.util.MyBatisRepository;
import com.yxb.cms.model.input.MarketFeeAccountReg;
@MyBatisRepository
public interface MarketFeeAccountRegMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MarketFeeAccountReg record);

    int insertSelective(MarketFeeAccountReg record);

    List<MarketFeeAccountReg> selectListByPrimaryKey(Map<String, Object> Map);
    
    MarketFeeAccountReg selectByPrimaryKey(Map<String, Object> Map);
    
    int getCount(Map<String, Object> Map);

    int updateByPrimaryKeySelective(MarketFeeAccountReg record);

    int updateByPrimaryKey(MarketFeeAccountReg record);
}