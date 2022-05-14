package com.zks888.api.service;

import com.zks888.api.entity.ApicasesVariables;
import com.zks888.api.entity.DbconditionVariables;
import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Dbvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/25
*/
public interface DbconditionVariablesService extends Service<DbconditionVariables> {
    List<DbconditionVariables> getbyvariablesid(final Map<String, Object> params);

    void updatedbconditionvariables(DbconditionVariables params);

    int ifexist(Condition condition);

}
