package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ApicasesReportstatics;

import java.util.List;

/**
* @author zks888
* @date 2021/04/01
*/
public interface ApicasesReportstaticsService extends Service<ApicasesReportstatics> {

    List<ApicasesReportstatics> getapicasesreportstaticsbypandb(long planid, String batchname);
}
