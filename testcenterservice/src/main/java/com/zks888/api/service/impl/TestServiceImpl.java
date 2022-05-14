package com.zks888.api.service.impl;

import com.zks888.api.mapper.TestMapper;
import com.zks888.api.entity.Test;
import com.zks888.api.service.TestService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2020/04/14
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl extends AbstractService<Test> implements TestService {
@Resource
private TestMapper testMapper;

}
