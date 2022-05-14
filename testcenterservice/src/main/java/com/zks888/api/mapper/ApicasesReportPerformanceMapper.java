package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ApicasesReportPerformance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApicasesReportPerformanceMapper extends MyMapper<ApicasesReportPerformance> {
    List<ApicasesReportPerformance> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesReportPerformance> finddynamicresult(@Param("testplanid") long planid,@Param("batchname") String batchname,@Param("tableName") String tableName);
    List<ApicasesReportPerformance> finddynamicresultbystatus(@Param("testplanid") long planid,@Param("batchname") String batchname,@Param("tableName") String tableName,@Param("status")String status);
    List<ApicasesReportPerformance> listallresult();
    List<ApicasesReportPerformance> listallresultbyplanid(@Param("testplanid") long testplanid);

}
