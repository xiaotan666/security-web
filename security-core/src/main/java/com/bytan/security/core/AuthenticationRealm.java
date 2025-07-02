package com.bytan.security.core;

import com.bytan.security.core.config.AccessTokenConfig;
import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.exception.AuthenticationException;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.subject.SubjectType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 身份验证相关功能实现
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/10  23:41
 */
public class AuthenticationRealm implements SubjectType {

    private final AuthenticationDataLoader authenticationDataLoader;
    private final AccessTokenConfig tokenConfig;
    private final SecretKey signKey;
    private final SecretKey encryptKey;

    public AuthenticationRealm(AuthenticationDataLoader authenticationDataLoader, AccessTokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        this.authenticationDataLoader = authenticationDataLoader;
        this.signKey = getSignKey();
        this.encryptKey = getEncryptKey();
    }

    /**
     * 获取加密密钥
     * @return 加密密钥
     */
    private SecretKey getEncryptKey() {
        String encryptKey = tokenConfig.getSignKey();
        if (encryptKey != null && !encryptKey.isEmpty()) {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(encryptKey));
        } else {
            String encryptDaoKey = authenticationDataLoader.buildLoaderKey("secret_key");
            SecurityDao securityDao = authenticationDataLoader.getSecurityDao();
            encryptKey = securityDao.get(encryptDaoKey);
            if (encryptKey != null && !encryptKey.isEmpty()) {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(encryptKey));
            } else {
                SecretKey secretKey = Jwts.SIG.HS256.key().build();
                securityDao.save(encryptDaoKey, Encoders.BASE64.encode(secretKey.getEncoded()));
                return secretKey;
            }
        }
    }

    /**
     * 获取签名密钥
     * @return 签名密钥
     */
    private SecretKey getSignKey() {
        String signKey = tokenConfig.getSignKey();
        if (signKey != null && !signKey.isEmpty()) {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey));
        } else {
            String signDaoKey = authenticationDataLoader.buildLoaderKey("encrypt_key");
            SecurityDao securityDao = authenticationDataLoader.getSecurityDao();
            signKey = securityDao.get(signDaoKey);
            if (signKey != null && !signKey.isEmpty()) {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey));
            } else {
                SecretKey secretKey = Jwts.SIG.HS512.key().build();
                securityDao.save(signDaoKey, Encoders.BASE64.encode(secretKey.getEncoded()));
                return secretKey;
            }
        }
    }

    /**
     * 判断角色是否拥有权限
     *
     * @param role       角色
     * @param permission 需要校验的权限
     * @return true拥有权限
     */
    public boolean hasRolePermission(String role, String permission) {
        return authenticationDataLoader.getRolePermission(role)
                .stream()
                .anyMatch(permission::equals);
    }

    /**
     * 判断角色是否拥有权限
     *
     * @param role           角色
     * @param permissionList 需要校验的权限列表
     * @return true拥有权限
     */
    public boolean hasRolePermission(String role, Set<String> permissionList) {
        return authenticationDataLoader.getRolePermission(role).containsAll(permissionList);
    }

    /**
     * 是否拥有该角色
     *
     * @param subjectId 主体id
     * @param role      需要校验的角色
     * @return true拥有权限
     */
    public boolean hasSubjectRole(String subjectId, String role) {
        return authenticationDataLoader.getSubjectRole(subjectId)
                .stream()
                .anyMatch(role::equals);
    }

    /**
     * 判断用户是否拥有该角色
     *
     * @param subjectId 主体id
     * @param roleList  需要校验的角色列表
     * @return true拥有权限
     */
    public boolean hasSubjectRole(String subjectId, Set<String> roleList) {
        return authenticationDataLoader.getSubjectRole(subjectId).containsAll(roleList);
    }

    /**
     * 判断用户是否拥有权限
     *
     * @param subjectId  主体id
     * @param permission 需要校验的权限
     * @return true拥有权限
     */
    public boolean hasSubjectPermission(String subjectId, String permission) {
        Set<String> roleList = authenticationDataLoader.getSubjectRole(subjectId);
        for (String role : roleList) {
            if (hasRolePermission(role, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有权限
     *
     * @param subjectId      主体id
     * @param permissionList 需要校验的权限
     * @return true拥有权限
     */
    public boolean hasSubjectPermission(String subjectId, Set<String> permissionList) {
        Set<String> roleList = authenticationDataLoader.getSubjectRole(subjectId);
        for (String role : roleList) {
            if (hasRolePermission(role, permissionList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前访问是否合法
     *
     * @param accessToken 访问令牌
     */
    public void isAuthentication(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                String subjectId = parseAccessToken(accessToken);
                if (subjectId != null && !subjectId.isEmpty()) {
                    Set<String> subjectTokenList = authenticationDataLoader.getSubjectAccessToken(subjectId);
                    boolean match = subjectTokenList.contains(accessToken);
                    if (!match) {
                        throw new AuthenticationException();
                    }
                    SubjectContext.setSubjectId(subjectId);
                }
            } catch (JwtException e) {
                throw new AuthenticationException();
            }
        } else {
            String subjectId = SubjectContext.getSubjectId();
            if (subjectId != null && !subjectId.isEmpty()) {
                return;
            }

            throw new AuthenticationException(AuthenticationException.USER_NOT_LOGIN);
        }
    }

    /**
     * 生成访问令牌
     *
     * @param subjectId 主体id
     * @return 访问令牌
     */
    public String generateAccessToken(String subjectId) {
        //签名
        String jws = Jwts.builder()
                .subject(subjectId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(tokenConfig.getTimeout(), ChronoUnit.SECONDS)))
                .signWith(signKey)
                .compact();
        //加密签名结果
        String jwe = Jwts.builder()
                .content(jws.getBytes(StandardCharsets.UTF_8), "application/jwt")
                .encryptWith(encryptKey, Jwts.KEY.DIRECT, Jwts.ENC.A128CBC_HS256)
                .compact();

        String prefix = tokenConfig.getPrefix();
        String accessToken = prefix != null && !prefix.isEmpty() ? prefix + " " + jwe : jwe;
        addSubjectAccessToken(subjectId, accessToken);
        return accessToken;
    }

    /**
     * 解析访问令牌里面的主体id
     *
     * @param accessToken 访问密钥
     * @return 主体id
     */
    public String parseAccessToken(String accessToken) {
        String prefix = tokenConfig.getPrefix();
        String token = prefix != null && !prefix.isEmpty() ?  accessToken.substring(prefix.length() + 1) : accessToken;

        byte[] jws = Jwts.parser()
                .decryptWith(encryptKey)
                .build()
                .parseEncryptedContent(token)
                .getPayload();
        Claims claims = Jwts.parser()
                .verifyWith(signKey)
                .build()
                .parseSignedClaims(new String(jws, StandardCharsets.UTF_8))
                .getPayload();

        return claims.getSubject();
    }

    /**
     * 回收访问令牌
     * @param accessToken 访问令牌
     */
    public void recycleAccessToken(String subjectId, String accessToken) {
        Set<String> subjectToken = authenticationDataLoader.getSubjectAccessToken(subjectId);
        if (subjectToken == null) {
            subjectToken = new HashSet<>();
        }
        subjectToken.remove(accessToken);
        authenticationDataLoader.setSubjectAccessToken(subjectId, subjectToken);
    }

    /**
     * 回收主体所有访问令牌
     * @param subjectId 主体id
     */
    public void recycleAccessTokenBySubject(String subjectId) {
        authenticationDataLoader.setSubjectAccessToken(subjectId, Set.of());
    }

    /**
     * 添加主体令牌信息
     * @param subjectId 主体id
     * @param accessToken 访问令牌
     */
    public void addSubjectAccessToken(String subjectId, String accessToken) {
        Set<String> subjectToken = authenticationDataLoader.getSubjectAccessToken(subjectId);
        if (subjectToken == null) {
            subjectToken = new HashSet<>();
        }

        subjectToken.add(accessToken);
        authenticationDataLoader.setSubjectAccessToken(subjectId, subjectToken);
    }

    @Override
    public String getSubjectType() {
        return authenticationDataLoader.getSubjectType();
    }
}
