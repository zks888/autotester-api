package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.EnviromentAssemble;
import com.zks888.api.mapper.EnviromentAssembleMapper;
import com.zks888.api.service.EnviromentAssembleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/11/06
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class EnviromentAssembleServiceImpl extends AbstractService<EnviromentAssemble> implements EnviromentAssembleService {
@Resource
private EnviromentAssembleMapper enviromentAssembleMapper;

    @Override
    public List<EnviromentAssemble> findassembleWithName(Map<String, Object> params) {
        return enviromentAssembleMapper.findassembleWithName(params);
    }

    @Override
    public void updateenviromentassemble(EnviromentAssemble params) {
        enviromentAssembleMapper.updateenviromentassemble(params);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }
}
