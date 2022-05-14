package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.dto.Casedata;
import com.zks888.api.entity.ApiCasedata;

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

    List<ApiCasedata> getparamvaluebycaseidandtype(final Map<String, Object> params);

    List<ApiCasedata> getcasedatabycaseid(Long caseid);

    void deletcasedatabyid(Long caseid);

}
