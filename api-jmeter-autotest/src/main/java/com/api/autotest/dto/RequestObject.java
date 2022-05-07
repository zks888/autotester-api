package com.api.autotest.dto;

import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import java.util.List;

public class RequestObject {

    private String testplanid = "";
    private String deployunitid = "";
    private String caseid = "";
    private String batchid = "";
    private String slaverid = "";
    private String batchname = "";
    private String casename = "";
    private String testplanname = "";
    private String casetype = "";
    private String expect = "";
    private String RequestmMthod;
    private HttpParamers paramers;
    private String PostData;
    private String requestcontenttype;
    private String responecontenttype;
    private HttpHeader header;
    private String headjson;
    private String paramjson;
    private String bodyjson;
    private String resource;
    private String protocal;
    private String apistyle;
    private String variablesjson;
    private String deployunitvisittype;
    private String machineip;

    public List<ApicasesAssert> getApicasesAssertList() {
        return apicasesAssertList;
    }

    public void setApicasesAssertList(List<ApicasesAssert> apicasesAssertList) {
        this.apicasesAssertList = apicasesAssertList;
    }

    private List<ApicasesAssert> apicasesAssertList;

    public String getTestplanid() {
        return testplanid;
    }

    public void setTestplanid(String testplanid) {
        this.testplanid = testplanid;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getSlaverid() {
        return slaverid;
    }

    public void setSlaverid(String slaverid) {
        this.slaverid = slaverid;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getDeployunitid() {
        return deployunitid;
    }

    public void setDeployunitid(String deployunitid) {
        this.deployunitid = deployunitid;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getTestplanname() {
        return testplanname;
    }

    public void setTestplanname(String testplanname) {
        this.testplanname = testplanname;
    }

    public String getRequestmMthod() {
        return RequestmMthod;
    }

    public void setRequestmMthod(String requestmMthod) {
        RequestmMthod = requestmMthod;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getApistyle() {
        return apistyle;
    }

    public void setApistyle(String apistyle) {
        this.apistyle = apistyle;
    }

    public HttpParamers getParamers() {
        return paramers;
    }

    public void setParamers(HttpParamers paramers) {
        this.paramers = paramers;
    }

    public String getPostData() {
        return PostData;
    }

    public void setPostData(String postData) {
        PostData = postData;
    }

    public String getRequestcontenttype() {
        return requestcontenttype;
    }

    public void setRequestcontenttype(String requestcontenttype) {
        this.requestcontenttype = requestcontenttype;
    }

    public String getResponecontenttype() {
        return responecontenttype;
    }

    public void setResponecontenttype(String responecontenttype) {
        this.responecontenttype = responecontenttype;
    }

    public HttpHeader getHeader() {
        return header;
    }

    public void setHeader(HttpHeader header) {
        this.header = header;
    }

    public String getHeadjson() {
        return headjson;
    }

    public void setHeadjson(String headjson) {
        this.headjson = headjson;
    }

    public String getParamjson() {
        return paramjson;
    }

    public void setParamjson(String paramjson) {
        this.paramjson = paramjson;
    }

    public String getBodyjson() {
        return bodyjson;
    }

    public void setBodyjson(String bodyjson) {
        this.bodyjson = bodyjson;
    }

    public String getVariablesjson() {
        return variablesjson;
    }

    public void setVariablesjson(String variablesjson) {
        this.variablesjson = variablesjson;
    }

    public String getDeployunitvisittype() {
        return deployunitvisittype;
    }

    public void setDeployunitvisittype(String deployunitvisittype) {
        this.deployunitvisittype = deployunitvisittype;
    }

    public String getMachineip() {
        return machineip;
    }

    public void setMachineip(String machineip) {
        this.machineip = machineip;
    }
}
