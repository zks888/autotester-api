package com.zks888.api.service;

import com.zks888.api.entity.Dbvariables;
import com.zks888.api.entity.Scriptvariables;
import com.zks888.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/04/02
*/
public interface ScriptvariablesService extends Service<Scriptvariables> {

    List<Scriptvariables> findscriptvariablesWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatescriptvariables(Scriptvariables params);


    int ifexist(Condition condition);
}
