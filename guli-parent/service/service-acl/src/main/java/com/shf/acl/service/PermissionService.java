package com.shf.acl.service;

import com.shf.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取全部菜单
     * @return
     */
    List<Permission> queryAllMenu();

    /**
     * 递归删除菜单
     * @param id
     */
    void removeChildById(String id);

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionId
     */
    void saveRolePermissionRelationShip(String roleId, String[] permissionId);
}
