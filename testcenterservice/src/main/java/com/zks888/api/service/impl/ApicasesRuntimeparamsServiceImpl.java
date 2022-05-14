package com.zks888.api.service.impl;

import com.zks888.api.mapper.ApicasesRuntimeparamsMapper;
import com.zks888.api.entity.ApicasesRuntimeparams;
import com.zks888.api.service.ApicasesRuntimeparamsService;
import com.zks888.api.core.service.AbstractService;
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
