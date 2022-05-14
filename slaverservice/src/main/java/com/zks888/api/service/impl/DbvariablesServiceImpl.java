package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Dbvariables;
import com.zks888.api.mapper.DbvariablesMapper;
import com.zks888.api.service.DbvariablesService;
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
public class DbvariablesServiceImpl extends AbstractService<Dbvariables> implements DbvariablesService {
@Resource
private DbvariablesMapper dbvariablesMapper;

    @Override
    public List<Dbvariables> finddbvariablesWithName(Map<String, Object> params) {
        return dbvariablesMapper.finddbvariablesWithName(params);
    }

    @Override
    public void updatedbvariables(Dbvariables params) {
        dbvariablesMapper.updatedbvariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
