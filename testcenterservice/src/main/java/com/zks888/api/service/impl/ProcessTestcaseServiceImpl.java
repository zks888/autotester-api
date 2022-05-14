package com.zks888.api.service.impl;

import com.zks888.api.mapper.ProcessTestcaseMapper;
import com.zks888.api.entity.ProcessTestcase;
import com.zks888.api.service.ProcessTestcaseService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zks888
 * @date 2022/04/27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessTestcaseServiceImpl extends AbstractService<ProcessTestcase> implements ProcessTestcaseService {
    @Resource
    private ProcessTestcaseMapper processTestcaseMapper;

}
