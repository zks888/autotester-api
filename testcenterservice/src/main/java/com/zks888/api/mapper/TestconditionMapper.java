package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Testcondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TestconditionMapper extends MyMapper<Testcondition> {
    List<Testcondition> findtestconditionWithName(final Map<String, Object> params);
    void updateTestcondition(Testcondition params);
    void updatecasename(@Param("objectid") long caseid,@Param("objecttype")String objecttype ,@Param("objectname")String newcasename);
    List<Testcondition> getallTestcondition();
    List<Testcondition> getallTestconditionByType(@Param("objecttype") String objecttype);
    List<Testcondition> GetConditionByPlanIDAndConditionType(Long objectid,String conditiontype,String objecttype);
    List<Testcondition> gettestconditionforscripyanddelay(String objecttype);

}
