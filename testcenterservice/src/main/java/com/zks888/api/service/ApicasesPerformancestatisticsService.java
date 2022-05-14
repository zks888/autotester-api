package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ApicasesPerformancestatistics;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/12/07
*/
public interface ApicasesPerformancestatisticsService extends Service<ApicasesPerformancestatistics> {
    List<ApicasesPerformancestatistics> listallresult();

    List<ApicasesPerformancestatistics> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesPerformancestatistics> getresultbyidandname(long testplanid, String batchname);
}
