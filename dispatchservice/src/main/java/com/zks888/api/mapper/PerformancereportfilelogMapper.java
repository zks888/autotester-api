package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Performancereportfilelog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerformancereportfilelogMapper extends MyMapper<Performancereportfilelog> {
    List<Performancereportfilelog> findrecentperformancereportfile(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem);

}
