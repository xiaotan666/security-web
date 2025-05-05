package com.bytan.security.oauth2.dataloader;

import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.data.loader.InitializeDataLoader;

import java.util.List;
import java.util.Map;

/**
 * Oauth2内部缓存数据加载器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/20 17:59
 */
abstract class InteriorCacheOauth2DataLoader extends InitializeDataLoader {

    /**
     * 主体 关联 授权范围key
     */
    public static String Subject_Scope_Key = "security:oauth2:scope";
    /**
     * client关联Secret key
     */
    public static String Client_Secret_Key = "security:oauth2:client";

    /**
     * 添加应用密钥信息
     * @param clientSecretMap 应用密钥信息
     */
    public void addClientSecret(Map<String, Object> clientSecretMap) {
        securityDao.saveBatchByMap(buildLoaderKey(Client_Secret_Key), clientSecretMap);
        securityDao.expire(buildLoaderKey(Client_Secret_Key), SecurityDao.NOT_EXPIRE);
    }

    /**
     * 获取应用密钥信息
     * @param clientId 应用id
     * @return 密钥信息
     */
    public String getClientSecret(String clientId) {
        return securityDao.getByMap(buildLoaderKey(Client_Secret_Key), clientId);
    }

    /**
     * 添加身份授权范围
     * @param subjectId 主体id
     * @param scopeList 授权范围列表
     */
    public void addSubjectScope(String subjectId, List<String> scopeList) {
        securityDao.saveByMap(buildLoaderKey(Subject_Scope_Key), subjectId, scopeList);
        securityDao.expire(buildLoaderKey(Subject_Scope_Key), SecurityDao.NOT_EXPIRE);
    }

    /**
     * 获取授权范围
     * @param subjectId 主体id
     * @return 授权范围列表
     */
    public List<String> getSubjectScope(String subjectId) {
        return securityDao.getByMap(buildLoaderKey(Subject_Scope_Key), subjectId);
    }

}
