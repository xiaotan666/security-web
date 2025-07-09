package com.bytan.security.core.data.loader;

import com.bytan.security.core.data.dao.SecurityDao;

import java.util.ArrayList;
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
    public String[] Role_Permission_Key = {"role_permission"};
    /**
     * 主体与角色
     */
    public String[] Subject_Role_Key = {"subject_role"};
    /**
     * 主体关联访问令牌key
     */
    public String[] Subject_AccessToken_Key = {"access_token"};

    /**
     * 构建数据加载器的键
     * @param keys 键
     * @return dao的键
     */
    public String buildLoaderKey(String ...keys) {
        return "security" + ":" + "authentication" + ":" + this.getSubjectType() + ":" + String.join(":", keys);
    }

    /**
     * 设置角色权限信息
     *
     * @param rolePermissionMap 角色权限信息
     */
    public void setRolePermission(Map<String, Object> rolePermissionMap) {
        String key = buildLoaderKey(Role_Permission_Key);
        securityDao.saveBatchByMap(key, rolePermissionMap);
        securityDao.expire(key, SecurityDao.NOT_EXPIRE);
    }

    /**
     * 设置角色权限信息
     *
     * @param role           角色信息
     * @param permissionList 权限信息
     */
    public void setRolePermission(String role, List<String> permissionList) {
        setRolePermission(
                new HashMap<String, Object>() {{
                    put(role, new ArrayList<String>(permissionList));
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
        return securityDao.getByMap(buildLoaderKey(Role_Permission_Key), role);
    }

    /**
     * 清空角色权限信息
     * @param role 主体id
     */
    public void clearRolePermission(String role) {
        securityDao.deleteByMap(buildLoaderKey(Role_Permission_Key), role);
    }

    /**
     * 设置主体角色信息
     *
     * @param subjectRoleMap 主体角色信息
     */
    public void setSubjectRole(Map<String, Object> subjectRoleMap) {
        String key = buildLoaderKey(Subject_Role_Key);
        securityDao.saveBatchByMap(key, subjectRoleMap);
        securityDao.expire(key, SecurityDao.NOT_EXPIRE);
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
                    put(subjectId, new ArrayList<String>(roleList));
                }}
        );
    }

    /**
     * 获取主体角色信息
     *
     * @param subjectId 主体id
     * @return 角色列表
     */
    public List<String> getSubjectRole(String subjectId) {
        return securityDao.getByMap(buildLoaderKey(Subject_Role_Key), subjectId);
    }

    /**
     * 清空主体角色信息
     * @param subjectId 主体id
     */
    public void clearSubjectRole(String subjectId) {
        securityDao.deleteByMap(buildLoaderKey(Subject_Role_Key), subjectId);
    }

    /**
     * 设置主体与访问令牌信息
     * @param subjectId 主体id
     * @param tokenList 令牌列表
     */
    public void setSubjectAccessToken(String subjectId, List<String> tokenList) {
        String key = buildLoaderKey(Subject_AccessToken_Key);
        securityDao.saveByMap(key, subjectId, new ArrayList<String>(tokenList));
        securityDao.expire(key, SecurityDao.NOT_EXPIRE);
    }

    /**
     * 获取主体与访问令牌信息
     * @param subjectId 主体id
     * @return 主体与访问令牌列表
     */
    public List<String> getSubjectAccessToken(String subjectId) {
        return securityDao.getByMap(buildLoaderKey(Subject_AccessToken_Key), subjectId);
    }

    /**
     * 清空主体访问令牌信息
     * @param subjectId 主体id
     */
    public void clearSubjectAccessToken(String subjectId) {
        securityDao.deleteByMap(buildLoaderKey(Subject_AccessToken_Key), subjectId);
    }
}
