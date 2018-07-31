package com.yxb.cms.domain.dto;

import com.yxb.cms.model.BudgetTemplate;
import com.yxb.cms.service.budget.domain.ChannelBudget;
import com.yxb.cms.service.budget.domain.SubChannelBudget;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Budget {
	private Integer id;
	private Integer refId;
	private String title;
	private String creator;
	private Date createDate;
	private Date finishDate;
	private String status;
	private String isPublic;
	private Date beginDate;
	private Short cycle;
	private String unit;
	private List<String> date;
	private String advanceType;
	private List<Double> advanceAmount;
	private String repaymentType;
	private List<Double> repaymentAmount;
	private List<ChannelBudget> totals;
	private List<SubChannelBudget> details;

	public Budget(BudgetTemplate budgetTemplate, List<ChannelBudget> totals, List<SubChannelBudget> details) {
		this.id = budgetTemplate.getId();
		this.refId = budgetTemplate.getRefId();
		this.title = budgetTemplate.getTitle();
		this.creator = budgetTemplate.getCreator();
		this.createDate = budgetTemplate.getCreateDate();
		this.finishDate = budgetTemplate.getFinishDate();
		this.status = budgetTemplate.getStatus();
		this.isPublic = budgetTemplate.getIsPublic();
		this.beginDate = budgetTemplate.getBeginDate();
		this.cycle = budgetTemplate.getCycle();
		this.unit = budgetTemplate.getUnit();

		if (cycle != null) {
			this.date = new ArrayList<>();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			for (int i = 0; i < cycle; i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(this.beginDate);
				calendar.add(Calendar.MONTH, i);
				this.date.add(format.format(calendar.getTime()));
			}
		}

		this.advanceType = budgetTemplate.getAdvanceType();
		this.advanceAmount = new ArrayList<>();
		String advances = budgetTemplate.getAdvanceAmount();
		if (advances != null) {
			for (String a :
					advances.split(",")) {
				this.advanceAmount.add(Double.parseDouble(a));
			}
		}

		this.repaymentType = budgetTemplate.getRepaymentType();
		this.repaymentAmount = new ArrayList<>();
		String repayments = budgetTemplate.getRepaymentAmount();
		if (repayments != null) {
			for (String p :
					repayments.split(",")) {
				this.repaymentAmount.add(Double.parseDouble(p));
			}
		}

		this.totals = totals;
		this.details = details;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRefId() {
		return refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Short getCycle() {
		return cycle;
	}

	public void setCycle(Short cycle) {
		this.cycle = cycle;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<String> getDate() {
		return date;
	}

	public void setDate(List<String> date) {
		this.date = date;
	}

	public String getAdvanceType() {
		return advanceType;
	}

	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}

	public List<Double> getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(List<Double> advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public List<Double> getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(List<Double> repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public List<ChannelBudget> getTotals() {
		return totals;
	}

	public void setTotals(List<ChannelBudget> totals) {
		this.totals = totals;
	}

	public List<SubChannelBudget> getDetails() {
		return details;
	}

	public void setDetails(List<SubChannelBudget> details) {
		this.details = details;
	}
}

