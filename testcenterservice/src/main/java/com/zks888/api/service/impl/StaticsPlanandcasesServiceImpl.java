package com.zks888.api.service.impl;

import com.zks888.api.mapper.StaticsPlanandcasesMapper;
import com.zks888.api.entity.StaticsPlanandcases;
import com.zks888.api.service.StaticsPlanandcasesService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zks888
* @date 2021/04/14
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class StaticsPlanandcasesServiceImpl extends AbstractService<StaticsPlanandcases> implements StaticsPlanandcasesService {
@Resource
private StaticsPlanandcasesMapper staticsPlanandcasesMapper;

    @Override
    public List<StaticsPlanandcases> getplanstatics(String StaticDate) {
        return staticsPlanandcasesMapper.getplanstatics(StaticDate);
    }
}
