package com.yxb.cms.model;

public class StockBudgetDetail {
	private String dueDate;
	private String productChannel;
	private Double initialPrincipal;
	private Double finalPrincipal;
	private Double interest;
	private Double interestRate;
	private Double repayPrincipal;

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getProductChannel() {
		return productChannel;
	}

	public void setProductChannel(String productChannel) {
		this.productChannel = productChannel;
	}

	public Double getInitialPrincipal() {
		return initialPrincipal;
	}

	public void setInitialPrincipal(Double initialPrincipal) {
		this.initialPrincipal = initialPrincipal;
	}

	public Double getFinalPrincipal() {
		return finalPrincipal;
	}

	public void setFinalPrincipal(Double finalPrincipal) {
		this.finalPrincipal = finalPrincipal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getRepayPrincipal() {
		return repayPrincipal;
	}

	public void setRepayPrincipal(Double repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
}
