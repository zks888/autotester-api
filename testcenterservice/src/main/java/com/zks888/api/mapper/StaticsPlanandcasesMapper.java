package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.StaticsPlanandcases;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaticsPlanandcasesMapper extends MyMapper<StaticsPlanandcases> {
    List<StaticsPlanandcases> getplanstatics(@Param("statics_date") String StaticDate);

    void savestaticsplanandcases(@Param("casedataList") final List<StaticsPlanandcases> testcase);

}
