package com.yxb.cms.model.input;

public class TbSubUtmSource {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column tb_sub_utm_source.id
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    private Integer id;
    
    private String key;
    
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
     * This field was generated by MyBatis Generator. This field corresponds to the database column tb_sub_utm_source.parent_id
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    private Integer parentId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column tb_sub_utm_source.source_name
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    private String sourceName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column tb_sub_utm_source.plan
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    private String plan;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column tb_sub_utm_source.unit
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    private String unit;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column tb_sub_utm_source.keyword
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    private String keyword;
    
    private String utmMedium;
    
    private String base_utm_source;
    
    private String landingPage;
  

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public String getBase_utm_source() {
		return base_utm_source;
	}

	public void setBase_utm_source(String base_utm_source) {
		this.base_utm_source = base_utm_source;
	}

	public String getUtmMedium() {
		return utmMedium;
	}

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}


	/**
     * This method was generated by MyBatis Generator. This method returns the value of the database column tb_sub_utm_source.id
     * @return  the value of tb_sub_utm_source.id
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column tb_sub_utm_source.id
     * @param id  the value for tb_sub_utm_source.id
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column tb_sub_utm_source.parent_id
     * @return  the value of tb_sub_utm_source.parent_id
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column tb_sub_utm_source.parent_id
     * @param parentId  the value for tb_sub_utm_source.parent_id
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column tb_sub_utm_source.source_name
     * @return  the value of tb_sub_utm_source.source_name
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column tb_sub_utm_source.source_name
     * @param sourceName  the value for tb_sub_utm_source.source_name
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column tb_sub_utm_source.plan
     * @return  the value of tb_sub_utm_source.plan
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public String getPlan() {
        return plan;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column tb_sub_utm_source.plan
     * @param plan  the value for tb_sub_utm_source.plan
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public void setPlan(String plan) {
        this.plan = plan;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column tb_sub_utm_source.unit
     * @return  the value of tb_sub_utm_source.unit
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column tb_sub_utm_source.unit
     * @param unit  the value for tb_sub_utm_source.unit
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column tb_sub_utm_source.keyword
     * @return  the value of tb_sub_utm_source.keyword
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column tb_sub_utm_source.keyword
     * @param keyword  the value for tb_sub_utm_source.keyword
     * @mbggenerated  Mon Aug 24 18:02:13 CST 2015
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}