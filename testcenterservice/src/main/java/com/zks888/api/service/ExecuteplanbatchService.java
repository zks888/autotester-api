package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Executeplanbatch;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/10/22
*/
public interface ExecuteplanbatchService extends Service<Executeplanbatch> {
    int ifexist(Condition condition);

    List<Executeplanbatch> getbatchbyplan(Long executeplanid);

    List<Executeplanbatch> getallexplanbatch();

    List<Executeplanbatch> findexplanbatchWithName(final Map<String, Object> params);

    List<Executeplanbatch> getrecentbatchbyid(Long executeplanid);



}
