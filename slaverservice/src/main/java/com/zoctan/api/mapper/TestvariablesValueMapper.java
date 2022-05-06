package com.zoctan.api.mapper;

import com.zoctan.api.core.mapper.MyMapper;
import com.zoctan.api.entity.TestvariablesValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

public interface TestvariablesValueMapper extends MyMapper<TestvariablesValue> {
    List<TestvariablesValue> findtestvariablesvalueWithName(final Map<String, Object> params);

    /**
     * 更新字典内容
     *
     * @param params 参数
     * @return 用户列表
     */
    void updatetestvariablesvalue(TestvariablesValue params);

    TestvariablesValue gettestvariablesvalue(Long planid,Long caseid,String variablesname,String batchname);

    int ifexist(Condition condition);

    List<TestvariablesValue> gettvlist(@Param("planid") Long planid, @Param("batchname")String batchname,@Param("variablestype")String variablestype);

}