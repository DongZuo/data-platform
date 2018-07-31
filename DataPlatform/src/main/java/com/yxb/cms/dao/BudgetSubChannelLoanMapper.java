package com.yxb.cms.dao;

import com.yxb.cms.model.BudgetSubChannelLoan;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface BudgetSubChannelLoanMapper {
	List<BudgetSubChannelLoan> selectByTemplateId(Integer id);

	@Transactional
	Integer deleteByTemplateId(Integer id);

	@Transactional
	Integer save(@Param("value") BudgetSubChannelLoan budgetSubChannelLoan);

	@Transactional
	Integer copy(@Param("value") Map<String, Object> data);
}
