package com.zks888.api.service;

import com.zks888.api.core.service.Service;
import com.zks888.api.entity.RolePermission;

/**
 * @author zks888
 * @date 2018/06/09
 */
public interface RolePermissionService extends Service<RolePermission> {

    void deleteRolePermission(Long roleId);
}
