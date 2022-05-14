package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ApicasesVariables;
import com.zks888.api.mapper.ApicasesVariablesMapper;
import com.zks888.api.service.ApicasesVariablesService;
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
public class ApicasesVariablesServiceImpl extends AbstractService<ApicasesVariables> implements ApicasesVariablesService {
@Resource
private ApicasesVariablesMapper apicasesVariablesMapper;

    @Override
    public List<ApicasesVariables> findApicasesVariablesWithName(Map<String, Object> params) {
        return apicasesVariablesMapper.findApicasesVariablesWithName(params);
    }

    @Override
    public void updateApicasesVariables(ApicasesVariables params) {
        apicasesVariablesMapper.updateApicasesVariables(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
