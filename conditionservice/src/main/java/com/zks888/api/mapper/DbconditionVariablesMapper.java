package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.DbconditionVariables;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DbconditionVariablesMapper extends MyMapper<DbconditionVariables> {
    List<DbconditionVariables> getbyvariablesid(final Map<String, Object> params);
    List<DbconditionVariables> getbyconditionid(@Param("dbconditionid") long dbconditionid);
    void updatedbconditionvariables(DbconditionVariables params);

}
