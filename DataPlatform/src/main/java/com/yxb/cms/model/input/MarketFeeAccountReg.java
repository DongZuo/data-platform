package com.yxb.cms.model.input;

import java.math.BigDecimal;
import java.util.Date;

public class MarketFeeAccountReg {
    private Integer id;

    private Date time;

    private String tradeId;

    private BigDecimal amount;

    private BigDecimal banlance;

    private BigDecimal marketRegAmount;

    private BigDecimal operationRegAmount;

    private BigDecimal wealthRegAmount;
    
    private BigDecimal customerRegAmount;
    
    private BigDecimal dataRegAmount;

    private BigDecimal otherRegAmount;
   
    

    public BigDecimal getDataRegAmount() {
		return dataRegAmount;
	}

	public void setDataRegAmount(BigDecimal dataRegAmount) {
		this.dataRegAmount = dataRegAmount;
	}

	public BigDecimal getCustomerRegAmount() {
		return customerRegAmount;
	}

	public void setCustomerRegAmount(BigDecimal customerRegAmount) {
		this.customerRegAmount = customerRegAmount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBanlance() {
        return banlance;
    }

    public void setBanlance(BigDecimal banlance) {
        this.banlance = banlance;
    }

    public BigDecimal getMarketRegAmount() {
        return marketRegAmount;
    }

    public void setMarketRegAmount(BigDecimal marketRegAmount) {
        this.marketRegAmount = marketRegAmount;
    }

    public BigDecimal getOperationRegAmount() {
        return operationRegAmount;
    }

    public void setOperationRegAmount(BigDecimal operationRegAmount) {
        this.operationRegAmount = operationRegAmount;
    }

    public BigDecimal getWealthRegAmount() {
        return wealthRegAmount;
    }

    public void setWealthRegAmount(BigDecimal wealthRegAmount) {
        this.wealthRegAmount = wealthRegAmount;
    }

    public BigDecimal getOtherRegAmount() {
        return otherRegAmount;
    }

    public void setOtherRegAmount(BigDecimal otherRegAmount) {
        this.otherRegAmount = otherRegAmount;
    }
}