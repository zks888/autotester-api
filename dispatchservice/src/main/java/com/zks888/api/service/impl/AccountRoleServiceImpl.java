package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.entity.AccountRole;
import com.zks888.api.mapper.AccountRoleMapper;
import com.zks888.api.service.AccountRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zks888
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountRoleServiceImpl extends AbstractService<AccountRole>
    implements AccountRoleService {
  @Resource private AccountRoleMapper accountRoleMapper;

  @Override
  public void updateRoleIdByAccountId(final AccountRole accountRole) {
    this.accountRoleMapper.updateRoleIdByAccountId(accountRole);
  }
}
