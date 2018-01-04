package com.ucs.ganzhou.app.api.model;

public class BaseRequestInfo {
    private  String requestId;
    private String url;
    private  String requestXml;

    public String getUrl() {
        return url;
    }

    public String getRequestXml() {
        return requestXml;
    }

    public String getRequestId() {
        return requestId;
    }

    public  BaseRequestInfo(String requestId, String url, String requestXml){
        this.requestXml = requestXml;
        this.url = url;
        this.requestId = requestId;
    }

}
