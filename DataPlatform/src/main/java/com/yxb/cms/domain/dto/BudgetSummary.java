package com.yxb.cms.domain.dto;

import java.util.Map;

public class BudgetSummary {
	private Map<String, Map<String, Double>> stockBudgetRepayment;
	private Map<String, Map<String, Double>> stockBudgetBalanceAfterRepay;
	private Map<String, Map<String, Double>> stockBudgetInterestRate;
	private Map<String, Double> budgetAdvance;
	private Map<String, Double> budgetAdvancePayment;
	private Map<String, Double> budgetAdvanceRepayment;
	private Map<String, Double> stockBudgetBalanceAfterRepayAndAdvance;
	private Map<String, Map<String, Double>> channelBudgetAmount;
	private Map<String, Map<String, Double>> channelBudgetRepayment;
	private Map<String, Map<String, Double>> channelBudgetBalance;
	private Map<String, Map<String, Double>> channelBudgetBalanceInterestRate;
	private Map<String, Double> budgetBalance;
	private Map<String, Double> budgetBalanceInterestRate;

	public BudgetSummary(Map<String, Map<String, Double>> stockBudgetRepayment, Map<String, Map<String, Double>> stockBudgetBalanceAfterRepay, Map<String, Map<String, Double>> stockBudgetInterestRate, Map<String, Double> budgetAdvance, Map<String, Double> budgetAdvancePayment, Map<String, Double> budgetAdvanceRepayment, Map<String, Double> stockBudgetBalanceAfterRepayAndAdvance, Map<String, Map<String, Double>> channelBudgetAmount, Map<String, Map<String, Double>> channelBudgetRepayment, Map<String, Map<String, Double>> channelBudgetBalance, Map<String, Map<String, Double>> channelBudgetBalanceInterestRate, Map<String, Double> budgetBalance, Map<String, Double> budgetBalanceInterestRate) {
		this.stockBudgetRepayment = stockBudgetRepayment;
		this.stockBudgetBalanceAfterRepay = stockBudgetBalanceAfterRepay;
		this.stockBudgetInterestRate = stockBudgetInterestRate;
		this.budgetAdvance = budgetAdvance;
		this.budgetAdvancePayment = budgetAdvancePayment;
		this.budgetAdvanceRepayment = budgetAdvanceRepayment;
		this.stockBudgetBalanceAfterRepayAndAdvance = stockBudgetBalanceAfterRepayAndAdvance;
		this.channelBudgetAmount = channelBudgetAmount;
		this.channelBudgetRepayment = channelBudgetRepayment;
		this.channelBudgetBalance = channelBudgetBalance;
		this.channelBudgetBalanceInterestRate = channelBudgetBalanceInterestRate;
		this.budgetBalance = budgetBalance;
		this.budgetBalanceInterestRate = budgetBalanceInterestRate;
	}

	public Map<String, Map<String, Double>> getStockBudgetRepayment() {
		return stockBudgetRepayment;
	}

	public void setStockBudgetRepayment(Map<String, Map<String, Double>> stockBudgetRepayment) {
		this.stockBudgetRepayment = stockBudgetRepayment;
	}

	public Map<String, Map<String, Double>> getStockBudgetBalanceAfterRepay() {
		return stockBudgetBalanceAfterRepay;
	}

	public void setStockBudgetBalanceAfterRepay(Map<String, Map<String, Double>> stockBudgetBalanceAfterRepay) {
		this.stockBudgetBalanceAfterRepay = stockBudgetBalanceAfterRepay;
	}

	public Map<String, Map<String, Double>> getStockBudgetInterestRate() {
		return stockBudgetInterestRate;
	}

	public void setStockBudgetInterestRate(Map<String, Map<String, Double>> stockBudgetInterestRate) {
		this.stockBudgetInterestRate = stockBudgetInterestRate;
	}

	public Map<String, Double> getBudgetAdvance() {
		return budgetAdvance;
	}

	public void setBudgetAdvance(Map<String, Double> budgetAdvance) {
		this.budgetAdvance = budgetAdvance;
	}

	public Map<String, Double> getBudgetAdvancePayment() {
		return budgetAdvancePayment;
	}

	public void setBudgetAdvancePayment(Map<String, Double> budgetAdvancePayment) {
		this.budgetAdvancePayment = budgetAdvancePayment;
	}

	public Map<String, Double> getBudgetAdvanceRepayment() {
		return budgetAdvanceRepayment;
	}

	public void setBudgetAdvanceRepayment(Map<String, Double> budgetAdvanceRepayment) {
		this.budgetAdvanceRepayment = budgetAdvanceRepayment;
	}

	public Map<String, Double> getStockBudgetBalanceAfterRepayAndAdvance() {
		return stockBudgetBalanceAfterRepayAndAdvance;
	}

	public void setStockBudgetBalanceAfterRepayAndAdvance(Map<String, Double> stockBudgetBalanceAfterRepayAndAdvance) {
		this.stockBudgetBalanceAfterRepayAndAdvance = stockBudgetBalanceAfterRepayAndAdvance;
	}

	public Map<String, Map<String, Double>> getChannelBudgetAmount() {
		return channelBudgetAmount;
	}

	public void setChannelBudgetAmount(Map<String, Map<String, Double>> channelBudgetAmount) {
		this.channelBudgetAmount = channelBudgetAmount;
	}

	public Map<String, Map<String, Double>> getChannelBudgetRepayment() {
		return channelBudgetRepayment;
	}

	public void setChannelBudgetRepayment(Map<String, Map<String, Double>> channelBudgetRepayment) {
		this.channelBudgetRepayment = channelBudgetRepayment;
	}

	public Map<String, Map<String, Double>> getChannelBudgetBalance() {
		return channelBudgetBalance;
	}

	public void setChannelBudgetBalance(Map<String, Map<String, Double>> channelBudgetBalance) {
		this.channelBudgetBalance = channelBudgetBalance;
	}

	public Map<String, Map<String, Double>> getChannelBudgetBalanceInterestRate() {
		return channelBudgetBalanceInterestRate;
	}

	public void setChannelBudgetBalanceInterestRate(Map<String, Map<String, Double>> channelBudgetBalanceInterestRate) {
		this.channelBudgetBalanceInterestRate = channelBudgetBalanceInterestRate;
	}

	public Map<String, Double> getBudgetBalance() {
		return budgetBalance;
	}

	public void setBudgetBalance(Map<String, Double> budgetBalance) {
		this.budgetBalance = budgetBalance;
	}

	public Map<String, Double> getBudgetBalanceInterestRate() {
		return budgetBalanceInterestRate;
	}

	public void setBudgetBalanceInterestRate(Map<String, Double> budgetBalanceInterestRate) {
		this.budgetBalanceInterestRate = budgetBalanceInterestRate;
	}
}
