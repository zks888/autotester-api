package com.zks888.api.service.impl;

import com.zks888.api.mapper.DispatchMapper;
import com.zks888.api.entity.Dispatch;
import com.zks888.api.service.DispatchService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/11/21
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class DispatchServiceImpl extends AbstractService<Dispatch> implements DispatchService {
@Resource
private DispatchMapper dispatchMapper;

    @Override
    public List<Dispatch> findDispatchWithName(Map<String, Object> params) {
        return dispatchMapper.findDispatchWithName(params);
    }

    @Override
    public List<Dispatch> getDispatchWithstatus(long testplanid, String batchname, String status) {
        return dispatchMapper.getDispatchWithstatus(testplanid, batchname, status);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }
}
