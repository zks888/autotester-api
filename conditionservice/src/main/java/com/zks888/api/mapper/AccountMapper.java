package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zks888
 * @date 2018/06/09
 */
public interface AccountMapper extends MyMapper<Account> {
  /**
   * 获取所有用户以及对应角色
   *
   * @return 用户列表
   */
  List<Account> findWithUsername(@Param("name") String name);

}
