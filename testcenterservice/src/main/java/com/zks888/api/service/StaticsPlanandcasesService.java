package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.StaticsPlanandcases;

import java.util.List;

/**
* @author zks888
* @date 2021/04/14
*/
public interface StaticsPlanandcasesService extends Service<StaticsPlanandcases> {

    List<StaticsPlanandcases> getplanstatics(String StaticDate);

}
