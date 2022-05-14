package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ApicasesReportstatics;
import com.zks888.api.mapper.ApicasesReportstaticsMapper;
import com.zks888.api.service.ApicasesReportstaticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2021/04/01
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesReportstaticsServiceImpl extends AbstractService<ApicasesReportstatics> implements ApicasesReportstaticsService {
@Resource
private ApicasesReportstaticsMapper apicasesReportstaticsMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
