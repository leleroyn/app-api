package com.ucs.ganzhou.app.api.model;

import java.util.Date;

public class Finance {
    private String projectName;
    private float rate;
    private int duration;
    private  float amount;
    private Date beginTime;
    private float minBuyAmount;

    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    public Date getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getMinBuyAmount() {
        return minBuyAmount;
    }

    public void setMinBuyAmount(float minBuyAmount) {
        this.minBuyAmount = minBuyAmount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
