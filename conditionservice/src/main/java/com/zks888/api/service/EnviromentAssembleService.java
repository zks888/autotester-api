package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.EnviromentAssemble;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/11/06
*/
public interface EnviromentAssembleService extends Service<EnviromentAssemble> {

    List<EnviromentAssemble> findassembleWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateenviromentassemble(EnviromentAssemble params);


    int ifexist(Condition condition);

}
