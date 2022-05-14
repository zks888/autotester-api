package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Executeplanbatch;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ExecuteplanbatchMapper extends MyMapper<Executeplanbatch> {
    int ifexist(Condition condition);

    List<Executeplanbatch> getbatchbyplan(@Param("executeplanid") Long executeplanid);

    Executeplanbatch getbatchidbyplanidandbatchname(@Param("executeplanid") Long executeplanid,@Param("batchname") String batchname);

    List<Executeplanbatch> getallexplanbatch();

    List<Executeplanbatch> findexplanbatchWithName(final Map<String, Object> params);

    List<Executeplanbatch> getbatchbyexectype(@Param("exectype") String exectype);

    List<Executeplanbatch> getrecentbatchbyid(@Param("executeplanid") Long executeplanid);

}
