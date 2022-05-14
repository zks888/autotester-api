package com.zks888.api.service;

import com.zks888.api.entity.ConditionDb;
import com.zks888.api.entity.ConditionOrder;
import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ExecuteplanTestcase;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/01/03
*/
public interface ConditionOrderService extends Service<ConditionOrder> {

    List<ConditionOrder> findconditionorderWithName(final Map<String, Object> params);
    List<ConditionOrder> findconditionorderWithid(final Map<String, Object> params);
    List<ConditionOrder> findconditionorderWithidandtype(Long conditionid,String subconditiontype);
    void saveconditionorder(final List<ConditionOrder> conditionOrderList);
    void deleteconditionorderbyconid(Long conditionid);
    void deleteconditionorderbysubconid(Long conditionid,Long subconditionid,String subconditiontype);
}
