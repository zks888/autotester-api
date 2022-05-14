package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Testvariables;
import com.zks888.api.mapper.TestvariablesMapper;
import com.zks888.api.service.TestvariablesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/07/14
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestvariablesServiceImpl extends AbstractService<Testvariables> implements TestvariablesService {
@Resource
private TestvariablesMapper testvariablesMapper;

    @Override
    public List<Testvariables> findtestvariablesWithName(Map<String, Object> params) {
        return testvariablesMapper.findtestvariablesWithName(params);
    }

    @Override
    public void updatetestvariables(Testvariables params) {
        testvariablesMapper.updatetestvariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
