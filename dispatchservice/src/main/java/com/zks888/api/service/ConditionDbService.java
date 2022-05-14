package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ConditionApi;
import com.zks888.api.entity.ConditionDb;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/05/30
*/
public interface ConditionDbService extends Service<ConditionDb> {

    List<ConditionDb> finddbconditionWithName(final Map<String, Object> params);
    void updateTestconditiondb(ConditionDb params);

    int ifexist(Condition condition);
    List<ConditionDb> GetCaseListByConditionID(Long conditionid);


}
