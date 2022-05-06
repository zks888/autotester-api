package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.dto.TestplanCase;
import com.zoctan.api.entity.JmeterPerformanceObject;

import java.io.IOException;

/**
* @author zks888
* @date 2020/04/17
*/
public interface TestPlanCaseService extends Service<TestplanCase> {

    void ExecuteHttpPerformancePlanCase(JmeterPerformanceObject jmeterPerformanceObject, String DeployName, String JmeterPath, String JmxPath, String JmxCaseName, String JmeterPerformanceReportPath, String JmeterPerformanceReportLogFilePath, Long Thread, Long Loop,String creator) throws IOException;

    void ExecuteHttpPlanFunctionCase(Long Slaverid, String JmeterPath,String JmxPath,String DispatchIds,String MysqlUrl,String MysqlUsername,String MysqlPassword,int JmeterLogFileNum) throws IOException;
}
