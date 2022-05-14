package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.ApiCasedata;
import com.zks888.api.mapper.ApiCasedataMapper;
import com.zks888.api.service.ApiCasedataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zks888
 * @date 2020/09/21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiCasedataServiceImpl extends AbstractService<ApiCasedata> implements ApiCasedataService {
    @Resource
    private ApiCasedataMapper apiCasedataMapper;

    @Override
    public List<ApiCasedata> GetCaseDatasByCaseID(Long caseid) {
        return apiCasedataMapper.GetCaseDatasByCaseID(caseid);
    }

    @Override
    public ApiCasedata GetCaseDatasByCaseIDAndApiparamAndType(Long caseid, String apiparam,String property) {
        return apiCasedataMapper.GetCaseDatasByCaseIDAndApiparamAndType(caseid,apiparam,property);
    }

    @Override
    public void UpdateByCaseIDAndApiparam(Long caseid, String apiparam, String propertytype,String Value) {
        apiCasedataMapper.UpdateByCaseIDAndApiparam(caseid,apiparam,propertytype,Value);
    }
}
