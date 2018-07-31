package com.yxb.cms.model;

import com.yxb.cms.service.budget.domain.ChannelBudget;

public class BudgetChannelLoan {
	private Integer id;
	private Integer templateId;
	private String channelName;
	private String loanAmount;

	public BudgetChannelLoan() {
	}

	public BudgetChannelLoan(Integer templateId, ChannelBudget total) {
		this.id = null;
		this.templateId = templateId;
		this.channelName = total.getChannelName();
		StringBuilder sb = new StringBuilder();
		Double[] data = total.getAmount();
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i]);
			if (i < data.length - 1) {
				sb.append(",");
			}
		}
		this.loanAmount = sb.toString();
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

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
}
