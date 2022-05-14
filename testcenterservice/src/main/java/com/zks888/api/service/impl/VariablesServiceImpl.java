package com.zks888.api.service.impl;

import com.zks888.api.entity.Enviroment;
import com.zks888.api.mapper.VariablesMapper;
import com.zks888.api.entity.Variables;
import com.zks888.api.service.VariablesService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/02/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class VariablesServiceImpl extends AbstractService<Variables> implements VariablesService {
@Resource
private VariablesMapper variablesMapper;


    @Override
    public List<Variables> findvariablesWithName(Map<String, Object> params) {
        return variablesMapper.findvariablesWithName(params);
    }

    @Override
    public void updatevariables(Variables params) {
        variablesMapper.updatevariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }
}
