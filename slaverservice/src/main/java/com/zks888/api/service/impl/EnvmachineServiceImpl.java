package com.zks888.api.service.impl;

import com.zks888.api.mapper.EnvmachineMapper;
import com.zks888.api.entity.Envmachine;
import com.zks888.api.service.EnvmachineService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2020/04/20
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnvmachineServiceImpl extends AbstractService<Envmachine> implements EnvmachineService {
@Resource
private EnvmachineMapper envmachineMapper;

}
