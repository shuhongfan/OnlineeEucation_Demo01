package com.shf.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.acl.entity.Permission;
import com.shf.acl.entity.RolePermission;
import com.shf.acl.helper.PermissionHelper;
import com.shf.acl.mapper.RolePermissionMapper;
import com.shf.acl.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
