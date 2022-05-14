package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Enviroment;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/18
*/
public interface EnviromentService extends Service<Enviroment> {

    /**
     * 按条件查询字典名和值
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Enviroment> findEnviromentName(String params);

    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Enviroment> findEnviromentWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateEnviroment(Enviroment params);


    int ifexist(Condition condition);

    Integer getenviromentnum();


}
