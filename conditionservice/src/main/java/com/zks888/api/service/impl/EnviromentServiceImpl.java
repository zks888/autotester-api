package com.zks888.api.service.impl;

import com.zks888.api.mapper.EnviromentMapper;
import com.zks888.api.entity.Enviroment;
import com.zks888.api.service.EnviromentService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2020/04/18
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnviromentServiceImpl extends AbstractService<Enviroment> implements EnviromentService {
@Resource
private EnviromentMapper enviromentMapper;

}
