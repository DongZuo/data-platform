package com.yxb.cms.service.budget.domain;

import java.text.SimpleDateFormat;
import java.util.*;

import com.yxb.cms.model.StockBudgetDetail;

/**
 * 存量标的预算
 */
public class StockBudget {
	private Date[] dateIndex;
	private Map<String, Double[]> initialBalance;
	private Map<String, Double[]> balanceAfterRepay;
	private Map<String, Double[]> repayment;
	private Map<String, Double[]> interestRate;

	public StockBudget(Date[] dates, List<StockBudgetDetail> stockBudgetDetails) {
		dateIndex = dates;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String[] dateIndexAsString = Arrays.stream(dateIndex).map(format::format).toArray(String[]::new);
		Map<String, Map<String, Double>> initialBalanceData = new HashMap<>();
		Map<String, Map<String, Double>> balanceAfterRepayData = new HashMap<>();
		Map<String, Map<String, Double>> repaymentData = new HashMap<>();
		Map<String, Map<String, Double>> interestRateData = new HashMap<>();
		for (StockBudgetDetail stockBudgetDetail :
				stockBudgetDetails) {
			String channelName = stockBudgetDetail.getProductChannel();
			String date = stockBudgetDetail.getDueDate();
			if (!initialBalanceData.containsKey(channelName))
				initialBalanceData.put(channelName, new HashMap<>());
			Map<String, Double> subInitialBalanceData = initialBalanceData.get(channelName);
			subInitialBalanceData.put(date, stockBudgetDetail.getInitialPrincipal());
			if (!balanceAfterRepayData.containsKey(channelName))
				balanceAfterRepayData.put(channelName, new HashMap<>());
			Map<String, Double> subBalanceAfterRepayData = balanceAfterRepayData.get(channelName);
			subBalanceAfterRepayData.put(date, stockBudgetDetail.getFinalPrincipal());
			if (!repaymentData.containsKey(channelName))
				repaymentData.put(channelName, new HashMap<>());
			Map<String, Double> subRepaymentData = repaymentData.get(channelName);
			subRepaymentData.put(date, stockBudgetDetail.getRepayPrincipal());
			if (!interestRateData.containsKey(channelName))
				interestRateData.put(channelName, new HashMap<>());
			Map<String, Double> subInterestRateData = interestRateData.get(channelName);
			subInterestRateData.put(date, stockBudgetDetail.getInterestRate());
		}
		initialBalance = align(dateIndexAsString, initialBalanceData);
		balanceAfterRepay = align(dateIndexAsString, balanceAfterRepayData);
		repayment = align(dateIndexAsString, repaymentData);
		interestRate = align(dateIndexAsString, interestRateData);
	}

	public Map<String, Double[]> getInitialBalance() {
		return initialBalance;
	}

	public Map<String, Double[]> getBalanceAfterRepay() {
		return balanceAfterRepay;
	}

	public Map<String, Double[]> getRepayment() {
		return repayment;
	}

	public Map<String, Double[]> getInterestRate() {
		return interestRate;
	}

	private Map<String, Double[]> align(String[] index, Map<String, Map<String, Double>> data) {
		Map<String, Double[]> alignedData = new HashMap<>();

		for (Map.Entry<String, Map<String, Double>> entry :
				data.entrySet()) {
			String channelName = entry.getKey();
			Map<String, Double> dateValues = entry.getValue();
			Double[] values = new Double[index.length];
			for (int i = 0; i < index.length; i++) {
				values[i] = dateValues.getOrDefault(index[i], 0d);
			}
			alignedData.put(channelName, values);
		}

		return alignedData;
	}
}
