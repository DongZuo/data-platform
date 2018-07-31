package com.yxb.cms.model.input;

import java.util.Date;

public class FeeAccountBalanceKey {
    private Date date;

    private Integer dept;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }
}