package com.yxb.cms.service.budget.domain;

import java.util.*;

import com.data.util.ServiceUtil;
import com.data.util.numeric.DoubleAdder;
import com.data.util.numeric.DoubleDivider;
import com.data.util.numeric.FinanceFunction;
import com.data.util.numeric.Matrix;

/**
 * 新推标的预算(渠道)
 */
public class ChannelBudget {
	private Date[] dateIndex;
	private String channelName;
	private Double[] amount;
	private Double[] repayment;
	private Double[] balance;
	private Double[] balanceInterest;
	private Double[] balanceInterestRate;
	private Map<Long, SubChannelBudget> subChannelBudgets = new HashMap<>();

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

	public Double[] getAmount() {
		return amount;
	}

	public void setAmount(Double[] amount) {
		this.amount = amount;
	}

	public Double[] getRepayment() {
		return repayment;
	}

	public Double[] getBalance() {
		return balance;
	}

	public Double[] getBalanceInterest() {
		return balanceInterest;
	}

	public Double[] getBalanceInterestRate() {
		return balanceInterestRate;
	}

	public Map<Long, SubChannelBudget> getSubChannelBudgets() {
		return subChannelBudgets;
	}

	public void load(Date[] dates, List<SubChannelBudget> subChannelBudgets) {
		dateIndex = dates;
		for (SubChannelBudget subChannelBudget :
				subChannelBudgets) {
			String channelName = subChannelBudget.getChannelName();
			if (this.channelName.equals(channelName)) {
				subChannelBudget.load(dates, amount);
				String[] keys = new String[4];
				keys[0] = Short.toString(subChannelBudget.getPeriod());
				keys[1] = Double.toString(subChannelBudget.getInterestRate());
				keys[2] = Double.toString(subChannelBudget.getPercentage());
				keys[3] = subChannelBudget.getRepaymentType();
				long hashCode = ServiceUtil.hash(keys);
				this.subChannelBudgets.put(hashCode, subChannelBudget);
			}
		}
		computeRepay();
		computeBalance();
		computeBalanceInterest();
		computeBalanceInterestRate();
	}

	public void computeRepay() {
		List<SubChannelBudget> budgets = new ArrayList<>(subChannelBudgets.values());
		int xSize = budgets.size();
		int ySize = amount.length;
		Matrix<Double> subRepays = new Matrix<>(Double.class, xSize, ySize);
		for (int i = 0; i < xSize; i++) {
			subRepays.setRow(i, budgets.get(i).repaySumByMonth());
		}
		repayment = subRepays.applyOnX(new DoubleAdder());
	}

	public void computeBalance() {
		List<SubChannelBudget> subChannelBudgets = new ArrayList<>(this.subChannelBudgets.values());
		int xSize = subChannelBudgets.size();
		int ySize = amount.length;
		Matrix<Double> subBalances = new Matrix<>(Double.class, xSize, ySize);
		for (int i = 0; i < xSize; i++) {
			subBalances.setRow(i, subChannelBudgets.get(i).getBalance());
		}
		balance = subBalances.applyOnX(new DoubleAdder());
	}

	public void computeBalanceInterest() {
		List<SubChannelBudget> subChannelBudgets = new ArrayList<>(this.subChannelBudgets.values());
		int xSize = subChannelBudgets.size();
		int ySize = amount.length;
		Matrix<Double> subBalanceInterests = new Matrix<>(Double.class, xSize, ySize);
		for (int i = 0; i < xSize; i++) {
			subBalanceInterests.setRow(i, subChannelBudgets.get(i).getBalanceInterest());
		}
		balanceInterest = subBalanceInterests.applyOnX(new DoubleAdder());
	}

	private void computeBalanceInterestRate() {
		balanceInterestRate = FinanceFunction.align(Double.class, balanceInterest, balance, new DoubleDivider());
	}


	public double repaySum() {
		return FinanceFunction.reduce(repayment, new DoubleAdder());
	}

	public double amountSum() {
		return FinanceFunction.reduce(amount, new DoubleAdder());
	}
}
