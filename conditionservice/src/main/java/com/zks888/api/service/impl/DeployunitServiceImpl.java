package com.zks888.api.service.impl;

import com.zks888.api.mapper.DeployunitMapper;
import com.zks888.api.entity.Deployunit;
import com.zks888.api.service.DeployunitService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
        return deployunitMapper.findDeployNameValueWithCode(params);
    }
}
