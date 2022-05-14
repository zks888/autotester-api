package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Testcondition;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/05/31
*/
public interface TestconditionService extends Service<Testcondition> {
    List<Testcondition> findtestconditionWithName(final Map<String, Object> params);

    void updateTestcondition(Testcondition params);

    int ifexist(Condition condition);

    List<Testcondition> getallTestcondition();

    List<Testcondition> GetConditionByPlanIDAndConditionType(Long objectid,String conditiontype,String objecttype);

}
