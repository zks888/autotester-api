package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.TestvariablesValue;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/07/17
*/
public interface TestvariablesValueService extends Service<TestvariablesValue> {
    List<TestvariablesValue> findtestvariablesvalueWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatetestvariablesvalue(TestvariablesValue params);

    TestvariablesValue gettestvariablesvalue(Long planid,Long caseid,String variablesname,String batchname);
    int ifexist(Condition condition);
    List<TestvariablesValue> gettvlist(Long planid,String batchname,String variablestype);
}
