package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Dictionary;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/16
*/
public interface DictionaryService extends Service<Dictionary> {

    /**
     * 按条件查询字典名和值
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Dictionary> findDicNameValueWithCode(String params);

    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Dictionary> findDicWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateDic(Dictionary params);


}
