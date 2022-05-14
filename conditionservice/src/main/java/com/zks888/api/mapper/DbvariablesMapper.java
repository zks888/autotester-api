package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Dbvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface DbvariablesMapper extends MyMapper<Dbvariables> {
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
