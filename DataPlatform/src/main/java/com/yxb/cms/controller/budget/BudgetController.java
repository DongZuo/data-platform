package com.yxb.cms.controller.budget;

import com.data.util.ServiceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxb.cms.controller.BasicController;
import com.yxb.cms.domain.dto.*;
import com.yxb.cms.model.BudgetTemplate;
import com.yxb.cms.service.budget.BudgetService;
import com.yxb.cms.service.budget.domain.ChannelBudget;
import com.yxb.cms.service.budget.domain.SubChannelBudget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/budget")
public class BudgetController extends BasicController{
	private BudgetService budgetService;

	@Autowired
	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@RequestMapping(value = "/channelDetail", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List<Map<String, Object>>>> subChannel(@RequestParam("begin_date") java.sql.Date beginDate,
																			 @RequestParam("cycle") int cycle,
																			 @RequestParam("sub_channel_loan") String serSubChannelLoan,
																			 @RequestParam("channel_loan") String serChannelLoan) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ChannelBudget> channelBudgets = objectMapper.readValue(serChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, ChannelBudget.class));
		List<SubChannelBudget> subChannelBudgets = objectMapper.readValue(serSubChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, SubChannelBudget.class));
		Date[] dates = ServiceUtil.dateRange(beginDate, Calendar.MONTH, cycle);
		Map<String, List<Map<String, Object>>> subChannel = budgetService.channelDetail(channelBudgets, subChannelBudgets, dates);

		return ResponseEntity.ok(subChannel);
	}

	/**
	 * 预算汇总
	 *
	 * @param beginDate         预算起始日期
	 * @param cycle             预算周期
	 * @param serSubChannelLoan 子渠道预算
	 * @param serChannelLoan    渠道预算
	 * @param advancePayType    提前垫付类型
	 * @param advancePayment    提前垫付数值
	 * @param advanceRepayType  提前还款类型
	 * @param advanceRepayment  提前还款数值
	 * @return 预算
	 * @throws IOException 渠道/子渠道预算反序列化异常
	 */
	@RequestMapping(value = "/computeBudget", method = RequestMethod.POST)
	public ResponseEntity computeBudget(@RequestParam("begin_date") java.sql.Date beginDate,
										@RequestParam("cycle") int cycle,
										@RequestParam("sub_channel_loan") String serSubChannelLoan,
										@RequestParam("channel_loan") String serChannelLoan,
										@RequestParam("advance_pay_type") String advancePayType,
										@RequestParam("advance_payment[]") Double[] advancePayment,
										@RequestParam("advance_repay_type") String advanceRepayType,
										@RequestParam("advance_repayment[]") Double[] advanceRepayment) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ChannelBudget> channelBudgets = objectMapper.readValue(serChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, ChannelBudget.class));
		List<SubChannelBudget> subChannelBudgets = objectMapper.readValue(serSubChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, SubChannelBudget.class));
		Date[] dates = ServiceUtil.dateRange(beginDate, Calendar.MONTH, cycle);
		BudgetSummary budgetSummary = budgetService.computeBudget(dates, subChannelBudgets, channelBudgets, advancePayType, advancePayment, advanceRepayType, advanceRepayment);

		return ResponseEntity.ok(budgetSummary);
	}

	@RequestMapping(value = "/computeBudgetDetail", method = RequestMethod.POST)
	public ResponseEntity<List<ChannelRepayment>> computeBudgetDetail(@RequestParam("begin_date") java.sql.Date beginDate,
																	  @RequestParam("cycle") int cycle,
																	  @RequestParam("sub_channel_loan") String serSubChannelLoan,
																	  @RequestParam("channel_loan") String serChannelLoan) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ChannelBudget> channelBudgets = objectMapper.readValue(serChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, ChannelBudget.class));
		List<SubChannelBudget> subChannelBudgets = objectMapper.readValue(serSubChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, SubChannelBudget.class));
		Date[] dates = ServiceUtil.dateRange(beginDate, Calendar.MONTH, cycle);
		List<ChannelRepayment> channelRepayments = budgetService.computeBudgetDetail(channelBudgets, subChannelBudgets, dates);

		return ResponseEntity.ok(channelRepayments);
	}

	@RequestMapping(value = "/budgetDetail", method = RequestMethod.POST)
	public ResponseEntity<Budget> getBudget(Integer id) {
		Budget budget = budgetService.getBudget(id);
		return ResponseEntity.ok(budget);
	}

	@RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
	public ResponseEntity saveBudget(@RequestParam MultiValueMap<String, String> rawData) throws IOException {
		Map<String, Object> data = new HashMap<>();
		for (Map.Entry<String, List<String>> entry :
				rawData.entrySet()) {
			String key = entry.getKey();
			data.put(key, rawData.getFirst(key));
		}
		Integer id = budgetService.saveBudget(data);
		return ResponseEntity.ok(id);
	}

	@RequestMapping(value = "/deleteDetail", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> deleteBudget(@RequestBody Map<String,String> body) {
		budgetService.deleteBudget(body.get("key"));
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据删除成功");
		result.put("data", "");
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/searchBudget", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchBudget(@RequestBody Map<String,String> body) {
		List<SearchedBudget> budgets = budgetService.searchBudget(body.get("budgetType"), body.get("status"), body.get("timeType"), body.get("begin"), body.get("end"));
		for (SearchedBudget searchedBudget : budgets) {
			searchedBudget.setKey(searchedBudget.getId().toString());
		}
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("total", budgets.size());
		responseData.put("rows", budgets);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据获取成功");
		result.put("data", responseData);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/publicBudget", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> publicBudget() {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据复制成功");
		result.put("data", budgetService.publicBudget());
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/copyBudget", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> copyTemplate(@RequestBody Map<String,String> body){
		BudgetTemplate budget = budgetService.copyBudget(body.get("newKey"),body.get("oldKey"),body.get("userName"));
		//String username = (String) session.getAttribute("loginname");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		result.put("msg", "数据复制成功");
		result.put("data", budget);
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value = "/completeBudget", method = RequestMethod.POST)
	public ResponseEntity<String> completeBudget(int id,String title) {
		 budgetService.completeBudget(id,title);
		 return ResponseEntity.ok("complete");
	}
}