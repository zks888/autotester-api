package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.TestconditionReport;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/07/17
*/
public interface TestconditionReportService extends Service<TestconditionReport> {

    List<TestconditionReport> findTestconditionReportWithName(final Map<String, Object> params);
    int ifexist(Condition condition);

}
