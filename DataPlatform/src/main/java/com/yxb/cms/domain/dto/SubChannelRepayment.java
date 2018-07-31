package com.yxb.cms.domain.dto;

import java.util.List;
import java.util.Map;

public class SubChannelRepayment {
	private short period;
	private String interestRate;
	private List<Map<String, Object>> repayment;

	public SubChannelRepayment(short period, String interestRate, List<Map<String, Object>> repayment) {
		this.period = period;
		this.interestRate = interestRate;
		this.repayment = repayment;
	}

	public short getPeriod() {
		return period;
	}

	public void setPeriod(short period) {
		this.period = period;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public List<Map<String, Object>> getRepayment() {
		return repayment;
	}

	public void setRepayment(List<Map<String, Object>> repayment) {
		this.repayment = repayment;
	}
}
