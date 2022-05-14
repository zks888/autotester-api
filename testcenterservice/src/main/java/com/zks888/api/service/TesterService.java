package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Tester;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/22
*/
public interface TesterService extends Service<Tester> {
    /**
     * 按条件查询测试人员名和值
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Tester> findtesterName(String params);

    /**
     * 按条件查询测试人员内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Tester> findTesterWithName(final Map<String, Object> params);

    /**
     * 更新测试人员内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateTester(Tester params);

}
