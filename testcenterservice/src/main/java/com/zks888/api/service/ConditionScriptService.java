package com.zks888.api.service;

import com.zks888.api.entity.ConditionApi;
import com.zks888.api.entity.ConditionScript;
import com.zks888.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/11/20
*/
public interface ConditionScriptService extends Service<ConditionScript> {
    List<ConditionScript> findtestconditionapiWithName(final Map<String, Object> params);

    void updateTestconditionapi(ConditionScript params);
    void deletesubconditionbyconid(Long conditionid);

    int ifexist(Condition condition);
}