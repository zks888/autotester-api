package com.zks888.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 12:02
 */
@Setter
@Getter
public class FunctionCaseStatis {
    public float getCaseNum() {
        return CaseNum;
    }

    public void setCaseNum(int caseNum) {
        CaseNum = caseNum;
    }

    public int getExecCaseNums() {
        return ExecCaseNums;
    }

    public void setExecCaseNums(int execCaseNums) {
        ExecCaseNums = execCaseNums;
    }

    public float getSuccessCaseNums() {
        return SuccessCaseNums;
    }

    public void setSuccessCaseNums(int successCaseNums) {
        SuccessCaseNums = successCaseNums;
    }

    public float getFailCaseNums() {
        return FailCaseNums;
    }

    public void setFailCaseNums(int failCaseNums) {
        FailCaseNums = failCaseNums;
    }

    public int getNotExecCaseNums() {
        return NotExecCaseNums;
    }

    public void setNotExecCaseNums(int notExecCaseNums) {
        NotExecCaseNums = notExecCaseNums;
    }

    private float CaseNum;
    private int ExecCaseNums;
    private float SuccessCaseNums;
    private float FailCaseNums;
    private int NotExecCaseNums;

    public String getSuccessrate() {
        return successrate;
    }

    public void setSuccessrate(String successrate) {
        this.successrate = successrate;
    }

    public String getFailrate() {
        return failrate;
    }

    public void setFailrate(String failrate) {
        this.failrate = failrate;
    }

    private String successrate;
    private String failrate;

    public float getCosttime() {
        return costtime;
    }

    public void setCosttime(float costtime) {
        this.costtime = costtime;
    }

    private float costtime;


}
