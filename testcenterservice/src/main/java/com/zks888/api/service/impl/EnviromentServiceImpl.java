package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Enviroment;
import com.zks888.api.mapper.EnviromentMapper;
import com.zks888.api.service.EnviromentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/04/18
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnviromentServiceImpl extends AbstractService<Enviroment> implements EnviromentService {
@Resource
private EnviromentMapper enviromentMapper;


    @Override
    public List<Enviroment> findEnviromentName(String params) {
        return this.enviromentMapper.findEnviromentName(params);
    }

    @Override
    public List<Enviroment> findEnviromentWithName(Map<String, Object> params) {
        return this.enviromentMapper.findEnviromentWithName(params);
    }

    @Override
    public void updateEnviroment(Enviroment params) {
        enviromentMapper.updateEnviroment(params);

    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public Integer getenviromentnum() {
        return enviromentMapper.getenviromentnum();
    }
}
