package com.yxb.cms.service.input;

import java.util.List;
import java.util.Map;

import com.yxb.cms.model.input.SemStatistics;

public interface ISemStatisticsService {

	/**
	 * 分页查询所有的统计列
	 * @param params
	 * @return
	 */
	List<SemStatistics> selectSemStatisticsList(Map<String, Object> params);
	
	/**
	 * 获取查询列总数
	 * @param params
	 * @return
	 */
	int countSemStatisticsList(Map<String, Object> params);
	
}
