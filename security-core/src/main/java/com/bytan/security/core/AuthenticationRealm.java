package com.bytan.security.core;

import cn.hutool.core.util.StrUtil;
import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.config.SecurityTokenConfig;
import com.bytan.security.core.subject.SubjectContext;

import java.util.List;

/**
 * 身份验证相关功能实现
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/10  23:41
 */
public class AuthenticationRealm extends AuthenticationTokenRealm {

    private final AuthenticationDataLoader authenticationDataLoader;

    public AuthenticationRealm(AuthenticationDataLoader authenticationDataLoader, SecurityTokenConfig tokenConfig) {
        super(tokenConfig);
        this.authenticationDataLoader = authenticationDataLoader;
    }

    /**
     * 判断角色是否拥有权限
     * @param role 角色
     * @param permission 需要校验的权限
     * @return true拥有权限
     */
    public boolean hasRolePermission(String role, String permission) {
        List<String> permissionList = authenticationDataLoader.getRolePermission(role);
        return permissionList.stream().anyMatch(permission::equals);
    }

    /**
     * 判断角色是否拥有权限
     * @param role 角色
     * @param permissionList 需要校验的权限列表
     * @return true拥有权限
     */
    public boolean hasRolePermission(String role, List<String> permissionList) {
        List<String> rolePermissionList = authenticationDataLoader.getRolePermission(role);
        for (String permission : rolePermissionList) {
            permissionList.remove(permission);
        }

        return permissionList.isEmpty();
    }

    /**
     * 是否拥有该角色
     * @param subjectId 主体id
     * @param role 需要校验的角色
     * @return true拥有权限
     */
    public boolean hasSubjectRole(String subjectId, String role) {
        List<String> roleList = authenticationDataLoader.getSubjectRole(subjectId);
        return roleList.stream().anyMatch(role::equals);
    }

    /**
     * 判断用户是否拥有该角色
     * @param subjectId 主体id
     * @param roleList 需要校验的角色列表
     * @return true拥有权限
     */
    public boolean hasSubjectRole(String subjectId, List<String> roleList) {
        List<String> authRoleList = authenticationDataLoader.getSubjectRole(subjectId);
        for (String authRole : authRoleList) {
            roleList.remove(authRole);
            if (roleList.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有权限
     * @param subjectId 主体id
     * @param permission 需要校验的权限
     * @return true拥有权限
     */
    public boolean hasSubjectPermission(String subjectId, String permission) {
        List<String> roleList = authenticationDataLoader.getSubjectRole(subjectId);
        for (String role : roleList) {
            if (hasRolePermission(role, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有权限
     * @param subjectId 主体id
     * @param permissionList 需要校验的权限
     * @return true拥有权限
     */
    public boolean hasSubjectPermission(String subjectId, List<String> permissionList) {
        List<String> roleList = authenticationDataLoader.getSubjectRole(subjectId);
        for (String role : roleList) {
            if (hasRolePermission(role, permissionList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否通过身份验证
     * @param accessToken 访问密钥
     * @return true通过
     */
    public boolean hasAuthentication(String accessToken) {
        if (StrUtil.isNotBlank(SubjectContext.getSubjectId())){
            return true;
        }
        if (StrUtil.isNotBlank(accessToken) && hasAccessToken(accessToken)) {
            String subjectId = parseAccessToken(accessToken);
            if (StrUtil.isNotBlank(subjectId)) {
                SubjectContext.setSubjectId(subjectId);
                return true;
            }
        }

        return false;
    }

    @Override
    public String getSubjectType() {
        return authenticationDataLoader.getSubjectType();
    }
}
