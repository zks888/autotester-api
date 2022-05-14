package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ApiCasedata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zks888
* @date 2020/09/21
*/
public interface ApiCasedataService extends Service<ApiCasedata> {

    List<ApiCasedata>  GetCaseDatasByCaseID(@Param("caseid") Long caseid);

    ApiCasedata  GetCaseDatasByCaseIDAndApiparamAndType(@Param("caseid") Long caseid,@Param("apiparam") String apiparam,@Param("propertytype")String propertytype);

    void UpdateByCaseIDAndApiparam(@Param("caseid") Long caseid,@Param("apiparam") String apiparam,@Param("propertytype")String propertytype,@Param("apiparamvalue")String apiparamvalue);

}
