package com.yxb.cms.dao.input;

import java.util.List;
import java.util.Map;

import com.data.util.MyBatisRepository;
import com.yxb.cms.model.input.FeeAccountBalance;
import com.yxb.cms.model.input.FeeAccountBalanceKey;

@MyBatisRepository
public interface FeeAccountBalanceMapper {
    int deleteByPrimaryKey(FeeAccountBalanceKey key);

    int insert(FeeAccountBalance record);

    int insertSelective(FeeAccountBalance record);

    List<FeeAccountBalance> selectByPrimaryKey(Map<String, Object> Map);
    
    int getCount(Map<String, Object> Map);

    int updateByPrimaryKeySelective(FeeAccountBalance record);

    int updateByPrimaryKey(FeeAccountBalance record);
}