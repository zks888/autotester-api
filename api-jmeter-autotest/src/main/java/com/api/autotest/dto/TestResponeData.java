package com.api.autotest.dto;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.List;

public class TestResponeData {

    private List<Header> headerList;
    private List<RequestHead> requestHeadList;
    private List<RequestParams> requestParamsList;
    private List<Cookie> cookies;
    private int ResponeCode;
    private long Size;
    private long ResponeTime;
    private ResponeGeneral responeGeneral;
    private String RequestUrl;
    private String ResponeContent;

    public List<Header> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<Header> headerList) {
        this.headerList = headerList;
    }

    public String getResponeContent() {
        return ResponeContent;
    }

    public void setResponeContent(String responeContent) {
        ResponeContent = responeContent;
    }

    public List<RequestHead> getRequestHeadList() {
        return requestHeadList;
    }

    public void setRequestHeadList(List<RequestHead> requestHeadList) {
        this.requestHeadList = requestHeadList;
    }

    public List<RequestParams> getRequestParamsList() {
        return requestParamsList;
    }

    public void setRequestParamsList(List<RequestParams> requestParamsList) {
        this.requestParamsList = requestParamsList;
    }

    public int getResponeCode() {
        return ResponeCode;
    }

    public void setResponeCode(int responeCode) {
        ResponeCode = responeCode;
    }

    public long getSize() {
        return Size;
    }

    public void setSize(long size) {
        Size = size;
    }

    public long getResponeTime() {
        return ResponeTime;
    }

    public void setResponeTime(long responeTime) {
        ResponeTime = responeTime;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public ResponeGeneral getResponeGeneral() {
        return responeGeneral;
    }

    public void setResponeGeneral(ResponeGeneral responeGeneral) {
        this.responeGeneral = responeGeneral;
    }

    public String getRequestUrl() {
        return RequestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        RequestUrl = requestUrl;
    }
}


