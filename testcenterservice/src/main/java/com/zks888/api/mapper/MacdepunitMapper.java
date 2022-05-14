package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Envmachine;
import com.zks888.api.entity.Macdepunit;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface MacdepunitMapper extends MyMapper<Macdepunit> {
    /**
     * 按环境名或者服务器名获取服务器发布单元内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    List<Macdepunit> findMacAndDepWithName(final Map<String, Object> params);
    List<Macdepunit> findMacAndDepWithid(final Map<String, Object> params);

    /**
     * 更新服务器发布单元内容
     *
     * @param params 参数
     * @return 环境服务器列表
     */
    void updateMacAndDep(Macdepunit params);

    int ifexist(Condition condition);

    Integer findmachinenumbyenvidanddeployid(@Param("envid") long envid, @Param("depunitid")long depunitid);
    Macdepunit getmacdepbyenvidanddepid(long envid,long depunitid);

    List<Macdepunit> getenvassemblelistbyenvidandtype(@Param("envid")long envid, @Param("assembletype") String  assembletype);

    List<Macdepunit> findmachinebyid(@Param("machineid")long machineid);
    List<Macdepunit> findenviromentbyenvid(@Param("envid")long envid);
    List<Macdepunit> findassemblebyassid(@Param("assembleid")long assid);

}
