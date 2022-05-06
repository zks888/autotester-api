package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.ApicasesReportstatics;
import tk.mybatis.mapper.entity.Condition;

/**
* @author zks888
* @date 2021/04/01
*/
public interface ApicasesReportstaticsService extends Service<ApicasesReportstatics> {
    int ifexist(Condition condition);

}
