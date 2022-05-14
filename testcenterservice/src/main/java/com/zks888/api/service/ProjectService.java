package com.zks888.api.service;

import com.zks888.api.entity.Enviroment;
import com.zks888.api.entity.Project;
import com.zks888.api.core.service.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/27
*/
public interface ProjectService extends Service<Project> {
    List<Project> findProjectWithName(final Map<String, Object> params);
    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateProject(Project params);


    int ifexist(Condition condition);
}
