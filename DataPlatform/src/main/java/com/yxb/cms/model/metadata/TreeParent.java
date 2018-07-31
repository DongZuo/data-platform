package com.yxb.cms.model.metadata;

import java.util.List;


/**
 * @author yangxin_ryan
 * left part: Database information
 */
public class TreeParent {

    private String name;

    private String key;

    private List<TreeChild> menu;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public List<TreeChild> getMenu() {
        return menu;
    }

    public void setMenu(List<TreeChild> menu) {
        this.menu = menu;
    }
}
