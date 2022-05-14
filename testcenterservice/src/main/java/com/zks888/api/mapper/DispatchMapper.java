package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Dispatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DispatchMapper extends MyMapper<Dispatch> {
    List<Dispatch> findDispatchWithName(final Map<String, Object> params);
    List<Dispatch> getDispatchWithstatus(@Param("execplanid") long testplanid, @Param("batchname") String batchname,@Param("status") String status);

}
