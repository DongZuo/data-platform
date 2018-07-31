package com.yxb.cms.service.input;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxb.cms.dao.input.SemStatisticsMapper;
import com.yxb.cms.model.input.SemStatistics;

@Service
public class SemStatisticsServiceImpl implements ISemStatisticsService {

	@Resource
	private SemStatisticsMapper semStatisticsMapper;
	
	/**
	 * 分页查询所有的统计列
	 * @param params
	 * @return
	 */
	@Override
	public List<SemStatistics> selectSemStatisticsList(Map<String, Object> params) {
		return semStatisticsMapper.getByPage(params);
	}

	@Override
	public int countSemStatisticsList(Map<String, Object> params) {
		return semStatisticsMapper.getCount(params);
	}

}
