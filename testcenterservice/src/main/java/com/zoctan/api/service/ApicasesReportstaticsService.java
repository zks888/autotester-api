package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesReportstatics;

import java.util.List;

/**
* @author zks888
* @date 2021/04/01
*/
public interface ApicasesReportstaticsService extends Service<ApicasesReportstatics> {

    List<ApicasesReportstatics> getapicasesreportstaticsbypandb(long planid, String batchname);
}
