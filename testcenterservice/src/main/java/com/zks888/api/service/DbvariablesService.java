package com.zks888.api.service;

import com.zks888.api.entity.Dbvariables;
import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Testvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/25
*/
public interface DbvariablesService extends Service<Dbvariables> {
    List<Dbvariables> finddbvariablesWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatedbvariables(Dbvariables params);


    int ifexist(Condition condition);

}
