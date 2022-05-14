package com.zks888.api.mapper;

import com.zks888.api.core.mapper.MyMapper;
import com.zks888.api.dto.RoleWithResource;
import com.zks888.api.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zks888
 * @date 2018/06/09
 */
public interface RoleMapper extends MyMapper<Role> {
  /**
   * 获取所有角色以及对应的权限
   *
   * @return 角色可控资源列表
   */
  List<RoleWithResource> listRoles();

  void SaveRole(@Param("id") Long id,@Param("name") String name);

  /**
   * 按角色Id更新修改时间
   *
   * @param id 角色Id
   */
  void updateTimeById(@Param("id") Long id);
}
