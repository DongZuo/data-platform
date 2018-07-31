package com.yxb.cms.dao;

import com.yxb.cms.model.BudgetChannelLoan;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface BudgetChannelLoanMapper {
	List<BudgetChannelLoan> selectByTemplateId(Integer id);

	@Transactional
	Integer deleteByTemplateId(Integer id);

	@Transactional
	Integer save(@Param("value") BudgetChannelLoan value);

	@Transactional
	Integer copy(@Param("value") Map<String, Object> data);
}
