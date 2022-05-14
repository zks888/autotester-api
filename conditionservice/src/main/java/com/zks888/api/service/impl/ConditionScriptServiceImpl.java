package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ConditionScript;
import com.zks888.api.mapper.ConditionScriptMapper;
import com.zks888.api.service.ConditionScriptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/11/20
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionScriptServiceImpl extends AbstractService<ConditionScript> implements ConditionScriptService {
@Resource
private ConditionScriptMapper conditionScriptMapper;

    @Override
    public List<ConditionScript> findtestconditionapiWithName(Map<String, Object> params) {
        return conditionScriptMapper.findtestconditionscriptWithName(params);
    }

    @Override
    public ConditionScript findtestconditionscriptwithid(Long conditionid) {
        return conditionScriptMapper.findtestconditionscriptwithid(conditionid);
    }

    @Override
    public void updateTestconditionapi(ConditionScript params) {
        conditionScriptMapper.updateTestconditionScript(params);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }
}
