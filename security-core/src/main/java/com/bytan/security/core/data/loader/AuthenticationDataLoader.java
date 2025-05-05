package com.bytan.security.core.data.loader;

import java.util.ArrayList;
import java.util.List;

/**
 * 身份权限信息加载器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/14 17:08
 */
public abstract class AuthenticationDataLoader extends InteriorCacheAuthenticationDataLoader {

    /**
     * 自定义获取角色关联的权限信息（当缓存拿不到数据时，就会调用该接口）
     * @param role 角色
     * @return 权限列表
     */
    public abstract List<String> doGetRolePermission(String role);

    /**
     * 自定义获取主体关联的角色信息（当缓存拿不到数据时，就会调用该接口）
     * @param subjectId 主体id
     * @return 角色列表
     */
    public abstract List<String> doGetSubjectRole(String subjectId);

    @Override
    public List<String> getRolePermission(String role) {
        List<String> rolePermissionList = super.getRolePermission(role);
        if (rolePermissionList == null || rolePermissionList.isEmpty()) {
            rolePermissionList = doGetRolePermission(role);
            if (rolePermissionList != null && !rolePermissionList.isEmpty()) {
                this.setRolePermission(role, rolePermissionList);
            } else {
                rolePermissionList = new ArrayList<>();
            }
        }
        return rolePermissionList;
    }

    @Override
    public List<String> getSubjectRole(String subjectId) {
        List<String> subjecthRoleList = super.getSubjectRole(subjectId);
        if (subjecthRoleList == null || subjecthRoleList.isEmpty()) {
            subjecthRoleList = doGetSubjectRole(subjectId);
            if (subjecthRoleList != null && !subjecthRoleList.isEmpty()) {
                this.setSubjectRole(subjectId, subjecthRoleList);
            } else {
                subjecthRoleList = new ArrayList<>();
            }
        }
        return subjecthRoleList;
    }
}
