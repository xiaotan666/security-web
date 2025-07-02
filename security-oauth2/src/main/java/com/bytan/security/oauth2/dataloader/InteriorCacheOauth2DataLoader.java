package com.bytan.security.oauth2.dataloader;

import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.data.loader.InitializeDataLoader;

import java.util.Map;
import java.util.Set;

/**
 * Oauth2内部缓存数据加载器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/20 17:59
 */
abstract class InteriorCacheOauth2DataLoader extends InitializeDataLoader {

    /**
     * 主体关联授权范围key
     */
    public String[] Subject_Scope_Key = {"scope"};
    /**
     * client关联Secret key
     */
    public String[] Client_Secret_Key = {"client"};
    /**
     * 主体关联刷新令牌 key
     */
    public String[] Subject_RefreshToken_Key = {"refresh_token"};

    /**
     * 构建数据加载器的键
     * @param keys 键
     * @return dao的键
     */
    public String buildLoaderKey(String ...keys) {
        return "security" + ":" + "oauth2" + this.getSubjectType() + ":" + String.join(":", keys);
    }


    /**
     * 设置应用密钥信息
     * @param clientSecretMap 应用密钥信息
     */
    public void setClientSecret(Map<String, Object> clientSecretMap) {
        String key = buildLoaderKey(Client_Secret_Key);
        securityDao.saveBatchByMap(key, clientSecretMap);
        securityDao.expire(key, SecurityDao.NOT_EXPIRE);
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
     * 设置身份授权范围
     * @param subjectId 主体id
     * @param scopeList 授权范围列表
     */
    public void setSubjectScope(String subjectId, Set<String> scopeList) {
        String key = buildLoaderKey(Subject_Scope_Key);
        securityDao.saveByMap(key, subjectId, scopeList);
        securityDao.expire(key, SecurityDao.NOT_EXPIRE);
    }

    /**
     * 获取授权范围
     * @param subjectId 主体id
     * @return 授权范围列表
     */
    public Set<String> getSubjectScope(String subjectId) {
        return securityDao.getByMap(buildLoaderKey(Subject_Scope_Key), subjectId);
    }

    /**
     * 设置主体与刷新令牌信息
     * @param subjectId 主体id
     * @param tokenList 令牌列表
     */
    public void setSubjectRefreshToken(String subjectId, Set<String> tokenList) {
        String key = buildLoaderKey(Subject_RefreshToken_Key);
        securityDao.saveByMap(key, subjectId, tokenList);
        securityDao.expire(key, SecurityDao.NOT_EXPIRE);
    }

    /**
     * 获取主体与刷新令牌信息
     * @param subjectId 主体id
     * @return 主体与刷新令牌列表
     */
    public Set<String> getSubjectRefreshToken(String subjectId) {
        return securityDao.getByMap(buildLoaderKey(Subject_RefreshToken_Key), subjectId);
    }

}
