package com.zks888.api.service.impl;

import com.zks888.api.entity.Testvariables;
import com.zks888.api.mapper.TestvariablesValueMapper;
import com.zks888.api.entity.TestvariablesValue;
import com.zks888.api.service.TestvariablesValueService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2021/07/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestvariablesValueServiceImpl extends AbstractService<TestvariablesValue> implements TestvariablesValueService {
@Resource
private TestvariablesValueMapper testvariablesValueMapper;

    @Override
    public List<TestvariablesValue> findtestvariablesvalueWithName(Map<String, Object> params) {
        return testvariablesValueMapper.findtestvariablesvalueWithName(params);
    }

    @Override
    public void updatetestvariablesvalue(TestvariablesValue params) {
        testvariablesValueMapper.updatetestvariablesvalue(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

}
