package com.zks888.api.service.impl;

import com.zks888.api.mapper.RuntimeparamsrecordMapper;
import com.zks888.api.entity.Runtimeparamsrecord;
import com.zks888.api.service.RuntimeparamsrecordService;
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
public class RuntimeparamsrecordServiceImpl extends AbstractService<Runtimeparamsrecord> implements RuntimeparamsrecordService {
@Resource
private RuntimeparamsrecordMapper runtimeparamsrecordMapper;

}
