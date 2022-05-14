package com.zks888.api.service;

import com.zks888.api.entity.ConditionApi;
import com.zks888.api.entity.ConditionDelay;
import com.zks888.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/30
*/
public interface ConditionDelayService extends Service<ConditionDelay> {
    List<ConditionDelay> findtestconditiondelayWithName(final Map<String, Object> params);

    void updateTestconditiondelay(ConditionDelay params);


    int ifexist(Condition condition);
}
