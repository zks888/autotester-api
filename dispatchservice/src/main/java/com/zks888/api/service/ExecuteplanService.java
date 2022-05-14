package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.Executeplan;

/**
* @author zks888
* @date 2020/09/27
*/
public interface ExecuteplanService extends Service<Executeplan> {


    void updatetestplanstatus(Long id, String status);


}
