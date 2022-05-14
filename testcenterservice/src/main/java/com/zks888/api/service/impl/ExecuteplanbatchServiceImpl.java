package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Executeplanbatch;
import com.zks888.api.mapper.ExecuteplanbatchMapper;
import com.zks888.api.service.ExecuteplanbatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/10/22
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanbatchServiceImpl extends AbstractService<Executeplanbatch> implements ExecuteplanbatchService {
@Resource
private ExecuteplanbatchMapper executeplanbatchMapper;

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public List<Executeplanbatch> getbatchbyplan(Long executeplanid) {
        return executeplanbatchMapper.getbatchbyplan(executeplanid);
    }

    @Override
    public List<Executeplanbatch> getallexplanbatch() {
        return executeplanbatchMapper.getallexplanbatch();
    }

    @Override
    public List<Executeplanbatch> findexplanbatchWithName(Map<String, Object> params) {
        return executeplanbatchMapper.findexplanbatchWithName(params);
    }

    @Override
    public List<Executeplanbatch> getrecentbatchbyid(Long executeplanid) {
        return executeplanbatchMapper.getrecentbatchbyid(executeplanid);
    }

}
