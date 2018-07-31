package com.yxb.cms.model;

import com.yxb.cms.service.budget.domain.SubChannelBudget;

public class BudgetSubChannelLoan {
	private Integer id;
	private Integer templateId;
	private String channelName;
	private Short period;
	private Double interestRate;
	private Double percentage;
	private String repaymentType;

	public BudgetSubChannelLoan() {
	}

	public BudgetSubChannelLoan(Integer id, SubChannelBudget detail) {
		this.id=null;
		this.templateId = id;
		this.channelName = detail.getChannelName();
		this.period = detail.getPeriod();
		this.interestRate = detail.getInterestRate();
		this.percentage = detail.getPercentage();
		this.repaymentType = detail.getRepaymentType();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Short getPeriod() {
		return period;
	}

	public void setPeriod(Short period) {
		this.period = period;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
}
