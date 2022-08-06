package com.shf.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.acl.entity.Permission;
import com.shf.acl.entity.RolePermission;
import com.shf.acl.helper.PermissionHelper;
import com.shf.acl.mapper.PermissionMapper;
import com.shf.acl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.acl.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 获取全部菜单
     *
     * @return
     */
    @Override
    public List<Permission> queryAllMenu() {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.orderByDesc("id");

        List<Permission> permissionList = baseMapper.selectList(permissionQueryWrapper);

        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    /**
     * 递归删除菜单
     *
     * @param id
     */
    @Override
    public void removeChildById(String id) {
        ArrayList<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

//        把根节点id放到List中
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionId
     */
    @Override
    public void saveRolePermissionRelationShip(String roleId, String[] permissionIds) {
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.eq("role_id", roleId);
        rolePermissionService.remove(rolePermissionQueryWrapper);

        List<RolePermission> rolePermissionList = new ArrayList<>();

        Arrays.stream(permissionIds).forEach(permissionId -> {
            RolePermission permission = new RolePermission();
            permission.setRoleId(roleId);
            permission.setPermissionId(permissionId);
            rolePermissionList.add(permission);
        });
        rolePermissionService.saveBatch(rolePermissionList);
    }


    /**
     * 递归获取子节点
     *
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, ArrayList<String> idList) {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper
                .eq("pid", id)
                .select("id");

        List<Permission> permissionList = baseMapper.selectList(permissionQueryWrapper);

        permissionList.forEach(permission -> {
            idList.add(permission.getId());
            this.selectChildListById(permission.getId(), idList);
        });
    }
}
