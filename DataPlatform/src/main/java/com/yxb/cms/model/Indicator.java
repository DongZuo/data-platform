package com.yxb.cms.model;

public class Indicator {
    private String key;
    private String author;
    private String title;
    private String sqlInfo;
    private String description;
    private String[] tag ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSqlInfo() {
        return sqlInfo;
    }

    public void setSqlInfo(String sql) {
        this.sqlInfo = sql;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }



}
