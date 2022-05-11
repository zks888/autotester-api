package com.zoctan.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApicasesReportPerformance;
import com.zoctan.api.mapper.ApicasesReportPerformanceMapper;
import com.zoctan.api.service.ApicasesReportPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/12/14
*/
@Service
@DS("testreport")
@Transactional(rollbackFor = Exception.class)
public class ApicasesReportPerformanceServiceImpl extends AbstractService<ApicasesReportPerformance> implements ApicasesReportPerformanceService {
@Resource
private ApicasesReportPerformanceMapper apicasesReportPerformanceMapper;

    @Override
    public List<ApicasesReportPerformance> findApicasereportWithName(Map<String, Object> params) {
        return apicasesReportPerformanceMapper.findApicasereportWithName(params);
    }

    @Override
    public List<ApicasesReportPerformance> finddynamicresult(long planid, String batchname, String tableName) {
        return apicasesReportPerformanceMapper.finddynamicresult(planid, batchname, tableName);
    }

    @Override
    public List<ApicasesReportPerformance> finddynamicresultbystatus(long planid, String batchname, String tableName, String status) {
        return apicasesReportPerformanceMapper.finddynamicresultbystatus(planid, batchname, tableName, status);
    }

    @Override
    public List<ApicasesReportPerformance> listallresult() {
        return apicasesReportPerformanceMapper.listallresult();
    }
}
