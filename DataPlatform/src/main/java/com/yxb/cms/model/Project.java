package com.yxb.cms.model;

public class Project {
    String coverBg;
    String headerImg;
    String description;
    String author;
    String title;
    String key;

    public String getCoverBg() {
        return coverBg;
    }

    public void setCoverBg(String coverBg) {
        this.coverBg = coverBg;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = id;
    }
}
