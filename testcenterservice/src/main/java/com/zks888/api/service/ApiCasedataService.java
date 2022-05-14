package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.dto.Casedata;
import com.zks888.api.entity.ApiCasedata;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2020/09/21
*/
public interface ApiCasedataService extends Service<ApiCasedata> {
    /**
     *
     *
     * @param casedata 用例参数数据
     */
    void save(Casedata casedata);

    void saveCasedata(List<ApiCasedata> casedataMap);

    List<ApiCasedata> getparamvaluebycaseidandtype(final Map<String, Object> params);

    List<ApiCasedata> getparamvaluebycaseidandtype(Long caseid,String PrppertyType);

    List<ApiCasedata> getdataidbyapiidandtypeandparatype(Long apiid,String PrppertyType,String paramstype);

    List<ApiCasedata> getdataidbyapiidandtypeandapiparam(Long apiid,String PrppertyType,String apiparam);

    List<ApiCasedata> getdataidbyapiidandtype(Long apiid,String PrppertyType);

    void deletcasedatabyid(Long caseid);

    void updateparambycaseidandprotypeandapiparam(Long caseid,String propertytype,String apiparam, String oldapiparam,String paramstype);
}
