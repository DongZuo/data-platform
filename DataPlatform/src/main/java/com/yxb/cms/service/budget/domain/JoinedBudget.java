package com.yxb.cms.service.budget.domain;

import com.data.util.numeric.DoubleAdder;
import com.data.util.numeric.DoubleDivider;
import com.data.util.numeric.DoubleMultiplier;
import com.data.util.numeric.FinanceFunction;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 总预算
 */
public class JoinedBudget {
	private Date[] dateIndex;
	private Map<String, ChannelBudget> channelBudgets;
	private StockBudget stockBudget;
	private Double[] advance;
	private Double[] advancePayment;
	private Double[] advanceRepayment;

	public JoinedBudget(Date[] dateIndex, Map<String, ChannelBudget> channelBudgets, StockBudget stockBudget) {
		this.dateIndex = dateIndex;
		this.channelBudgets = channelBudgets;
		this.stockBudget = stockBudget;
	}

	public Double[] getAdvance() {
		return advance;
	}

	public Double[] getAdvancePayment() {
		return advancePayment;
	}

	public Double[] getAdvanceRepayment() {
		return advanceRepayment;
	}

	public void computeAdvance(String advancePayType, Double[] advancePayment,
							   String advanceRepayType, Double[] advanceRepayment) {
		Double[] stockBudgetInitialBalance = stockBudget.getInitialBalance().get("总计");
		Double[] channelBudgetAmount = new Double[dateIndex.length];
		for (int i = 0; i < dateIndex.length; i++) {
			channelBudgetAmount[i] = 0d;
		}
		for (ChannelBudget channelBudget :
				channelBudgets.values()) {
			channelBudgetAmount = FinanceFunction.align(Double.class, channelBudgetAmount, channelBudget.getAmount(), new DoubleAdder());
		}
		Double[] prevChannelBudgetAmount = new Double[dateIndex.length];
		System.arraycopy(channelBudgetAmount, 0, prevChannelBudgetAmount, 1, dateIndex.length - 1);
		prevChannelBudgetAmount[0] = 0d;
		Double[] prevBudgetBalance = FinanceFunction.align(Double.class, stockBudgetInitialBalance, prevChannelBudgetAmount, new DoubleAdder());
		this.advancePayment = new Double[advancePayment.length];
		if (advancePayType.contains("固定常量") || advancePayType.contains("分期常量"))
			System.arraycopy(advancePayment, 0, this.advancePayment, 0, advancePayment.length);
		else
			this.advancePayment = FinanceFunction.align(Double.class, prevBudgetBalance, advancePayment, new DoubleMultiplier());
		this.advanceRepayment = new Double[advanceRepayment.length];
		if (advanceRepayType.contains("固定常量") || advanceRepayType.contains("分期常量"))
			System.arraycopy(advanceRepayment, 0, this.advanceRepayment, 0, advancePayment.length);
		else
			this.advanceRepayment = FinanceFunction.align(Double.class, prevBudgetBalance, advanceRepayment, new DoubleMultiplier());

		Double[] advanceTotal = FinanceFunction.align(Double.class, advancePayment, advanceRepayment, new DoubleAdder());
		this.advance = new Double[advanceTotal.length];
		System.arraycopy(advanceTotal, 0, this.advance, 0, advance.length);
	}

	public Map<String, Double[]> getChannelBudgetAmount() {
		Map<String, Double[]> channelBudgetAmount = new HashMap<>();

		Double[] total = new Double[dateIndex.length];
		for (int i = 0; i < total.length; i++) {
			total[i] = 0d;
		}
		for (ChannelBudget channelBudget :
				channelBudgets.values()) {
			channelBudgetAmount.put(channelBudget.getChannelName(), channelBudget.getAmount());
			total = FinanceFunction.align(Double.class, total, channelBudget.getAmount(), new DoubleAdder());
		}
		channelBudgetAmount.put("总计", total);

		return channelBudgetAmount;
	}

	public Map<String, Double[]> getChannelBudgetRepay() {
		Map<String, Double[]> channelBudgetRepay = new HashMap<>();

		Double[] total = new Double[dateIndex.length];
		for (int i = 0; i < total.length; i++) {
			total[i] = 0d;
		}
		for (ChannelBudget channelBudget :
				channelBudgets.values()) {
			channelBudgetRepay.put(channelBudget.getChannelName(), channelBudget.getRepayment());
			total = FinanceFunction.align(Double.class, total, channelBudget.getRepayment(), new DoubleAdder());
		}
		channelBudgetRepay.put("总计", total);

		return channelBudgetRepay;
	}

	public Map<String, Double[]> getChannelBudgetBalance() {
		Map<String, Double[]> channelBudgetBalance = new HashMap<>();

		Double[] total = new Double[dateIndex.length];
		for (int i = 0; i < total.length; i++) {
			total[i] = 0d;
		}
		for (ChannelBudget channelBudget :
				channelBudgets.values()) {
			channelBudgetBalance.put(channelBudget.getChannelName(), channelBudget.getBalance());
			total = FinanceFunction.align(Double.class, total, channelBudget.getBalance(), new DoubleAdder());
		}
		channelBudgetBalance.put("总计", total);

		return channelBudgetBalance;
	}

	public Map<String, Double[]> getChannelBudgetBalanceInterestRate() {
		Map<String, Double[]> channelBudgetBalanceInterestRate = new HashMap<>();

		Double[] total = new Double[dateIndex.length];
		for (int i = 0; i < total.length; i++) {
			total[i] = 0d;
		}
		for (ChannelBudget channelBudget :
				channelBudgets.values()) {
			channelBudgetBalanceInterestRate.put(channelBudget.getChannelName(), channelBudget.getBalanceInterestRate());
			total = FinanceFunction.align(Double.class, total, channelBudget.getBalanceInterest(), new DoubleAdder());
		}
		Double[] channelBudgetBalanceTotal = getChannelBudgetBalance().get("总计");
		channelBudgetBalanceInterestRate.put("总计", FinanceFunction.align(Double.class, total, channelBudgetBalanceTotal, new DoubleDivider()));

		return channelBudgetBalanceInterestRate;
	}
}
