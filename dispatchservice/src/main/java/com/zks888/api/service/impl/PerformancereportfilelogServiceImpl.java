package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.Performancereportfilelog;
import com.zks888.api.mapper.PerformancereportfilelogMapper;
import com.zks888.api.service.PerformancereportfilelogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Season
 * @date 2022/04/15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PerformancereportfilelogServiceImpl extends AbstractService<Performancereportfilelog> implements PerformancereportfilelogService {
    @Resource
    private PerformancereportfilelogMapper performancereportfilelogMapper;

    @Override
    public List<Performancereportfilelog> findrecentperformancereportfile(int shardingTotalCount, int shardingItem) {
        return performancereportfilelogMapper.findrecentperformancereportfile(shardingTotalCount, shardingItem);
    }
}
