package com.zks888.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ApicasesReportPerformance;
import com.zks888.api.mapper.ApicasesReportPerformanceMapper;
import com.zks888.api.service.ApicasesReportPerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    public List<ApicasesReportPerformance> listallresult() {
        return apicasesReportPerformanceMapper.listallresult();
    }

    @Override
    public void adddynamiccaseperformancereport(long caseid, long testplanid, String batchname, long slaverid, String status, String respone, String assertvalue, long runtime, String expect, String errorinfo, Date create_time, Date lastmodify_time, String creator, String requestheader, String requestdatas, String url, String requestmethod, String tablename) {
        apicasesReportPerformanceMapper.adddynamiccaseperformancereport(caseid, testplanid, batchname, slaverid, status, respone, assertvalue, runtime, expect, errorinfo, create_time, lastmodify_time, creator, requestheader, requestdatas, url, requestmethod, tablename);
    }
}
