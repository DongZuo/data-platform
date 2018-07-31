package com.yxb.cms.model.metadata;

/**
 * @author yangxin_ryan
 * left part: table informations
 */
public class TreeChild {

    private String name;

    private String key;

    private String authority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
