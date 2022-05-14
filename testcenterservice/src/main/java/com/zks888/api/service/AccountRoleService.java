package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.AccountRole;

/**
 * @author zks888
 * @date 2018/06/09
 */
public interface AccountRoleService extends Service<AccountRole> {
  /**
   * 更新用户角色
   *
   * @param accountRole 用户角色
   */
  void updateRoleIdByAccountId(AccountRole accountRole);
}
