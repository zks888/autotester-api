package com.zks888.api.service.impl;

import com.zks888.api.mapper.ExecuteplanParamsMapper;
import com.zks888.api.entity.ExecuteplanParams;
import com.zks888.api.service.ExecuteplanParamsService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/12/19
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanParamsServiceImpl extends AbstractService<ExecuteplanParams> implements ExecuteplanParamsService {
@Resource
private ExecuteplanParamsMapper executeplanParamsMapper;

    @Override
    public void updateParams(ExecuteplanParams params) {
        executeplanParamsMapper.updateParams(params);
    }

    @Override
    public List<ExecuteplanParams> getParamsbyepid(Map<String, Object> params) {
        return executeplanParamsMapper.getParamsbyepid(params);
    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }

    @Override
    public void removeplanparams(long executeplanid) {
        executeplanParamsMapper.removeplanparams(executeplanid);
    }


}
