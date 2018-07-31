package com.yxb.cms.service.budget.domain;

import java.util.Arrays;
import java.util.Date;

import com.data.util.numeric.DoubleAdder;
import com.data.util.numeric.DoubleSubtractor;
import com.data.util.numeric.FinanceFunction;
import com.data.util.numeric.Matrix;

/**
 * 新推标的预算(子渠道)
 */
public class SubChannelBudget {
	private Date[] dateIndex;
	private String channelName;
	private short period;
	private double interestRate;
	private double percentage;
	private String repaymentType;
	private String returnName;
	private Double[] amount;
	private Matrix<Double> repayment;
	private Double[] balance;
	private Double[] balanceInterest;

	public Date[] getDateIndex() {
		return dateIndex;
	}

	public void setDateIndex(Date[] dateIndex) {
		this.dateIndex = dateIndex;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public short getPeriod() {
		return period;
	}

	public void setPeriod(short period) {
		this.period = period;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public void setRepayment(Matrix<Double> repayment) {
		this.repayment = repayment;
	}

	public void setBalance(Double[] balance) {
		this.balance = balance;
	}

	public void setBalanceInterest(Double[] balanceInterest) {
		this.balanceInterest = balanceInterest;
	}

	public void setAmount(Double[] amount) {
		this.amount = amount;
	}

	public Double[] getAmount() {
		return amount;
	}

	public Matrix<Double> getRepayment() {
		return repayment;
	}

	public Double[] getBalance() {
		return balance;
	}

	public Double[] getBalanceInterest() {
		return balanceInterest;
	}

	public void load(Date[] dates, Double[] channelAmount) {
		setDateIndex(dates);
		computeAmount(channelAmount);
		computeRepay();
		computeBalance();
		computeBalanceInterest();
	}

	private void computeAmount(Double[] channelAmount) {
		amount = new Double[channelAmount.length];
		for (int i = 0; i < amount.length; i++) {
			amount[i] = channelAmount[i] * percentage;
		}
	}

	private void computeRepay() {
		int size = amount.length;
		repayment = new Matrix<>(Double.class, size, size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (j > i)
					repayment.setCell(i, j, FinanceFunction.ppmt(interestRate / 12, j - i, period, amount[i]));
				else
					repayment.setCell(i, j, 0d);
			}
		}
	}

	private void computeBalance() {
		balance = FinanceFunction.sequence(Double.class, FinanceFunction.align(Double.class, amount, repaySumByMonth(), new DoubleSubtractor()), new DoubleAdder());
	}

	private void computeBalanceInterest() {
		balanceInterest = Arrays.stream(balance).map(b -> b * interestRate).toArray(Double[]::new);
	}

	public Double[] repaySumByLoan() {
		return repayment.applyOnY(new DoubleAdder());
	}

	public Double[] repaySumByMonth() {
		return repayment.applyOnX(new DoubleAdder());
	}

	public double amountSum() {
		return FinanceFunction.reduce(amount, new DoubleAdder());
	}

	public double repaySum() {
		return FinanceFunction.reduce(repaySumByMonth(), new DoubleAdder());
	}
}
