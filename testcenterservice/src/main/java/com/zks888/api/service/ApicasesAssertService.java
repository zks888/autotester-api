package com.zks888.api.service;

import com.zks888.api.entity.ApicasesAssert;
import com.zks888.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/10/29
*/
public interface ApicasesAssertService extends Service<ApicasesAssert> {

    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApicasesAssert> findAssertWithName(final Map<String, Object> params);
    List<ApicasesAssert> findAssertbycaseid(final Map<String, Object> params);


    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateAssert(ApicasesAssert params);


    int ifexist(Condition condition);


}
