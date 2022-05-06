package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.ExecuteplanTestcase;
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

    List<ExecuteplanTestcase> findexplanWithName(final Map<String, Object> params);

    List<ExecuteplanTestcase> findcasebydeployandapi(final Map<String, Object> params);

    List<ExecuteplanTestcase> findcasebytestplanid(final @Param("executeplanid") long executeplanid);

    List<Apicases> findcasebyplanid(final @Param("executeplanid") long executeplanid, @Param("deployunitid") long deployunitid,@Param("casetype") String casetype);

    Integer findcasebyplanidandcaseid(@Param("executeplanid") long executeplanid,@Param("testcaseid") long testcaseid);

    Integer findcasenumbyplanid(@Param("executeplanid") long executeplanid);

    void removeexecuteplantestcase(@Param("executeplanid") long executeplanid,@Param("testcaseid") long testcaseid);

    void removetestcase(@Param("testcaseid") long testcaseid);

    void removeplancase(@Param("executeplanid") long executeplanid);

    List<ExecuteplanTestcase> finddeployunitbyplanid(final @Param("executeplanid") long executeplanid);

    List<ExecuteplanTestcase> getstaticsplancases();

    List<ExecuteplanTestcase> getplancasesbyplanid(final long executeplanid);

}