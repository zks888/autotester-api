package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.Testvariables;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/07/14
*/
public interface TestvariablesService extends Service<Testvariables> {

    List<Testvariables> findtestvariablesWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatetestvariables(Testvariables params);


    int ifexist(Condition condition);

}
