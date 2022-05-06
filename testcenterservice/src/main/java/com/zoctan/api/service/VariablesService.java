package com.zoctan.api.service;

import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.entity.Variables;
import com.zoctan.api.core.service.Service;
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
