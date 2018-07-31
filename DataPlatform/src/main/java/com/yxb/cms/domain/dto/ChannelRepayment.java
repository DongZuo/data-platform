package com.yxb.cms.domain.dto;

import java.util.List;
import java.util.Map;

public class ChannelRepayment {
	private String channelName;
	private List<SubChannelRepayment> subChannelRepayments;
	private List<Map<String, Object>> channelRepaymentSummary;

	public ChannelRepayment(String channelName, List<SubChannelRepayment> subChannelRepayments, List<Map<String, Object>> channelRepaymentSummary) {
		this.channelName = channelName;
		this.subChannelRepayments = subChannelRepayments;
		this.channelRepaymentSummary = channelRepaymentSummary;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public List<SubChannelRepayment> getSubChannelRepayments() {
		return subChannelRepayments;
	}

	public void setSubChannelRepayments(List<SubChannelRepayment> subChannelRepayments) {
		this.subChannelRepayments = subChannelRepayments;
	}

	public List<Map<String, Object>> getChannelRepaymentSummary() {
		return channelRepaymentSummary;
	}

	public void setChannelRepaymentSummary(List<Map<String, Object>> channelRepaymentSummary) {
		this.channelRepaymentSummary = channelRepaymentSummary;
	}
}
