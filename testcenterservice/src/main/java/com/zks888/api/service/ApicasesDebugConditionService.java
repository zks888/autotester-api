package com.zks888.api.service;

import com.zks888.api.entity.Apicases;
import com.zks888.api.entity.ApicasesCondition;
import com.zks888.api.entity.ApicasesDebugCondition;
import com.zks888.api.core.service.Service;
import com.zks888.api.entity.ExecuteplanTestcase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/04/06
*/
public interface ApicasesDebugConditionService extends Service<ApicasesDebugCondition> {

    List<ApicasesDebugCondition> finddebugcondition(final Map<String, Object> params);

    void saveconditionscase(final List<ApicasesDebugCondition> casedataList);

    void deletacases(final List<ApicasesDebugCondition> casedataList);

    List<Apicases> findnotexistcase(long conditionid,  long deployunitid);

}
