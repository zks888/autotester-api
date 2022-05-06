package com.zoctan.api.dto;

import java.util.List;

/**
 * @author zks888
 * @date 2018/10/16
 */
public class TestPlanCaseReportStatics  {

  public String getTestplanName() {
    return TestplanName;
  }

  public void setTestplanName(String testplanName) {
    TestplanName = testplanName;
  }

  public List<Integer> getPlanCaseStatics() {
    return PlanCaseStatics;
  }

  public void setPlanCaseStatics(List<Integer> planCaseStatics) {
    PlanCaseStatics = planCaseStatics;
  }

  private String TestplanName;

  private List<Integer> PlanCaseStatics;


}
