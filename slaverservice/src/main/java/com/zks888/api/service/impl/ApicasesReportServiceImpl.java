package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ApicasesReport;
import com.zks888.api.mapper.ApicasesReportMapper;
import com.zks888.api.service.ApicasesReportService;
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
    public List<ApicasesReport> listallresult() {
        return apicasesReportMapper.listallresult();
    }

    @Override
    public void addcasereport(ApicasesReport ar) {
        apicasesReportMapper.addcasereport(ar);
    }
}
