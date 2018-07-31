package com.yxb.cms.model;

import java.util.List;

public class UserPermission {
    Long key;
    String userName;
    List<String>  permissions;

    public UserPermission(Long key, String userName, List<String> permissions) {
        this.key = key;
        this.userName = userName;
        this.permissions = permissions;
    }

    public Long getKey() {

        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
