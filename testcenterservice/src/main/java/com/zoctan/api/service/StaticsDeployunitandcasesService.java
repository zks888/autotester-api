package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.StaticsDeployunitandcases;

import java.util.List;

/**
* @author zks888
* @date 2021/04/15
*/
public interface StaticsDeployunitandcasesService extends Service<StaticsDeployunitandcases> {
    List<StaticsDeployunitandcases> getdeployunitstatics(String StaticDate);


}
