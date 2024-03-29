package com.zks888.api.dto;

import com.zks888.api.entity.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

/**
 * @author zks888
 * @date 2018/10/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountWithRole extends Account {
  /** 用户的角色Id */
  @Transient private Long roleId;

  /** 用户的角色名 */
  @Transient private String roleName;
}
