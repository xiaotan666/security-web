package com.bytan.security.core;

import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.config.SecurityTokenConfig;
import com.bytan.security.core.subject.SubjectContext;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

/**
 * 身份验证相关功能实现
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/10  23:41
 */
public class AuthenticationRealm {

    private final AuthenticationDataLoader authenticationDataLoader;
    private final SecurityTokenConfig tokenConfig;

    public AuthenticationRealm(AuthenticationDataLoader authenticationDataLoader, SecurityTokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        this.authenticationDataLoader = authenticationDataLoader;
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
     * 是否通过身份验证
     *
     * @param accessToken 访问密钥
     * @return true通过
     */
    public boolean hasAuthentication(String accessToken) {
        String subjectId = SubjectContext.getSubjectId();
        if (subjectId != null) {
            return true;
        }
        if (accessToken != null && hasAccessToken(accessToken)) {
            subjectId = parseAccessToken(accessToken);
        }

        if (subjectId != null) {
            SubjectContext.setSubjectId(subjectId);
            return true;
        }
        return false;
    }

    /**
     * 获取访问密钥
     *
     * @param subjectId 主体id
     * @return 访问密钥
     */
    public String getAccessToken(String subjectId) {
        return Jwts.builder()
                .subject(subjectId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(tokenConfig.getTimeout(), ChronoUnit.SECONDS)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(authenticationDataLoader.getSubjectType())), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 判断访问密钥的是否合法
     *
     * @param accessToken 访问密钥
     * @return 是否合法
     */
    public boolean hasAccessToken(String accessToken) {
        try {
            return parseAccessToken(accessToken) != null;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 解析访问密钥里面的主体id
     *
     * @param accessToken 访问密钥
     * @return 主体id
     */
    public String parseAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .decryptWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(authenticationDataLoader.getSubjectType())))
                .build()
                .parseEncryptedClaims(accessToken)
                .getPayload();
        return claims.getSubject();
    }

}
