package com.zks888.api.service.impl;

import com.zks888.api.mapper.ApicasesPerformancestatisticsMapper;
import com.zks888.api.entity.ApicasesPerformancestatistics;
import com.zks888.api.service.ApicasesPerformancestatisticsService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Season
* @date 2020/12/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesPerformancestatisticsServiceImpl extends AbstractService<ApicasesPerformancestatistics> implements ApicasesPerformancestatisticsService {
@Resource
private ApicasesPerformancestatisticsMapper apicasesPerformancestatisticsMapper;

}
