package com.yxb.cms.domain.vo;

import java.util.Date;

public class TbDailyInputVo {
    private Date date;
    
    private Integer id;
    
    private String key;
    
    private Integer utmSourceId;
    
    private String description;
    
    private String category;
    
    private String parentCategory;
    
    private String platform;
    
    private String chargeType;
    
    private Double consume;
    
    private Float discount;
    
    private Integer display;
    
    private Integer exposure;
    
    private Integer click;
    
    private String comment;
    
    private Integer downloads;
    
    private String landingPage;
    
    private String downloadPage;   
     

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getExposure() {
		return exposure;
	}

	public void setExposure(Integer exposure) {
		this.exposure = exposure;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public String getDownloadPage() {
		return downloadPage;
	}

	public void setDownloadPage(String downloadPage) {
		this.downloadPage = downloadPage;
	}

	public Integer getDownloads() {
		return downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUtmSourceId() {
        return utmSourceId;
    }

    public void setUtmSourceId(Integer utmSourceId) {
        this.utmSourceId = utmSourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
