package com.zks888.api.service.impl;

import com.zks888.api.mapper.PlanbantchexeclogMapper;
import com.zks888.api.entity.Planbantchexeclog;
import com.zks888.api.service.PlanbantchexeclogService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author zks888
* @date 2021/12/26
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class PlanbantchexeclogServiceImpl extends AbstractService<Planbantchexeclog> implements PlanbantchexeclogService {
@Resource
private PlanbantchexeclogMapper planbantchexeclogMapper;

}
