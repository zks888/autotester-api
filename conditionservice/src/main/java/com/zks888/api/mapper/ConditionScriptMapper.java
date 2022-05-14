package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ConditionScript;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ConditionScriptMapper extends MyMapper<ConditionScript> {
    List<ConditionScript> findtestconditionscriptWithName(final Map<String, Object> params);

    ConditionScript findtestconditionscriptwithid(Long conditionid);

    void updateTestconditionScript(ConditionScript params);

    int ifexist(Condition condition);
}
