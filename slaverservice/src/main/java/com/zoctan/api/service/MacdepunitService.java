package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Macdepunit;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/05/21
*/
public interface MacdepunitService extends Service<Macdepunit> {
    /**
     * 按环境名或者服务器名获取服务器发布单元内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    List<Macdepunit> findMacAndDepWithName(final Map<String, Object> params);

    /**
     * 更新服务器发布单元内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    void updateMacAndDep(Macdepunit params);

    int ifexist(Condition condition);

    Integer findmachinenumbyenvidanddeployid(long envid,long depunitid);

    Macdepunit getmacdepbyenvidanddepid(long envid,long depunitid);
}
