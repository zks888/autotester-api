package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApiCasedata;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApiCasedataMapper extends MyMapper<ApiCasedata> {
    /**
     *
     *
     * @param casedataMap 用例ID
     * @param casedataMap 用例参数和值
     */
    void saveCasedata(@Param("casedataMap") List<ApiCasedata> casedataMap);

    List<ApiCasedata> getcasedatabycaseid(Long caseid);
    /**
     * 根据用例id和参数类型获取用例参数值
     * @param params 用例ID,protype
     * @return 获取用例参数值
     */
    List<ApiCasedata> getparamvaluebycaseidandtype(final Map<String, Object> params);

    void deleteparamvaluebycaseidandtype(@Param("caseid") Long caseid,@Param("propertytype") String propertytype);

    void deletcasedatabyid(@Param("caseid") Long caseid);

}