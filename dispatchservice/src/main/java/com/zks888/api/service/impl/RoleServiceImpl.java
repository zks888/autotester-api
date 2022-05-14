package com.zks888.api.service.impl;

import com.zks888.api.core.service.AbstractService;
import com.zks888.api.dto.RoleWithPermission;
import com.zks888.api.dto.RoleWithResource;
import com.zks888.api.entity.Role;
import com.zks888.api.entity.RolePermission;
import com.zks888.api.mapper.PermissionMapper;
import com.zks888.api.mapper.RoleMapper;
import com.zks888.api.mapper.RolePermissionMapper;
import com.zks888.api.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zks888
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
  @Resource private RoleMapper roleMapper;
  @Resource private PermissionMapper permissionMapper;
  @Resource private RolePermissionMapper rolePermissionMapper;

  @Override
  public List<RoleWithResource> listRoleWithPermission() {
    // 由于mybatis在嵌套查询时和pagehelper有冲突
    // 暂时用for循环代替
    final List<RoleWithResource> roles = this.roleMapper.listRoles();
    roles.forEach(
        role -> {
          final List<com.zks888.api.entity.Resource> resources =
              this.permissionMapper.listRoleWithResourceByRoleId(role.getId());
          role.setResourceList(resources);
        });
    return roles;
  }

  @Override
  public void save(final RoleWithPermission role) {
    this.roleMapper.insert(role);
    this.rolePermissionMapper.saveRolePermission(role.getId(), role.getPermissionIdList());
  }

  @Override
  public void update(final RoleWithPermission role) {
    // 删掉所有权限，再添加回去
    final Condition condition = new Condition(RolePermission.class);
    condition.createCriteria().andCondition("role_id = ", role.getId());
    this.rolePermissionMapper.deleteByCondition(condition);
    this.rolePermissionMapper.saveRolePermission(role.getId(), role.getPermissionIdList());
    this.roleMapper.updateTimeById(role.getId());
  }
}
