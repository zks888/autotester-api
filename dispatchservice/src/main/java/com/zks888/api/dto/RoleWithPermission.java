package com.zks888.api.dto;

import com.zks888.api.entity.Role;

import java.util.List;

/**
 * @author zks888
 * @date 2018/10/16
 */
public class RoleWithPermission extends Role {
  /** 角色对应的权限Id列表 */
  private List<Integer> permissionIdList;

  public List<Integer> getPermissionIdList() {
    return this.permissionIdList;
  }

  public void setPermissionIdList(final List<Integer> permissionIdList) {
    this.permissionIdList = permissionIdList;
  }
}
