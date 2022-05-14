package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.StaticsDeployunitandcases;

import java.util.List;

/**
* @author zks888
* @date 2021/04/15
*/
public interface StaticsDeployunitandcasesService extends Service<StaticsDeployunitandcases> {
    List<StaticsDeployunitandcases> getdeployunitstatics(String StaticDate);


}
