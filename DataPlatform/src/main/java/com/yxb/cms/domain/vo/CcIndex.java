package com.yxb.cms.domain.vo;

public class CcIndex {
    private Integer id;

    private String userName;

    private String indexName;

	private String indexSql;
    
    private String initSql;

    private String indexType;
    
    public String getInitSql() {
		return initSql;
	}

	public void setInitSql(String initSql) {
		this.initSql = initSql;
	}

	public void setIndexSql(String indexSql) {
		this.indexSql = indexSql;
	}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName == null ? null : indexName.trim();
    }

    public String getIndexSql() {
        return indexSql;
    }

    public void setSql(String indexSql) {
        this.indexSql = indexSql == null ? null : indexSql.trim();
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType == null ? null : indexType.trim();
    }
}