package com.zoctan.api.service;

import com.zoctan.api.core.service.Service;
import com.zoctan.api.entity.RolePermission;

/**
 * @author zks888
 * @date 2018/06/09
 */
public interface RolePermissionService extends Service<RolePermission> {

    void deleteRolePermission(Long roleId);
}
