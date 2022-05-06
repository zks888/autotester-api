package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.ApicasesAssert;
import com.zoctan.api.entity.Enviroment;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ApicasesAssertMapper extends MyMapper<ApicasesAssert> {
    List<ApicasesAssert> findAssertWithName(final Map<String, Object> params);
    List<ApicasesAssert> findAssertbycaseid(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateAssert(ApicasesAssert params);


    int ifexist(Condition condition);
}