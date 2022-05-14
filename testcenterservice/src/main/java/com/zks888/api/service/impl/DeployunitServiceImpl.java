package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Deployunit;
import com.zks888.api.mapper.DeployunitMapper;
import com.zks888.api.service.DeployunitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DeployunitServiceImpl extends AbstractService<Deployunit> implements DeployunitService {
@Resource
private DeployunitMapper deployunitMapper;

    @Override
    public Deployunit findDeployNameValueWithCode(String params) {
        return this.deployunitMapper.findDeployNameValueWithCode(params);
    }

    @Override
    public List<Deployunit> findDeployWithName(Map<String, Object> params) {
        return this.deployunitMapper.findDeployWithName(params);
    }

    @Override
    public void updateDeploy(Deployunit params)
    {
        deployunitMapper.updateDeploy(params);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }

    @Override
    public Integer getdeploynum() {
        return deployunitMapper.getdeploynum();
    }

    @Override
    public List<String> getstaticsdeploynames() {
        return deployunitMapper.getstaticsdeploynames();
    }

}
