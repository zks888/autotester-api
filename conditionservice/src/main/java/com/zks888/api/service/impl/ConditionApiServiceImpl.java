package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ConditionApi;
import com.zks888.api.mapper.ConditionApiMapper;
import com.zks888.api.service.ConditionApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/05/30
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionApiServiceImpl extends AbstractService<ConditionApi> implements ConditionApiService {
@Resource
private ConditionApiMapper conditionApiMapper;

    @Override
    public List<ConditionApi> findtestconditionapiWithName(Map<String, Object> params) {
        return conditionApiMapper.findtestconditionapiWithName(params);
    }

    @Override
    public void updateTestconditionapi(ConditionApi params) {
        conditionApiMapper.updateTestconditionapi(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public List<ConditionApi> getallTestconditionapi() {
        return conditionApiMapper.getallTestconditionapi();
    }

    @Override
    public List<ConditionApi> GetCaseListByConditionID(Long conditionid) {
        return conditionApiMapper.GetCaseListByConditionID(conditionid);
    }
}
