package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ApicasesReport;

import java.util.List;
import java.util.Map;

public interface ApicasesReportMapper extends MyMapper<ApicasesReport> {
    List<ApicasesReport> findApicasereportWithName(final Map<String, Object> params);
    List<ApicasesReport> listallresult();
    void addcasereport(ApicasesReport ar);
}
