package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.dto.Casedata;
import com.zks888.api.entity.ApiCasedata;
import com.zks888.api.mapper.ApiCasedataMapper;
import com.zks888.api.service.ApiCasedataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public void save(final Casedata cd)
    {
        this.apiCasedataMapper.deleteparamvaluebycaseidandtype(cd.getCaseid(),cd.getPropertytype());
        this.apiCasedataMapper.saveCasedata(cd.getCasedataMap());
    }

    @Override
    public List<ApiCasedata> getparamvaluebycaseidandtype(final Map<String, Object> params) {
        return this.apiCasedataMapper.getparamvaluebycaseidandtype(params);
    }

    @Override
    public List<ApiCasedata> getcasedatabycaseid(Long caseid) {
        return this.apiCasedataMapper.getcasedatabycaseid(caseid);
    }

    @Override
    public void deletcasedatabyid(Long caseid) {
        this.apiCasedataMapper.deletcasedatabyid(caseid);
    }
}
