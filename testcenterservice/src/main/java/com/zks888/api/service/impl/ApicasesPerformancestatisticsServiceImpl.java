package com.zks888.api.service.impl;

import com.zks888.api.mapper.ApicasesPerformancestatisticsMapper;
import com.zks888.api.entity.ApicasesPerformancestatistics;
import com.zks888.api.service.ApicasesPerformancestatisticsService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/12/07
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesPerformancestatisticsServiceImpl extends AbstractService<ApicasesPerformancestatistics> implements ApicasesPerformancestatisticsService {
@Resource
private ApicasesPerformancestatisticsMapper apicasesPerformancestatisticsMapper;

    @Override
    public List<ApicasesPerformancestatistics> listallresult() {
        return apicasesPerformancestatisticsMapper.listallresult();
    }

    @Override
    public List<ApicasesPerformancestatistics> findApicasereportWithName(Map<String, Object> params) {
        return apicasesPerformancestatisticsMapper.findApicasereportWithName(params);
    }

    @Override
    public List<ApicasesPerformancestatistics> getresultbyidandname(long testplanid, String batchname) {
        return apicasesPerformancestatisticsMapper.getresultbyidandname(testplanid, batchname);
    }
}
