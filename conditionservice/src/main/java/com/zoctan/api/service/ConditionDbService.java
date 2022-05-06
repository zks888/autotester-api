package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ConditionDb;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/05/30
*/
public interface ConditionDbService extends Service<ConditionDb> {

    List<ConditionDb> finddbconditionWithName(final Map<String, Object> params);
    List<ConditionDb> GetDBConditionByConditionID(long conditionid);
    void updateTestconditiondb(ConditionDb params);

    int ifexist(Condition condition);

}
