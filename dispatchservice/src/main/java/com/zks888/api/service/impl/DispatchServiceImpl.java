package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Dispatch;
import com.zks888.api.mapper.DispatchMapper;
import com.zks888.api.service.DispatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2020/11/21
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchServiceImpl extends AbstractService<Dispatch> implements DispatchService {
@Resource
private DispatchMapper dispatchMapper;

}
