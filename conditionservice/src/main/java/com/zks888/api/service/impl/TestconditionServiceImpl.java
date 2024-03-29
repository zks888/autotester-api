package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.core.service.HttpHeader;
import com.zks888.api.core.service.HttpParamers;
import com.zks888.api.entity.*;
import com.zks888.api.mapper.TestconditionMapper;
import com.zks888.api.service.TestconditionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/05/31
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestconditionServiceImpl extends AbstractService<Testcondition> implements TestconditionService {
@Resource
private TestconditionMapper testconditionMapper;

    @Override
    public List<Testcondition> findtestconditionWithName(Map<String, Object> params) {
        return testconditionMapper.findtestconditionWithName(params);
    }

    @Override
    public void updateTestcondition(Testcondition params) {
        testconditionMapper.updateTestcondition(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public List<Testcondition> getallTestcondition() {
        return testconditionMapper.getallTestcondition();
    }

    @Override
    public List<Testcondition> GetConditionByPlanIDAndConditionType(Long objectid,String conditiontype,String objecttype) {
        return testconditionMapper.GetConditionByPlanIDAndConditionType(objectid,conditiontype,objecttype);
    }

}
