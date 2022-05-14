package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Apicases;
import com.zks888.api.entity.ApicasesDebugCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApicasesDebugConditionMapper extends MyMapper<ApicasesDebugCondition> {
    List<ApicasesDebugCondition> finddebugcondition(final Map<String, Object> params);
    void saveconditionscase(@Param("casedataList")final List<ApicasesDebugCondition> casedataList);
    void deletacases(@Param("casedataList")final List<ApicasesDebugCondition> casedataList);
    Integer findcasebyconditionidandcaseid(@Param("conditionid") long conditionid,@Param("caseid") long caseid);
    List<Apicases> findnotexistcase(final @Param("conditionid") long conditionid, @Param("deployunitid") long deployunitid);
}
