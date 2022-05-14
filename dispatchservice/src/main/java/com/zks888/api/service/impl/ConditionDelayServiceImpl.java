package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ConditionDelay;
import com.zks888.api.mapper.ConditionDelayMapper;
import com.zks888.api.service.ConditionDelayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/30
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionDelayServiceImpl extends AbstractService<ConditionDelay> implements ConditionDelayService {
@Resource
private ConditionDelayMapper conditionDelayMapper;

    @Override
    public List<ConditionDelay> findtestconditiondelayWithName(Map<String, Object> params) {
        return conditionDelayMapper.findtestconditiondelayWithName(params);
    }

    @Override
    public void updateTestconditiondelay(ConditionDelay params) {
        conditionDelayMapper.updateTestconditiondelay(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
