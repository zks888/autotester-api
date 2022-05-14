package com.zks888.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zks888.api.core.service.AbstractService;
import com.zks888.api.mapper.PermissionMapper;
import com.zks888.api.entity.Permission;
import com.zks888.api.service.PermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zks888
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends AbstractService<Permission>
    implements PermissionService {
  @Resource private PermissionMapper permissionMapper;

  @Override
  public List<com.zks888.api.entity.Resource> listResourceWithHandle() {
    return this.permissionMapper.listResourceWithHandle();
  }

  @Override
  public List<com.zks888.api.entity.Resource> listRoleWithResourceByRoleId(Long roleId) {
    return this.permissionMapper.listRoleWithResourceByRoleId(roleId);
  }
}
