package com.yxb.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yxb.cms.domain.vo.CcIndex;
@Mapper
public interface CcChartsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CcIndex record);

    int insertSelective(CcIndex record);

    CcIndex selectByPrimaryKey(Integer id);
        
    List<CcIndex> selectByUserNameList(CcIndex index);    

    int updateByPrimaryKeySelective(CcIndex record);

    int updateByPrimaryKey(CcIndex record);
}