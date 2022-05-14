package com.zks888.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zks888.api.mapper.RouteperformancereportMapper;
import com.zks888.api.entity.Routeperformancereport;
import com.zks888.api.service.RouteperformancereportService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zks888
 * @date 2022/04/13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RouteperformancereportServiceImpl extends AbstractService<Routeperformancereport> implements RouteperformancereportService {
    @Resource
    private RouteperformancereportMapper routeperformancereportMapper;

}
