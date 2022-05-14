package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.DbconditionVariables;
import com.zks888.api.mapper.DbconditionVariablesMapper;
import com.zks888.api.service.DbconditionVariablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/25
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DbconditionVariablesServiceImpl extends AbstractService<DbconditionVariables> implements DbconditionVariablesService {
@Resource
private DbconditionVariablesMapper dbconditionVariablesMapper;

    @Override
    public List<DbconditionVariables> getbyvariablesid(Map<String, Object> params) {
        return dbconditionVariablesMapper.getbyvariablesid(params);
    }

    @Override
    public List<DbconditionVariables> getbyconditionid(long dbconditionid) {
        return dbconditionVariablesMapper.getbyconditionid(dbconditionid);
    }


    @Override
    public void updatedbconditionvariables(DbconditionVariables params) {
        dbconditionVariablesMapper.updatedbconditionvariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
