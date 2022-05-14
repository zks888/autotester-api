package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Executeplan;
import com.zks888.api.mapper.ExecuteplanMapper;
import com.zks888.api.service.ExecuteplanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zks888
 * @date 2020/09/27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanServiceImpl extends AbstractService<Executeplan> implements ExecuteplanService {
    @Resource
    private ExecuteplanMapper executeplanMapper;

    @Override
    public void updatetestplanstatus(Long id, String status) {
        this.executeplanMapper.updatetestplanstatus(id,status);
    }
}
