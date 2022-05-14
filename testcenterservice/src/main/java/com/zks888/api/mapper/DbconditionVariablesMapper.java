package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.DbconditionVariables;

import java.util.List;
import java.util.Map;

public interface DbconditionVariablesMapper extends MyMapper<DbconditionVariables> {
    List<DbconditionVariables> getbyvariablesid(final Map<String, Object> params);
    void updatedbconditionvariables(DbconditionVariables params);

}
