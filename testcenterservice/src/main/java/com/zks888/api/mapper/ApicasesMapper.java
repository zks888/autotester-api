package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Apicases;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ApicasesMapper extends MyMapper<Apicases> {
    /**
     * 按发布单元名或者协议名获取发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    List<Apicases> findApiCaseWithName(final Map<String, Object> params);

    List<Apicases> findApiCasebynameandcasetype(final Map<String, Object> params);

    /**
     * 更新发布单元内容
     *
     * @param params 参数
     * @return 发布单元列表
     */
    void updateApicase(Apicases params);

    int ifexist(Condition condition);

    List<Apicases> forupdateifexist(Apicases apicase);

    List<Apicases> getapicasebyName(@Param("deployunitid")long deployunitid,@Param("apiid") long apiid);

    Integer getcasenum(@Param("casetype")String casetype);


    List<Apicases> getstaticsdeployunitcases();
    List<Apicases> getcasebydeployunitid(Long deployunitid);

    List<Apicases> getcasebyapiid(@Param("apiid") Long apiid);

}
