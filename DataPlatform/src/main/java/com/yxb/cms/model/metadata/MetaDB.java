package com.yxb.cms.model.metadata;

/**
 * @author yangxin_ryan
 * database model
 */
public class MetaDB {

    private int dbID;

    private String dbEngName;

    private String dbChinName;

    private String dbComment;

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getDbEngName() {
        return dbEngName;
    }

    public void setDbEngName(String dbEngName) {
        this.dbEngName = dbEngName;
    }

    public String getDbChinName() {
        return dbChinName;
    }

    public void setDbChinName(String dbChinName) {
        this.dbChinName = dbChinName;
    }

    public String getDbComment() {
        return dbComment;
    }

    public void setDbComment(String dbComment) {
        this.dbComment = dbComment;
    }
}
