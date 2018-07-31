package com.yxb.cms.model;

import java.util.List;

public class APInterface {
    private String projectId;
    private String key ;
    private String icon;
    private String title;
    private String description;
    private List<Param> params ;
    private List<Param> returnParams;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public List<Param> getReturnParams() {
        return returnParams;
    }

    public void setReturnParams(List<Param> returnParams) {
        this.returnParams = returnParams;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
