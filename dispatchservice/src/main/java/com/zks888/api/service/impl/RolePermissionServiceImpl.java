package com.zks888.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zks888.api.core.service.AbstractService;
import com.zks888.api.mapper.RolePermissionMapper;
import com.zks888.api.entity.RolePermission;
import com.zks888.api.service.RolePermissionService;

import javax.annotation.Resource;

/**
 * @author zks888
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends AbstractService<RolePermission>
    implements RolePermissionService {
  @Resource private RolePermissionMapper rolePermissionMapper;
}
