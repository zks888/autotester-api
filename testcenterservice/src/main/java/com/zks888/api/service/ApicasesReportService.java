package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ApicasesReport;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/10/16
*/
public interface ApicasesReportService extends Service<ApicasesReport> {
    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApicasesReport> findApicasereportWithName(final Map<String, Object> params);

    List<ApicasesReport> findApicasereportWithNameandStatus(long planid,String status,String batchname);

    Long getApicasetotalsWithName(final Map<String, Object> params);

    Long getApicasenumbystatus(final Map<String, Object> params);

    Long getApicasecosttimes(final Map<String, Object> params);

    List<ApicasesReport> listallresult();

    List<ApicasesReport> getreportbyplanandbatchstatus(long planid,String status,String batchname);
}
