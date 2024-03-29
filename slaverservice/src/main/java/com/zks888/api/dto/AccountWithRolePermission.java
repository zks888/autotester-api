package com.zks888.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author zks888
 * @date 2018/10/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountWithRolePermission extends AccountWithRole {
  /** 用户的角色对应的权限code */
  @Transient private List<String> permissionCodeList;
}
