package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ConditionApi;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/05/30
*/
public interface ConditionApiService extends Service<ConditionApi> {
    List<ConditionApi> findtestconditionapiWithName(final Map<String, Object> params);

    void updateTestconditionapi(ConditionApi params);

    int ifexist(Condition condition);

    List<ConditionApi> getallTestconditionapi();
    List<ConditionApi> GetCaseListByConditionID(Long conditionid);

}
