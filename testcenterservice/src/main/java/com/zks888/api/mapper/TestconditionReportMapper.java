package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.TestconditionReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TestconditionReportMapper extends MyMapper<TestconditionReport> {
    List<TestconditionReport> findTestconditionReportWithName(final Map<String, Object> params);
    List<TestconditionReport> findconditionreport(@Param("testplanid")long planid, @Param("batchname")String batchname);

}
