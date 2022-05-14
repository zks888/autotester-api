package com.zks888.api.service.impl;

import com.zks888.api.mapper.MachineMapper;
import com.zks888.api.entity.Machine;
import com.zks888.api.service.MachineService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2020/04/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class MachineServiceImpl extends AbstractService<Machine> implements MachineService {
@Resource
private MachineMapper machineMapper;

}
