package com.zks888.api.service;

import com.zks888.api.entity.Deployunit;
import com.zks888.api.core.service.Service;

/**
* @author zks888
* @date 2020/04/17
*/
public interface DeployunitService extends Service<Deployunit> {
    Deployunit findDeployNameValueWithCode(String params);

}
