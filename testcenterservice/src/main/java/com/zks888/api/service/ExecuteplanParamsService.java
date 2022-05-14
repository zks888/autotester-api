package com.zks888.api.service;

import com.zks888.api.entity.ApiParams;
import com.zks888.api.entity.ExecuteplanParams;
import com.zks888.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/12/19
*/
public interface ExecuteplanParamsService extends Service<ExecuteplanParams> {
    void updateParams(ExecuteplanParams params);

    List<ExecuteplanParams> getParamsbyepid(final Map<String, Object> params);

    int ifexist(Condition condition);

    void removeplanparams(final long executeplanid);

}
