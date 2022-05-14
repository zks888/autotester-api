package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Slaver;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/09/26
*/
public interface SlaverService extends Service<Slaver> {


    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Slaver> findslaverWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateSlaver(Slaver params);

    List<Slaver> findslaveralive(String stype, String status);

    Integer getslavernum();


}
