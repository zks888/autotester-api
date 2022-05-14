package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ApicasesReportstatics;

import java.util.List;
import java.util.Map;

public interface ApicasesReportstaticsMapper extends MyMapper<ApicasesReportstatics> {
    List<ApicasesReportstatics> getapicasesreportstatics(final Map<String, Object> params);

    List<ApicasesReportstatics> getdeployunitapicasesreportstatics(final Map<String, Object> params);

}
