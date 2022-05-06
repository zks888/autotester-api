package com.zoctan.api.service.impl;

import com.zoctan.api.mapper.ApicasesRuntimeparamsMapper;
import com.zoctan.api.entity.ApicasesRuntimeparams;
import com.zoctan.api.service.ApicasesRuntimeparamsService;
import com.zoctan.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2021/05/30
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesRuntimeparamsServiceImpl extends AbstractService<ApicasesRuntimeparams> implements ApicasesRuntimeparamsService {
@Resource
private ApicasesRuntimeparamsMapper apicasesRuntimeparamsMapper;

}
