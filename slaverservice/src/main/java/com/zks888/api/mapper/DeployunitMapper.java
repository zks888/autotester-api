package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Deployunit;
import org.apache.ibatis.annotations.Param;

public interface DeployunitMapper extends MyMapper<Deployunit> {

    Deployunit findDeployNameValueWithCode(@Param("deployunitname") String params);

}
