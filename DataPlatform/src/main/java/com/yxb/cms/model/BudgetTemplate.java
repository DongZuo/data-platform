package com.yxb.cms.model;

import java.sql.Date;

public class BudgetTemplate {
	private Integer id;
	private Integer refId;
	private String title;
	private String creator;
	private Date createDate;
	private Date finishDate;
	private String type;
	private String status;
	private String isPublic;
	private Date beginDate;
	private Short cycle;
	private String unit;
	private String advanceType;
	private String advanceAmount;
	private String repaymentType;
	private String repaymentAmount;
	private String remark;
	private String key_;
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getAdvanceType() {
		return advanceType;
	}

	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}

	public String getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(String advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
