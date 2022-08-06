package com.shf.acl.controller;


import com.shf.acl.entity.Permission;
import com.shf.acl.service.RolePermissionService;
import com.shf.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色权限 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
@RestController
@RequestMapping("/acl/admin/role-permission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;


}

