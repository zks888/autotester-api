package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.TestconditionReport;
import com.zks888.api.mapper.TestconditionReportMapper;
import com.zks888.api.service.TestconditionReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/07/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestconditionReportServiceImpl extends AbstractService<TestconditionReport> implements TestconditionReportService {
@Resource
private TestconditionReportMapper testconditionReportMapper;

    @Override
    public List<TestconditionReport> findTestconditionReportWithName(Map<String, Object> params) {
        return testconditionReportMapper.findTestconditionReportWithName(params);
    }
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
