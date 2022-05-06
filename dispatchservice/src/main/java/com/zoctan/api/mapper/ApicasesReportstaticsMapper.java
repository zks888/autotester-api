package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesReportstatics;

import java.util.List;
import java.util.Map;

public interface ApicasesReportstaticsMapper extends MyMapper<ApicasesReportstatics> {
    List<ApicasesReportstatics> getapicasesreportstatics(final Map<String, Object> params);

    List<ApicasesReportstatics> getdeployunitapicasesreportstatics(final Map<String, Object> params);

}