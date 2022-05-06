package com.zoctan.api.service;

import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.core.service.Service;

/**
* @author zks888
* @date 2020/04/17
*/
public interface DeployunitService extends Service<Deployunit> {
    Deployunit findDeployNameValueWithCode(String params);

}
