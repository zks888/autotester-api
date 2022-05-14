package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.StaticsDeployunitandcases;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaticsDeployunitandcasesMapper extends MyMapper<StaticsDeployunitandcases> {
    List<StaticsDeployunitandcases> getdeployunitstatics(@Param("statics_date") String StaticDate);

    void savestaticsdeployunitandcases(@Param("casedataList") final List<StaticsDeployunitandcases> testcase);


}
