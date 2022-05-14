package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ApicasesCondition;

import java.util.List;
import java.util.Map;

public interface ApicasesConditionMapper extends MyMapper<ApicasesCondition> {
    List<ApicasesCondition> findconditionWithName(final Map<String, Object> params);

}
