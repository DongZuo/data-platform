package com.yxb.cms.model;

import java.util.List;

public class BacktrackResult {

    private List<String> completeDate,failureDate;
    private int percent;
    public BacktrackResult(List<String> completeDate, List<String> failureDate, int percent){
        this.completeDate=completeDate;
        this.failureDate=failureDate;
        this.percent=percent;
    }

    public List<String> getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(List<String> completeDate) {
        this.completeDate = completeDate;
    }

    public List<String> getFailureDate() {
        return failureDate;
    }

    public void setFailureDate(List<String> failureDate) {
        this.failureDate = failureDate;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
