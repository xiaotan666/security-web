package com.bytan.security.oauth2;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.bytan.security.core.subject.SubjectType;
import com.bytan.security.oauth2.config.SecurityOauth2TokenConfig;

/**
 * oauth2 Token相关功能实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/25 17:39
 */
public abstract class Oauth2TokenRealm implements SubjectType {

    protected SecurityOauth2TokenConfig oauth2TokenConfig;

    private final String TOKEN_UID = "uid";
    private final String TOKEN_TYPE = "type";

    public Oauth2TokenRealm(SecurityOauth2TokenConfig oauth2TokenConfig) {
        this.oauth2TokenConfig = oauth2TokenConfig;
    }

    /**
     * 获取刷新密钥
     * @param subjectId 主体id
     * @return 刷新密钥
     */
    public String getRefreshToken(String subjectId) {
        DateTime nowTime = DateUtil.date();

        return JWT.create()
                .setIssuedAt(nowTime)//签发时间
                .setNotBefore(nowTime)//生效时间
                .setExpiresAt(DateUtil.offset(nowTime, DateField.SECOND, (int) oauth2TokenConfig.getRefreshTimeout()))//失效时间
                .setKey(getSubjectType().getBytes())
                .setHeader(TOKEN_TYPE, getSubjectType())
                .setPayload(TOKEN_UID, subjectId)
                .sign();
    }

    /**
     * 判断密钥的有效性
     * @param refreshToken 刷新密钥
     * @return true通过
     */
    public boolean hasRefreshToken(String refreshToken) {
        try {
            JWTValidator.of(refreshToken)
                    .validateAlgorithm(JWTSignerUtil.hs256(getSubjectType().getBytes()))
                    .validateDate();
        } catch (ValidateException e) {
            return false;
        }

        return true;
    }

    /**
     * 解析刷新密钥里面的主体id
     * @param refreshToken 刷新密钥
     * @return 主体id
     */
    public String parseRefreshToken(String refreshToken) {
        return JWTUtil.parseToken(refreshToken).getHeader(TOKEN_UID).toString();
    }

}
