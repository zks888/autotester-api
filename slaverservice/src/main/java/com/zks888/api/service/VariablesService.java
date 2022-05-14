package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Variables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/02/15
*/
public interface VariablesService extends Service<Variables> {

    List<Variables> findvariablesWithName(final Map<String, Object> params);

    void updatevariables(Variables params);
    int ifexist(Condition condition);

}
