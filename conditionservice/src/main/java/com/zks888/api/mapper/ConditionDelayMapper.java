package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ConditionDelay;

import java.util.List;
import java.util.Map;

public interface ConditionDelayMapper extends MyMapper<ConditionDelay> {
    List<ConditionDelay> findtestconditiondelayWithName(final Map<String, Object> params);

    void updateTestconditiondelay(ConditionDelay params);

    List<ConditionDelay> GetDelayConditionByConditionID(long conditionid);

}
