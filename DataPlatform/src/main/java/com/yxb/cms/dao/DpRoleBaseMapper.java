package com.yxb.cms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.yxb.cms.model.DpRole;
/**
*  @author rrd
*/
public interface DpRoleBaseMapper {

    int insertDpRole(DpRole object);

    int updateDpRole(DpRole object);

    List<DpRole> queryDpRole(DpRole object);

    DpRole queryDpRoleLimit1(DpRole object);

}