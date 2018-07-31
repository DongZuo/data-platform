package com.yxb.cms.dao;

import com.yxb.cms.domain.dto.PublicBudget;
import com.yxb.cms.domain.dto.SearchedBudget;
import com.yxb.cms.model.BudgetTemplate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface BudgetTemplateMapper{
	List<SearchedBudget> search(@Param("type") String type,
								@Param("status") String status,
								@Param("t_type") String tType,
								@Param("begin") String begin,
								@Param("end") String end);

	BudgetTemplate selectById(Integer id);
	
	BudgetTemplate selectByKey(@Param("newKey") String newKey);

	List<PublicBudget> publicBudget();

	Integer countById(Integer id);

	@Transactional
	Integer save(@Param("value") Map<String, Object> data);

	@Transactional
	Integer update(@Param("value") Map<String, Object> data);

	@Transactional
	Integer delete(@Param("newKey") String newKey);

	@Transactional
	Integer copyTemplate(@Param("value") Map<String, Object> data);
}
