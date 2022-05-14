package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ApicasesCondition;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/11/03
*/
public interface ApicasesConditionService extends Service<ApicasesCondition> {
    int ifexist(Condition condition);

    List<ApicasesCondition> findconditionWithName(final Map<String, Object> params);

}
