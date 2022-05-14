package com.zks888.api.service;

import com.zks888.api.entity.Performancereportfilelog;
import com.zks888.api.core.service.Service;

import java.util.List;

/**
* @author Season
* @date 2022/04/15
*/
public interface PerformancereportfilelogService extends Service<Performancereportfilelog> {

    List<Performancereportfilelog> findrecentperformancereportfile(long slaverid);

}
