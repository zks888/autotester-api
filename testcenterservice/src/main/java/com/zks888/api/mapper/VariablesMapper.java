package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Enviroment;
import com.zks888.api.entity.Variables;

import java.util.List;
import java.util.Map;

public interface VariablesMapper extends MyMapper<Variables> {
    List<Variables> findvariablesWithName(final Map<String, Object> params);
    void updatevariables(Variables params);

}
