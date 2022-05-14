package com.zks888.api.service.impl;

import com.zks888.api.mapper.PerformancereportsourceMapper;
import com.zks888.api.entity.Performancereportsource;
import com.zks888.api.service.PerformancereportsourceService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zks888
 * @date 2022/04/19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PerformancereportsourceServiceImpl extends AbstractService<Performancereportsource> implements PerformancereportsourceService {
    @Resource
    private PerformancereportsourceMapper performancereportsourceMapper;

}
