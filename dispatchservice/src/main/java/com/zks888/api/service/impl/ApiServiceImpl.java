package com.zks888.api.service.impl;

import com.zks888.api.mapper.ApiMapper;
import com.zks888.api.entity.Api;
import com.zks888.api.service.ApiService;
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
public class ApiServiceImpl extends AbstractService<Api> implements ApiService {
    @Resource
    private ApiMapper apiMapper;

}
