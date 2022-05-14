package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Dispatch;
import com.zks888.api.entity.Slaver;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SlaverMapper extends MyMapper<Slaver> {
    /**
     * 按条件查询字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<Slaver> findslaverWithName(final Map<String, Object> params);

    List<Slaver> findslaverbyip(@Param("ip") final String ip);

    Slaver findslaverbyid(@Param("id") final Long id);

    List<Slaver> findslaverbytype(@Param("stype") final String stype);

    List<Slaver> findslaveralive(@Param("stype") final String stype, @Param("status") final String status);

    void updateSlaverStatus(@Param("id") Long id,@Param("status") String status);

    List<Slaver> findslaverbytypeandstatus(@Param("stype") final String stype,@Param("status") final String status);

    Integer findbusyslavernums(@Param("slaverlist") final List<Dispatch> slaverlist, @Param("status") String status, @Param("stype") String stype);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateSlaver(Slaver params);

    void addslaver(Slaver params);
}
