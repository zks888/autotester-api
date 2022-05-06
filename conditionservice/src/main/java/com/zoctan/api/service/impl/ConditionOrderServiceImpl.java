package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ConditionOrder;
import com.zoctan.api.mapper.ConditionOrderMapper;
import com.zoctan.api.service.ConditionOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/01/03
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConditionOrderServiceImpl extends AbstractService<ConditionOrder> implements ConditionOrderService {
@Resource
private ConditionOrderMapper conditionOrderMapper;

    @Override
    public List<ConditionOrder> findconditionorderWithName(Map<String, Object> params) {
        return conditionOrderMapper.findconditionorderWithName(params);
    }

    @Override
    public List<ConditionOrder> findconditionorderWithid(Map<String, Object> params) {
        return conditionOrderMapper.findconditionorderWithid(params);
    }

    @Override
    public List<ConditionOrder> findconditionorderWithidandtype(Long conditionid, String subconditiontype) {
        return conditionOrderMapper.findconditionorderWithidandtype(conditionid, subconditiontype);
    }

    @Override
    public void saveconditionorder(List<ConditionOrder> conditionOrderList) {
        conditionOrderMapper.saveconditionorder(conditionOrderList);
    }

    @Override
    public void deleteconditionorderbyconid(Long conditionid) {
        conditionOrderMapper.deleteconditionorderbyconid(conditionid);
    }

    @Override
    public void deleteconditionorderbysubconid(Long conditionid,Long subconditionid) {
        conditionOrderMapper.deleteconditionorderbysubconid(conditionid,subconditionid);
    }
}
