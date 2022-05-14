package com.zks888.api.service.impl;

import com.zks888.api.mapper.ApicasesReportstaticsMapper;
import com.zks888.api.entity.ApicasesReportstatics;
import com.zks888.api.service.ApicasesReportstaticsService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zks888
* @date 2021/04/01
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesReportstaticsServiceImpl extends AbstractService<ApicasesReportstatics> implements ApicasesReportstaticsService {
@Resource
private ApicasesReportstaticsMapper apicasesReportstaticsMapper;

    @Override
    public List<ApicasesReportstatics> getapicasesreportstaticsbypandb(long planid, String batchname) {
        return apicasesReportstaticsMapper.getapicasesreportstaticsbypandb(planid, batchname);
    }
}
