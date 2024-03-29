package com.zks888.api.service.impl;

import com.zks888.api.entity.Apicases;
import com.zks888.api.entity.ExecuteplanTestcase;
import com.zks888.api.mapper.ApicasesDebugConditionMapper;
import com.zks888.api.entity.ApicasesDebugCondition;
import com.zks888.api.service.ApicasesDebugConditionService;
import com.zks888.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author zks888
* @date 2022/04/06
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesDebugConditionServiceImpl extends AbstractService<ApicasesDebugCondition> implements ApicasesDebugConditionService {
@Resource
private ApicasesDebugConditionMapper apicasesDebugConditionMapper;

    @Override
    public List<ApicasesDebugCondition> finddebugcondition(Map<String, Object> params) {
        return apicasesDebugConditionMapper.finddebugcondition(params);
    }

    @Override
    public void saveconditionscase(List<ApicasesDebugCondition> casedataList) {
        List<ApicasesDebugCondition> caselist = new ArrayList();
        for (ApicasesDebugCondition tc : casedataList) {
            Integer tmptestcase = apicasesDebugConditionMapper.findcasebyconditionidandcaseid(tc.getConditionid(), tc.getCaseid());
            if (tmptestcase == null || tmptestcase.intValue() == 0) {
                caselist.add(tc);
            }
        }
        if (caselist.size() > 0) {
            apicasesDebugConditionMapper.saveconditionscase(casedataList);
        }
    }

    @Override
    public void deletacases(List<ApicasesDebugCondition> casedataList) {
        apicasesDebugConditionMapper.deletacases(casedataList);
    }

    @Override
    public List<Apicases> findnotexistcase(long conditionid, long deployunitid) {
        return apicasesDebugConditionMapper.findnotexistcase(conditionid,deployunitid);
    }
}
