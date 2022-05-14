package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Testcondition;

import java.util.List;
import java.util.Map;

public interface TestconditionMapper extends MyMapper<Testcondition> {
    List<Testcondition> findtestconditionWithName(final Map<String, Object> params);
    void updateTestcondition(Testcondition params);
    List<Testcondition> getallTestcondition();
    List<Testcondition> GetConditionByPlanIDAndConditionType(Long objectid,String conditiontype,String objecttype);
}
