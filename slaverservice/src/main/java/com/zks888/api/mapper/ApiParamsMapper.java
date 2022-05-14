package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.ApiParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface ApiParamsMapper extends MyMapper<ApiParams> {
    /**
     * 按条件查询Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    List<ApiParams> findApiParamsWithName(final Map<String, Object> params);

    List<ApiParams> getDistinctApiParamsbyid(Long apiid);

    /**
     * 按条件查询Api内容
     *
     * @return 用户列表
     */

    List<ApiParams> getApiParamsbypropertytype(@Param("apiid") Long apiid, @Param("propertytype")String propertytype);


    /**
     * 更新Api内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updateApiParams(ApiParams params);

    int ifexist(Condition condition);

}
