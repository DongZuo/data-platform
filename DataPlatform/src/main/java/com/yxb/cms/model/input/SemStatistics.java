package com.yxb.cms.model.input;

import java.math.BigDecimal;
import java.util.Date;

public class SemStatistics {
    private Integer statisticsId;
    
    private String key;

    private String description;

    private String category;

    private String platform;

    private String chargeType;

    private Date countTime;
    
    private String flagDate;
    
    private String timeFlag;

    private Integer totalRegUser;
    
    private Integer totalOpenUser;

    private Integer totalRegLender;

    private BigDecimal lenderConvert;

    private Integer totalLender;

    private BigDecimal totalLenderMoney;

    private BigDecimal avgLenderMoney;

    private Integer totalPayUser;

    private BigDecimal totalPayMoney;

    private BigDecimal avgPayMoney;
    
    private BigDecimal fundMoney;
    
    private Integer fundUser;
    
    private BigDecimal sumMoney;

    private Integer sumUser;
      
   
    
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getTotalOpenUser() {
		return totalOpenUser;
	}

	public void setTotalOpenUser(Integer totalOpenUser) {
		this.totalOpenUser = totalOpenUser;
	}

	public BigDecimal getFundMoney() {
		return fundMoney;
	}

	public void setFundMoney(BigDecimal fundMoney) {
		this.fundMoney = fundMoney;
	}

	public Integer getFundUser() {
		return fundUser;
	}

	public void setFundUser(Integer fundUser) {
		this.fundUser = fundUser;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public Integer getSumUser() {
		return sumUser;
	}

	public void setSumUser(Integer sumUser) {
		this.sumUser = sumUser;
	}

	public String getFlagDate() {
		return flagDate;
	}

	public void setFlagDate(String flagDate) {
		this.flagDate = flagDate;
	}

	public Integer getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(Integer statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    public Integer getTotalRegUser() {
        return totalRegUser;
    }

    public void setTotalRegUser(Integer totalRegUser) {
        this.totalRegUser = totalRegUser;
    }

    public Integer getTotalRegLender() {
        return totalRegLender;
    }

    public void setTotalRegLender(Integer totalRegLender) {
        this.totalRegLender = totalRegLender;
    }

    public BigDecimal getLenderConvert() {
        return lenderConvert;
    }

    public void setLenderConvert(BigDecimal lenderConvert) {
        this.lenderConvert = lenderConvert;
    }

    public Integer getTotalLender() {
        return totalLender;
    }

    public void setTotalLender(Integer totalLender) {
        this.totalLender = totalLender;
    }

    public BigDecimal getTotalLenderMoney() {
        return totalLenderMoney;
    }

    public void setTotalLenderMoney(BigDecimal totalLenderMoney) {
        this.totalLenderMoney = totalLenderMoney;
    }

    public BigDecimal getAvgLenderMoney() {
        return avgLenderMoney;
    }

    public void setAvgLenderMoney(BigDecimal avgLenderMoney) {
        this.avgLenderMoney = avgLenderMoney;
    }

    public Integer getTotalPayUser() {
        return totalPayUser;
    }

    public void setTotalPayUser(Integer totalPayUser) {
        this.totalPayUser = totalPayUser;
    }

    public BigDecimal getTotalPayMoney() {
        return totalPayMoney;
    }

    public void setTotalPayMoney(BigDecimal totalPayMoney) {
        this.totalPayMoney = totalPayMoney;
    }

    public BigDecimal getAvgPayMoney() {
        return avgPayMoney;
    }

    public void setAvgPayMoney(BigDecimal avgPayMoney) {
        this.avgPayMoney = avgPayMoney;
    }

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}


    
}