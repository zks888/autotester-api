package com.zks888.api.service.impl;

import com.zks888.api.entity.Dbvariables;
import com.zks888.api.mapper.ScriptvariablesMapper;
import com.zks888.api.entity.Scriptvariables;
import com.zks888.api.service.ScriptvariablesService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/04/02
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ScriptvariablesServiceImpl extends AbstractService<Scriptvariables> implements ScriptvariablesService {
@Resource
private ScriptvariablesMapper scriptvariablesMapper;


    @Override
    public List<Scriptvariables> findscriptvariablesWithName(Map<String, Object> params) {
        return scriptvariablesMapper.findscriptvariablesWithName(params);
    }

    @Override
    public void updatescriptvariables(Scriptvariables params) {
        scriptvariablesMapper.updatescriptvariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }


}
