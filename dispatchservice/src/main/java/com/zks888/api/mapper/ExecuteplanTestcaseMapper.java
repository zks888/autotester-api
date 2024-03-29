package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ExecuteplanTestcase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExecuteplanTestcaseMapper extends MyMapper<ExecuteplanTestcase> {

    /**
     *
     *
     * @param testcase 用例参数数据
     */
    void savetestplancase(@Param("casedataList") final List<ExecuteplanTestcase> testcase);

    List<ExecuteplanTestcase> findcasebydeployandapi(final Map<String, Object> params);

    List<ExecuteplanTestcase> findcasebytestplanid(final @Param("executeplanid") long executeplanid,@Param("casetype") String casetype);

    Integer findcasebyplanidandcaseid(@Param("executeplanid") long executeplanid,@Param("testcaseid") long testcaseid);

    Integer findcasenumbyplanid(@Param("executeplanid") long executeplanid);

    void removeexecuteplantestcase(@Param("executeplanid") long executeplanid,@Param("testcaseid") long testcaseid);

    List<ExecuteplanTestcase> finddeployunitbyplanid(final @Param("executeplanid") long executeplanid);

}
