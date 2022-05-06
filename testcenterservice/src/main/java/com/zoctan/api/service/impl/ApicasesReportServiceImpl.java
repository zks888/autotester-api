package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApicasesReportMapper;
import com.zoctan.api.entity.ApicasesReport;
import com.zoctan.api.service.ApicasesReportService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/10/16
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesReportServiceImpl extends AbstractService<ApicasesReport> implements ApicasesReportService {
@Resource
private ApicasesReportMapper apicasesReportMapper;

    @Override
    public List<ApicasesReport> findApicasereportWithName(Map<String, Object> params) {
        return apicasesReportMapper.findApicasereportWithName(params);
    }

    @Override
    public List<ApicasesReport> findApicasereportWithNameandStatus(long planid, String status, String batchname) {
        return apicasesReportMapper.findApicasereportWithNameandStatus(planid, status, batchname);
    }


    @Override
    public Long getApicasetotalsWithName(Map<String, Object> params) {
        return apicasesReportMapper.getApicasetotalsWithName(params);
    }

    @Override
    public Long getApicasenumbystatus(Map<String, Object> params) {
        return apicasesReportMapper.getApicasenumbystatus(params);
    }

    @Override
    public Long getApicasecosttimes(Map<String, Object> params) {
        return apicasesReportMapper.getApicasecosttimes(params);
    }

    @Override
    public List<ApicasesReport> listallresult() {
        return apicasesReportMapper.listallresult();
    }

    @Override
    public List<ApicasesReport> getreportbyplanandbatchstatus(long planid, String status, String batchname) {
        return apicasesReportMapper.getreportbyplanandbatchstatus(planid,status,batchname);
    }
}
