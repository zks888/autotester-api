package com.zks888.api.service.impl;

import com.zks888.api.mapper.ProjectMapper;
import com.zks888.api.entity.Project;
import com.zks888.api.service.ProjectService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/03/27
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService {
@Resource
private ProjectMapper projectMapper;

    @Override
    public List<Project> findProjectWithName(Map<String, Object> params) {
        return projectMapper.findProjectWithName(params);
    }

    @Override
    public void updateProject(Project params) {
        projectMapper.updateProject(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
