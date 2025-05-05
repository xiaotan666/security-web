package com.bytan.security.core.data.loader;

import com.bytan.security.core.data.dao.SecurityDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 身份权限信息内部缓存加载器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/16 17:56
 */
abstract class InteriorCacheAuthenticationDataLoader extends InitializeDataLoader {

    /**
     * 角色与权限
     */
    public String Role_Permission_Key = "security:authentication:role_permission";

    /**
     * 主体与角色
     */
    public String Subject_Role_Key = "security:authentication:subject_role";

    /**
     * 设置角色权限信息
     *
     * @param rolePermissionMap 角色权限信息
     */
    public void setRolePermission(Map<String, Object> rolePermissionMap) {
        this.securityDao.saveBatchByMap(buildLoaderKey(Role_Permission_Key), rolePermissionMap);
        this.securityDao.expire(buildLoaderKey(Role_Permission_Key), SecurityDao.NOT_EXPIRE);
    }

    /**
     * 设置角色权限信息
     *
     * @param role           角色信息
     * @param permissionList 权限信息
     */
    public void setRolePermission(String role, List<String> permissionList) {
        this.setRolePermission(
                new HashMap<String, Object>() {{
                    put(role, permissionList);
                }}
        );
    }

    /**
     * 获取角色权限信息
     *
     * @param role 角色
     * @return 权限列表
     */
    public List<String> getRolePermission(String role) {
        return this.securityDao.getByMap(buildLoaderKey(Role_Permission_Key), role);
    }

    /**
     * 设置主体角色信息
     *
     * @param subjectRoleMap 主体角色信息
     */
    public void setSubjectRole(Map<String, Object> subjectRoleMap) {
        this.securityDao.saveBatchByMap(buildLoaderKey(Subject_Role_Key), subjectRoleMap);
        this.securityDao.expire(buildLoaderKey(Subject_Role_Key), SecurityDao.NOT_EXPIRE);
    }

    /**
     * 设置主体角色信息
     *
     * @param subjectId 主体id
     * @param roleList  拥有的角色
     */
    public void setSubjectRole(String subjectId, List<String> roleList) {
        setSubjectRole(
                new HashMap<String, Object>() {{
                    put(subjectId, roleList);
                }}
        );
    }

    /**
     * 获取密钥信息
     *
     * @param subjectId 主体id
     * @return 角色列表
     */
    public List<String> getSubjectRole(String subjectId) {
        return this.securityDao.getByMap(buildLoaderKey(Subject_Role_Key), subjectId);
    }

}
