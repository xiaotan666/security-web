package com.bytan.security.oauth2;

import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.subject.SubjectType;
import com.bytan.security.oauth2.config.RefreshTokenConfig;
import com.bytan.security.oauth2.dataloader.Oauth2DataLoader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Oauth2相关核心功能
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2024/12/27 11:24
 */
public class Oauth2Realm implements SubjectType {

    private final Oauth2DataLoader oauth2DataLoader;
    private final RefreshTokenConfig refreshTokenConfig;
    private final SecretKey signKey;
    private final SecretKey encryptKey;

    public Oauth2Realm(Oauth2DataLoader oauth2DataLoader, RefreshTokenConfig refreshTokenConfig) {
        this.oauth2DataLoader = oauth2DataLoader;
        this.refreshTokenConfig = refreshTokenConfig;
        this.signKey = getSignKey();
        this.encryptKey = getEncryptKey();
    }

    /**
     * 获取加密密钥
     * @return 加密密钥
     */
    private SecretKey getEncryptKey() {
        String encryptKey = refreshTokenConfig.getSignKey();
        if (encryptKey != null && !encryptKey.isEmpty()) {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(encryptKey));
        } else {
            String encryptDaoKey = oauth2DataLoader.buildLoaderKey("secret_key");
            SecurityDao securityDao = oauth2DataLoader.getSecurityDao();
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
        String signKey = refreshTokenConfig.getSignKey();
        if (signKey != null && !signKey.isEmpty()) {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey));
        } else {
            String signDaoKey = oauth2DataLoader.buildLoaderKey("encrypt_key");
            SecurityDao securityDao = oauth2DataLoader.getSecurityDao();
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
     * 校验clientId和secret是否正确
     * @param clientId 应用id
     * @param secret 应用密钥
     * @return ture正确
     */
    public boolean hasClientSecret(String clientId, String secret) {
        String clientSecret = oauth2DataLoader.getClientSecret(clientId);
        return clientSecret != null && clientSecret.equals(secret);
    }

    /**
     * 是否拥有授权范围
     * @param subjectId 主体id
     * @param scope 授权范围
     * @return ture拥有
     */
    public boolean hasAuthScope(String subjectId, String scope) {
        return oauth2DataLoader.getSubjectScope(subjectId).stream().anyMatch(scope::equals);
    }

    /**
     * 是否拥有授权范围
     * @param subjectId 主体id
     * @param scopeList 授权范围列表
     * @return ture拥有
     */
    public boolean hasAuthScope(String subjectId, List<String> scopeList) {
        List<String> subjectScope = oauth2DataLoader.getSubjectScope(subjectId);
        List<String> isScopeList = new ArrayList<>(scopeList);

        isScopeList.removeAll(subjectScope);
        return isScopeList.isEmpty();
    }

    /**
     * 生成刷新令牌
     *
     * @param subjectId 主体id
     * @return 刷新令牌
     */
    public String generateRefreshToken(String subjectId) {
        //签名
        String jws = Jwts.builder()
                .subject(subjectId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(refreshTokenConfig.getTimeout(), ChronoUnit.SECONDS)))
                .signWith(signKey)
                .compact();
        //加密签名结果
        String accessToken = Jwts.builder()
                .content(jws.getBytes(StandardCharsets.UTF_8), "application/jwt")
                .encryptWith(encryptKey, Jwts.KEY.DIRECT, Jwts.ENC.A128CBC_HS256)
                .compact();

        addSubjectAccessToken(subjectId, accessToken);
        return accessToken;
    }

    /**
     * 解析刷新令牌里面的主体id
     *
     * @param refreshToken 刷新令牌
     * @return 主体id
     */
    public String parseRefreshToken(String refreshToken) {
        byte[] jws = Jwts.parser()
                .decryptWith(encryptKey)
                .build()
                .parseEncryptedContent(refreshToken)
                .getPayload();
        Claims claims = Jwts.parser()
                .verifyWith(signKey)
                .build()
                .parseSignedClaims(new String(jws, StandardCharsets.UTF_8))
                .getPayload();

        return claims.getSubject();
    }

    /**
     * 处理刷新令牌前缀
     * @param requestRefreshToken 请求携带的刷新令牌
     * @return 刷新令牌
     */
    public String preRefreshTokenPrefix(String requestRefreshToken) {
        String prefix = refreshTokenConfig.getPrefix();
        if (prefix != null && !requestRefreshToken.startsWith(prefix)) {
            return null;
        }

        return prefix != null && !prefix.isEmpty() ? requestRefreshToken.substring(prefix.length() + 1) : requestRefreshToken;
    }

    /**
     * 回收刷新令牌
     * @param refreshToken 刷新令牌
     */
    public void recycleRefreshToken(String subjectId, String refreshToken) {
        List<String> subjectToken = oauth2DataLoader.getSubjectRefreshToken(subjectId);
        if (subjectToken == null) {
            subjectToken = new ArrayList<>();
        }
        subjectToken.remove(refreshToken);
        oauth2DataLoader.setSubjectRefreshToken(subjectId, subjectToken);
    }

    /**
     * 回收主体所有刷新令牌
     * @param subjectId 主体id
     */
    public void recycleRefreshTokenBySubject(String subjectId) {
        oauth2DataLoader.setSubjectRefreshToken(subjectId, new ArrayList<>());
    }

    /**
     * 添加主体刷新令牌信息
     * @param subjectId 主体id
     * @param refreshToken 刷新令牌
     */
    public void addSubjectAccessToken(String subjectId, String refreshToken) {
        List<String> subjectToken = oauth2DataLoader.getSubjectRefreshToken(subjectId);
        if (subjectToken == null) {
            subjectToken = new ArrayList<>();
        }

        subjectToken.add(refreshToken);
        oauth2DataLoader.setSubjectRefreshToken(subjectId, subjectToken);
    }

    @Override
    public String getSubjectType() {
        return oauth2DataLoader.getSubjectType();
    }
}
