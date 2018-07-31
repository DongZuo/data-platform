package com.yxb.cms.model;
//通用列表模板，用于返回字段为 total,rows的response
public class GeneralList {
    int total ;
    Object [] rows ;
    String sortOrder;
    public GeneralList(int t,Object[] r,String o ){
        total = t;
        rows = r;
        sortOrder = o;
    }

    public void setRows(Object[] rows) {
        this.rows = rows;
    }
    public Object[] getRows(){
        return this.rows;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){return this.total;}
    public void setSortOrder(String o){
        this.sortOrder = o;
    }
    public String getSortOrder(){return this.sortOrder;}
}
