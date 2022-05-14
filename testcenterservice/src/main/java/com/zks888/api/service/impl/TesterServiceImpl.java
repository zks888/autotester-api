package com.zks888.api.service.impl;

import com.zks888.api.mapper.TesterMapper;
import com.zks888.api.entity.Tester;
import com.zks888.api.service.TesterService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/22
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TesterServiceImpl extends AbstractService<Tester> implements TesterService {
@Resource
private TesterMapper testerMapper;

    @Override
    public List<Tester> findtesterName(String params) {
        return this.testerMapper.findtesterName(params);
    }

    @Override
    public List<Tester> findTesterWithName(Map<String, Object> params) {
        return this.testerMapper.findTesterWithName(params);
    }

    @Override
    public void updateTester(Tester params) {
        testerMapper.updateTester(params);

    }
}
