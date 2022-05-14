package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Scriptvariables;

import java.util.List;
import java.util.Map;

public interface ScriptvariablesMapper extends MyMapper<Scriptvariables> {
    List<Scriptvariables> findscriptvariablesWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatescriptvariables(Scriptvariables params);
}
