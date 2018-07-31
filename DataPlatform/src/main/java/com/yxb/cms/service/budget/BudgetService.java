package com.yxb.cms.service.budget;

import com.data.util.numeric.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxb.cms.dao.BudgetChannelLoanMapper;
import com.yxb.cms.dao.BudgetSubChannelLoanMapper;
import com.yxb.cms.dao.BudgetTemplateMapper;
import com.yxb.cms.domain.dto.*;
import com.yxb.cms.model.BudgetChannelLoan;
import com.yxb.cms.model.BudgetSubChannelLoan;
import com.yxb.cms.model.BudgetTemplate;
import com.yxb.cms.model.StockBudgetDetail;
import com.yxb.cms.service.budget.domain.ChannelBudget;
import com.yxb.cms.service.budget.domain.JoinedBudget;
import com.yxb.cms.service.budget.domain.StockBudget;
import com.yxb.cms.service.budget.domain.SubChannelBudget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BudgetService {
	private BudgetTemplateMapper budgetTemplateDAO;
	private BudgetChannelLoanMapper budgetChannelLoanDAO;
	private BudgetSubChannelLoanMapper budgetSubChannelLoanDAO;
	private JdbcTemplate hiveTemplate;

	@Autowired
	public void setBudgetTemplateDAO(BudgetTemplateMapper budgetTemplateDAO) {
		this.budgetTemplateDAO = budgetTemplateDAO;
	}

	@Autowired
	public void setBudgetChannelLoanDAO(BudgetChannelLoanMapper budgetChannelLoanDAO) {
		this.budgetChannelLoanDAO = budgetChannelLoanDAO;
	}

	@Autowired
	public void setBudgetSubChannelLoanDAO(BudgetSubChannelLoanMapper budgetSubChannelLoanDAO) {
		this.budgetSubChannelLoanDAO = budgetSubChannelLoanDAO;
	}

	@Autowired
	public void setHiveTemplate(JdbcTemplate hiveTemplate) {
		this.hiveTemplate = hiveTemplate;
	}

	public Budget getBudget(Integer id) {
		BudgetTemplate budgetTemplate = budgetTemplateDAO.selectById(id);
		List<BudgetChannelLoan> budgetChannelLoans = budgetChannelLoanDAO.selectByTemplateId(id);
		List<BudgetSubChannelLoan> budgetSubChannelLoans = budgetSubChannelLoanDAO.selectByTemplateId(id);

		List<ChannelBudget> channelBudgets = new ArrayList<>();
		Map<String, Double[]> channelTotals = new HashMap<>();
		for (BudgetChannelLoan budgetChannelLoan :
				budgetChannelLoans) {
			ChannelBudget channelBudget = new ChannelBudget();
			channelBudget.setChannelName(budgetChannelLoan.getChannelName());
			String[] amount = budgetChannelLoan.getLoanAmount().split(",");
			Double[] data = new Double[amount.length];
			for (int i = 0; i < amount.length; i++) {
				data[i] = Double.parseDouble(amount[i]);
			}
			channelBudget.setAmount(data);
			channelBudgets.add(channelBudget);
			channelTotals.put(budgetChannelLoan.getChannelName(), data);
		}
		List<SubChannelBudget> subChannelBudgets = new ArrayList<>();
		for (BudgetSubChannelLoan budgetSubChannelLoan :
				budgetSubChannelLoans) {
			SubChannelBudget subChannelBudget = new SubChannelBudget();
			subChannelBudget.setChannelName(budgetSubChannelLoan.getChannelName());
			subChannelBudget.setPeriod(budgetSubChannelLoan.getPeriod());
			subChannelBudget.setInterestRate(budgetSubChannelLoan.getInterestRate());
			subChannelBudget.setPercentage(budgetSubChannelLoan.getPercentage());
			subChannelBudget.setRepaymentType(budgetSubChannelLoan.getRepaymentType());
			Double[] totals = channelTotals.get(budgetSubChannelLoan.getChannelName());
			Double[] data = new Double[totals.length];
			Double percentage = budgetSubChannelLoan.getPercentage();
			for (int i = 0; i < totals.length; i++) {
				data[i] = totals[i] * percentage;
			}
			subChannelBudget.setAmount(data);
			subChannelBudgets.add(subChannelBudget);
		}

		return new Budget(budgetTemplate, channelBudgets, subChannelBudgets);
	}

	public List<SearchedBudget> searchBudget(String type,String status, String tType, String begin, String end) {
		return budgetTemplateDAO.search(type, status, tType, begin, end);
	}

	@Transactional
	public Integer saveBudget(Map<String, Object> data) throws IOException {
		for (Map.Entry<String, Object> entry :
				data.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value.equals(""))
				data.put(key, null);
		}
		Integer id;
		if (data.get("id") != null) {
			id = Integer.parseInt((String) data.get("id"));
		} else {
			id = -1;
		}
		Integer count = budgetTemplateDAO.countById(id);
		// 创建新预算
		if (count == 0) {
			budgetTemplateDAO.save(data);
			id = (int) (long) data.get("id");
		}
		// 更新旧预算
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date();
			String time = sdf.format(curDate.getTime());
			BudgetTemplate tem = budgetTemplateDAO.selectById(id);
				data.put("remark", data.get("remark")+" 【编辑者："+data.get("creator")+" 编辑时间："+time+"】");		
			if(tem.getTitle() != null){
				data.put("title",null);
				data.put("creator",null);
			}
			budgetTemplateDAO.update(data);
		}
		// 渠道总推标
		ObjectMapper objectMapper = new ObjectMapper();
		String serChannelLoan = (String) data.get("channel_loan_total");
		if (serChannelLoan != null) {
			budgetChannelLoanDAO.deleteByTemplateId(id);
			List<ChannelBudget> channelBudgets = objectMapper.readValue(serChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, ChannelBudget.class));
			for (ChannelBudget total :
					channelBudgets) {
				BudgetChannelLoan budgetChannelLoan = new BudgetChannelLoan(id, total);
				budgetChannelLoanDAO.save(budgetChannelLoan);
			}
		}
		// 渠道推标详细配置
		String serSubChannelLoan = (String) data.get("channel_loan_detail");
		if (serSubChannelLoan != null) {
			budgetSubChannelLoanDAO.deleteByTemplateId(id);
			List<SubChannelBudget> subChannelBudgets = objectMapper.readValue(serSubChannelLoan, objectMapper.getTypeFactory().constructCollectionType(List.class, SubChannelBudget.class));
			for (SubChannelBudget detail :
					subChannelBudgets) {
				BudgetSubChannelLoan budgetSubChannelLoan = new BudgetSubChannelLoan(id, detail);
				budgetSubChannelLoanDAO.save(budgetSubChannelLoan);
			}
		}

		return id;
	}

	@Transactional
	public BudgetTemplate copyBudget(String newKey,String oldKey,String userName) {
		Map<String, Object> data = new HashMap<>();
		data.put("newKey", newKey);
		data.put("oldKey", oldKey);
		data.put("userName", userName);
		budgetTemplateDAO.copyTemplate(data);
		BudgetTemplate budget = budgetTemplateDAO.selectByKey(newKey);
		budget.setKey(budget.getKey_());
		data.put("id", budget.getId());
		budgetChannelLoanDAO.copy(data);
		budgetSubChannelLoanDAO.copy(data);
		return budget;
	}

	public List<PublicBudget> publicBudget() {
		List<PublicBudget> budgets = budgetTemplateDAO.publicBudget();
		for (PublicBudget publicBudget : budgets) {
			publicBudget.setKey(publicBudget.getId().toString());
		}
		return budgets;
	}

	@Transactional
	public void deleteBudget(String key) {
		budgetTemplateDAO.delete(key);
		BudgetTemplate budget = budgetTemplateDAO.selectByKey(key);
		budgetChannelLoanDAO.deleteByTemplateId(budget.getId());
		budgetSubChannelLoanDAO.deleteByTemplateId(budget.getId());
	}

	public Map<String, List<Map<String, Object>>> channelDetail(List<ChannelBudget> channelBudgets,
																List<SubChannelBudget> subChannelBudgets,
																Date[] dates) {
		Map<String, List<Map<String, Object>>> subChannelDetails = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");

		String[] formattedDates = new String[dates.length];
		for (int i = 0; i < dates.length; i++) {
			formattedDates[i] = "D" + dateFormat.format(dates[i]);
		}

		for (ChannelBudget channelBudget :
				channelBudgets) {
			channelBudget.load(dates, subChannelBudgets);
			List<Map<String, Object>> channelDetails = new ArrayList<>();
			double channelInterestRate = 0;
			for (SubChannelBudget scl :
					channelBudget.getSubChannelBudgets().values()) {
				Map<String, Object> subChannelRow = new HashMap<>();
				Double[] subChannelAmount = scl.getAmount();
				for (int i = 0; i < subChannelAmount.length; i++) {
					subChannelRow.put(formattedDates[i], subChannelAmount[i]);
				}
				subChannelRow.put("period", scl.getPeriod());
				subChannelRow.put("rate", String.format("%.2f%%", scl.getInterestRate() * 100));
				subChannelRow.put("percentage", String.format("%.2f%%", scl.getPercentage() * 100));
				channelInterestRate += scl.getInterestRate() * scl.getPercentage();
				channelDetails.add(subChannelRow);
			}
			Map<String, Object> channelRow = new HashMap<>();
			Double[] channelAmount = channelBudget.getAmount();
			for (int i = 0; i < channelAmount.length; i++) {
				channelRow.put(formattedDates[i], channelAmount[i]);
			}
			channelRow.put("period", "合计");
			channelRow.put("rate", String.format("%.2f%%", channelInterestRate * 100));
			channelRow.put("percentage", "100%");
			channelDetails.add(channelRow);
			String channelName = channelBudget.getChannelName();
			subChannelDetails.put(channelName, channelDetails);
		}

		return subChannelDetails;
	}

	public BudgetSummary computeBudget(Date[] dates,
									   List<SubChannelBudget> subChannelBudgets,
									   List<ChannelBudget> channelBudgets,
									   String advancePayType, Double[] advancePayment,
									   String advanceRepayType, Double[] advanceRepayment) {
		// 存量标的预算
		StockBudget stockBudget = getStockBudget(dates);
		// 新推标的预算
		Map<String, ChannelBudget> channelBudgetMap = new HashMap<>();
		for (ChannelBudget channelBudget :
				channelBudgets) {
			String channelName = channelBudget.getChannelName();
			channelBudget.load(dates, subChannelBudgets);
			channelBudgetMap.put(channelName, channelBudget);
		}
		// 总预算
		JoinedBudget joinedBudget = new JoinedBudget(dates, channelBudgetMap, stockBudget);
		// 存量标回款本金
		Map<String, Double[]> stockBudgetRepayment = stockBudget.getRepayment();
		// 存量资产余额（只含正常还款）
		Map<String, Double[]> stockBudgetBalanceAfterRepay = stockBudget.getBalanceAfterRepay();
		// 存量资产收益率
		Map<String, Double[]> stockBudgetInterestRate = stockBudget.getInterestRate();
		// 提前还款
		joinedBudget.computeAdvance(advancePayType, advancePayment, advanceRepayType, advanceRepayment);
		Double[] budgetAdvance = joinedBudget.getAdvance();
		Double[] budgetAdvancePayment = joinedBudget.getAdvancePayment();
		Double[] budgetAdvanceRepayment = joinedBudget.getAdvanceRepayment();
		// 存量资产余额（考虑提前还款后）
		Double[] stockBudgetBalanceAfterRepayAndAdvance = FinanceFunction.align(Double.class, stockBudgetBalanceAfterRepay.get("总计"), FinanceFunction.sequence(Double.class, budgetAdvance, new DoubleAdder()), new DoubleSubtractor());
		// 各渠道新推标的
		Map<String, Double[]> channelBudgetAmount = joinedBudget.getChannelBudgetAmount();
		// 新推标的正常本金回款
		Map<String, Double[]> channelBudgetRepay = joinedBudget.getChannelBudgetRepay();
		// 新资产余额
		Map<String, Double[]> channelBudgetBalance = joinedBudget.getChannelBudgetBalance();
		// 新资产收益率
		Map<String, Double[]> channelBudgetBalanceInterestRate = joinedBudget.getChannelBudgetBalanceInterestRate();
		// 合计资产余额
		Double[] budgetBalance = FinanceFunction.align(Double.class, stockBudgetBalanceAfterRepayAndAdvance, channelBudgetBalance.get("总计"), new DoubleAdder());
		// 合计资产余额收益率
		Double[] budgetBalanceInterestRate = FinanceFunction.align(Double.class, FinanceFunction.align(Double.class, FinanceFunction.align(Double.class, stockBudgetBalanceAfterRepay.get("总计"), stockBudgetInterestRate.get("总计"), new DoubleMultiplier()), FinanceFunction.align(Double.class, channelBudgetBalance.get("总计"), channelBudgetBalanceInterestRate.get("总计"), new DoubleMultiplier()), new DoubleAdder()), FinanceFunction.align(Double.class, stockBudgetBalanceAfterRepay.get("总计"), channelBudgetBalance.get("总计"), new DoubleAdder()), new DoubleDivider());

		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String[] formattedDates = Arrays.stream(dates).map(format::format).toArray(String[]::new);
		return new BudgetSummary(
				toRecord(formattedDates, stockBudgetRepayment),
				toRecord(formattedDates, stockBudgetBalanceAfterRepay),
				toRecord(formattedDates, stockBudgetInterestRate),
				toRecord(formattedDates, budgetAdvance),
				toRecord(formattedDates, budgetAdvancePayment),
				toRecord(formattedDates, budgetAdvanceRepayment),
				toRecord(formattedDates, stockBudgetBalanceAfterRepayAndAdvance),
				toRecord(formattedDates, channelBudgetAmount),
				toRecord(formattedDates, channelBudgetRepay),
				toRecord(formattedDates, channelBudgetBalance),
				toRecord(formattedDates, channelBudgetBalanceInterestRate),
				toRecord(formattedDates, budgetBalance),
				toRecord(formattedDates, budgetBalanceInterestRate));
	}

	public List<ChannelRepayment> computeBudgetDetail(List<ChannelBudget> channelBudgets, List<SubChannelBudget> subChannelBudgets, Date[] dates) {
		List<ChannelRepayment> channelRepayments = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

		int length = dates.length;
		String[] formattedDates = new String[length];
		for (int i = 0; i < length; i++) {
			formattedDates[i] = dateFormat.format(dates[i]);
		}

		for (ChannelBudget channelBudget :
				channelBudgets) {
			channelBudget.load(dates, subChannelBudgets);
			List<SubChannelRepayment> subChannelRepayments = new ArrayList<>();
			for (SubChannelBudget subChannelBudget :
					channelBudget.getSubChannelBudgets().values()) {
				// 子渠道回款明细
				Matrix<Double> subChannelRepayDetail = subChannelBudget.getRepayment();
				Double[] subChannelAmounts = subChannelBudget.getAmount();
				Double[] subChannelRepaySumByLoan = subChannelBudget.repaySumByLoan();
				List<Map<String, Object>> formattedSubChannelRepays = new ArrayList<>();
				for (int i = 0; i < subChannelRepayDetail.getxSize(); i++) {
					Double[] repayValues = subChannelRepayDetail.getRow(i);
					Map<String, Object> formattedSubChannelRepay = new HashMap<>();
					for (int j = 0; j < repayValues.length; j++) {
						formattedSubChannelRepay.put(Integer.toString(j + 1), repayValues[j]);
					}
					formattedSubChannelRepay.put("date", formattedDates[i]);
					formattedSubChannelRepay.put("amount", subChannelAmounts[i]);
					formattedSubChannelRepay.put("total", subChannelRepaySumByLoan[i]);
					formattedSubChannelRepays.add(formattedSubChannelRepay);
				}
				// 子渠道回款统计
				Map<String, Object> formattedSubChannelRepaySum = new HashMap<>();
				Double[] subChannelRepaySumByMonth = subChannelBudget.repaySumByMonth();
				for (int i = 0; i < subChannelRepaySumByMonth.length; i++) {
					formattedSubChannelRepaySum.put(Integer.toString(i + 1), subChannelRepaySumByMonth[i]);
				}
				formattedSubChannelRepaySum.put("date", "小计回款");
				formattedSubChannelRepaySum.put("amount", subChannelBudget.amountSum());
				formattedSubChannelRepaySum.put("total", subChannelBudget.repaySum());
				formattedSubChannelRepays.add(formattedSubChannelRepaySum);
				// 子渠道资产余额
				Map<String, Object> formattedSubChannelBalanceSum = new HashMap<>();
				Double[] subChannelBalance = subChannelBudget.getBalance();
				for (int i = 0; i < subChannelBalance.length; i++) {
					formattedSubChannelBalanceSum.put(Integer.toString(i + 1), subChannelBalance[i]);
				}
				formattedSubChannelBalanceSum.put("date", "小计资产余额");
				formattedSubChannelBalanceSum.put("amount", "-");
				formattedSubChannelBalanceSum.put("total", "-");
				formattedSubChannelRepays.add(formattedSubChannelBalanceSum);
				SubChannelRepayment subChannelRepayment = new SubChannelRepayment(subChannelBudget.getPeriod(), String.format("%.2f%%", subChannelBudget.getInterestRate() * 100), formattedSubChannelRepays);
				subChannelRepayments.add(subChannelRepayment);
			}
			List<Map<String, Object>> channelRepaySummary = new ArrayList<>();
			Double[] channelRepayByMonth = channelBudget.getRepayment();
			Double[] channelBalanceByMonth = channelBudget.getBalance();
			String[] channelBalanceInterestRateByMonth = Arrays.stream(channelBudget.getBalanceInterestRate()).map(rate -> String.format("%.2f%%", rate * 100)).toArray(String[]::new);
			Map<String, Object> formattedChannelRepay = indexMap(channelRepayByMonth);
			formattedChannelRepay.put("date", "合计回款");
			formattedChannelRepay.put("amount", channelBudget.amountSum());
			formattedChannelRepay.put("total", channelBudget.repaySum());
			channelRepaySummary.add(formattedChannelRepay);
			Map<String, Object> formattedChannelBalance = indexMap(channelBalanceByMonth);
			formattedChannelBalance.put("date", "合计资产余额");
			formattedChannelBalance.put("amount", "-");
			formattedChannelBalance.put("total", "-");
			channelRepaySummary.add(formattedChannelBalance);
			Map<String, Object> formattedChannelBalanceInterestRate = indexMap(channelBalanceInterestRateByMonth);
			formattedChannelBalanceInterestRate.put("date", "合计资产余额收益率");
			formattedChannelBalanceInterestRate.put("amount", "-");
			formattedChannelBalanceInterestRate.put("total", "-");
			channelRepaySummary.add(formattedChannelBalanceInterestRate);
			ChannelRepayment channelRepayment = new ChannelRepayment(channelBudget.getChannelName(), subChannelRepayments, channelRepaySummary);
			channelRepayments.add(channelRepayment);
		}

		return channelRepayments;
	}

	private StockBudget getStockBudget(Date[] dates) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String queryDate = format.format(dates[0]);
		// TODO 测试SQL
		String hql = "SELECT due_mth, product_channel, initialprincipal, finalprincipal, interest, interest_rate, initialprincipal-finalprincipal repaid_principal FROM (SELECT product_channel, trunc(value.duedate,'MM') due_mth, sum(value.initialprincipal) initialprincipal, sum(VALUE.finalprincipal)  finalprincipal, sum(VALUE.initialprincipal* interest)/100 interest, sum(VALUE.finalprincipal* interest)/sum(VALUE.finalprincipal) interest_rate FROM dw.dw_loan  lateral VIEW explode(borrow_repay_record) adTable AS phaseNumber,VALUE WHERE dt=date_sub(trunc('{date}','MM'),1) AND pass_time >'2010-01-01' AND VALUE.duedate>=trunc('{date}','MM') AND (VALUE.status!='REPAID'  OR  VALUE.repaidtime>=trunc('{date}','MM') ) AND ((repaid_by_guarantor_time IS NULL OR repaid_by_guarantor_time='' OR repaid_by_guarantor_time='null') OR repaid_by_guarantor_time>=trunc('{date}','MM') ) AND (VALUE.initialprincipal>0 OR VALUE.initialprincipal>0) GROUP BY product_channel,trunc(VALUE.duedate,'MM') UNION ALL SELECT '总计' product_channel, trunc(VALUE.duedate,'MM') due_mth, sum(VALUE.initialprincipal) initialprincipal, sum(VALUE.finalprincipal)  finalprincipal, sum(VALUE.initialprincipal* interest)/100 interest, sum(VALUE.finalprincipal* interest)/sum(VALUE.finalprincipal) interest_rate FROM dw.dw_loan  lateral VIEW explode(borrow_repay_record) adTable AS phaseNumber,VALUE WHERE dt=date_sub(trunc('{date}','MM'),1) AND pass_time >'2010-01-01' AND VALUE.duedate>=trunc('{date}','MM') AND (VALUE.status!='REPAID'  OR  VALUE.repaidtime>=trunc('{date}','MM') ) AND ((repaid_by_guarantor_time IS NULL OR repaid_by_guarantor_time='' OR repaid_by_guarantor_time='null') OR repaid_by_guarantor_time>=trunc('{date}','MM') ) AND (VALUE.initialprincipal>0 OR VALUE.initialprincipal>0) GROUP BY trunc(VALUE.duedate,'MM') ) a ORDER BY due_mth,product_channel";
		hql = hql.replaceAll("\\{date}", queryDate);
		List<StockBudgetDetail> stockBudgetDetails = hiveTemplate.query(hql, (resultset, i) -> {
			StockBudgetDetail StockBudgetDetail = new StockBudgetDetail();
			StockBudgetDetail.setDueDate(resultset.getString("due_mth"));
			StockBudgetDetail.setProductChannel(resultset.getString("product_channel"));
			StockBudgetDetail.setInitialPrincipal(resultset.getDouble("initialprincipal"));
			StockBudgetDetail.setFinalPrincipal(resultset.getDouble("finalprincipal"));
			StockBudgetDetail.setInterest(resultset.getDouble("interest"));
			StockBudgetDetail.setInterestRate(resultset.getDouble("interest_rate"));
			StockBudgetDetail.setRepayPrincipal(resultset.getDouble("repaid_principal"));

			return StockBudgetDetail;
		});

		return new StockBudget(dates, stockBudgetDetails);
	}

	private <T> Map<String, T> indexMap(T[] data) {
		Map<String, T> result = new HashMap<>();

		for (int i = 0; i < data.length; i++) {
			result.put(Integer.toString(i + 1), data[i]);
		}

		return result;
	}

	private Map<String, Map<String, Double>> toRecord(String[] index, Map<String, Double[]> data) {
		Map<String, Map<String, Double>> result = new HashMap<>();

		for (Map.Entry<String, Double[]> entry :
				data.entrySet()) {
			String key = entry.getKey();
			Double[] value = entry.getValue();
			Map<String, Double> record = new HashMap<>();
			for (int i = 0; i < value.length; i++) {
				record.put(index[i], value[i]);
			}
			result.put(key, record);
		}

		return result;
	}

	private Map<String, Double> toRecord(String[] index, Double[] data) {
		Map<String, Double> result = new HashMap<>();

		for (int i = 0; i < data.length; i++) {
			result.put(index[i], data[i]);
		}

		return result;
	}

	/**
	 * 调用生成预算是状态变为已完成
	 */
	public void completeBudget(Integer id,String title){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", "已完成");
		data.put("title", title);
		data.put("id", id);
		budgetTemplateDAO.update(data);	
	}

}