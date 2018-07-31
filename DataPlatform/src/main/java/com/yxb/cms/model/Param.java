package com.yxb.cms.model;

import java.util.List;

public class Param {
    private String name;
    private String type;
    private String key;
    private String commit;
//    long parentId;
//    long returnId;
//    long interfaceId;
private List<Param> children ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public List<Param> getChildren() {
        return children;
    }

    public void setChildren(List<Param> children) {
        this.children = children;
    }
}
