package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Dispatch;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/11/21
*/
public interface DispatchService extends Service<Dispatch> {
    List<Dispatch> findDispatchWithName(final Map<String, Object> params);

    List<Dispatch> getDispatchWithstatus(long testplanid,String batchname,String status);

    int ifexist(Condition condition);

}
