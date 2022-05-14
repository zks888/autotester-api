package com.zks888.api.service.impl;

import com.zks888.api.mapper.StaticsDeployunitandcasesMapper;
import com.zks888.api.entity.StaticsDeployunitandcases;
import com.zks888.api.service.StaticsDeployunitandcasesService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zks888
* @date 2021/04/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class StaticsDeployunitandcasesServiceImpl extends AbstractService<StaticsDeployunitandcases> implements StaticsDeployunitandcasesService {
@Resource
private StaticsDeployunitandcasesMapper staticsDeployunitandcasesMapper;

    @Override
    public List<StaticsDeployunitandcases> getdeployunitstatics(String StaticDate) {
        return staticsDeployunitandcasesMapper.getdeployunitstatics(StaticDate);
    }
}
