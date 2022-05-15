package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ExecuteplanTestcase;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/10/12
*/
public interface ExecuteplanTestcaseService extends Service<ExecuteplanTestcase> {

    List<ExecuteplanTestcase> findexplanWithName(final Map<String, Object> params);

    List<ExecuteplanTestcase> getplancasesbyplanid(final long executeplanid);

    void savetestplancase(final List<ExecuteplanTestcase> testcase);

    List<ExecuteplanTestcase> finddeployunitbyplanid(final long executeplanid);

    List<ExecuteplanTestcase> findcasebydeployandapi(final Map<String, Object> params);

    void removeexecuteplantestcase(final List<ExecuteplanTestcase> testcase);

    void removetestcase(final long testcaseid);

    void removeplancase(final long executeplanid);

    Integer findcasenumbyplanid(long executeplanid);

    List<ExecuteplanTestcase> getstaticsplancases();

}
