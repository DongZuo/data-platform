package com.yxb.cms.dao.meta;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MetaDBTableMapper {
    String getDBTableName();


}
