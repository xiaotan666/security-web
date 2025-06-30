package com.bytan.security.core;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.bytan.security.core.config.SecurityTokenConfig;
import com.bytan.security.core.subject.SubjectType;

/**
 * 身份验证Token相关功能实现
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/25 17:39
 */
public abstract class AuthenticationTokenRealm implements SubjectType {

    protected SecurityTokenConfig tokenConfig;

    private final String TOKEN_UID = "uid";
    private final String TOKEN_TYPE = "type";

    public AuthenticationTokenRealm(SecurityTokenConfig tokenProperties) {
        this.tokenConfig = tokenProperties;
    }

    /**
     * 获取访问密钥
     * @param subjectId 主体id
     * @return 访问密钥
     */
    public String getAccessToken(String subjectId) {
        DateTime nowTime = DateUtil.date();
        return JWT.create()
                .setIssuedAt(nowTime)//签发时间
                .setNotBefore(nowTime)//生效时间
                .setExpiresAt(DateUtil.offset(nowTime, DateField.SECOND, (int) tokenConfig.getTimeout()))//失效时间
                .setKey(getSubjectType().getBytes())
                .setHeader(TOKEN_TYPE, getSubjectType())
                .setPayload(TOKEN_UID, subjectId)
                .sign();
    }

    /**
     * 判断访问密钥的有效性
     * @param accessToken 访问密钥
     * @return true通过
     */
    public boolean hasAccessToken(String accessToken) {
        try {
            JWTValidator.of(accessToken)
                    .validateAlgorithm(JWTSignerUtil.hs256(getSubjectType().getBytes()))
                    .validateDate();
            return true;
        } catch (ValidateException e) {
            return false;
        }
    }

    /**
     * 解析访问密钥里面的主体id
     * @param accessToken 访问密钥
     * @return 主体id
     */
    public String parseAccessToken(String accessToken) {
        return JWTUtil.parseToken(accessToken).getPayload(TOKEN_UID).toString();
    }

}
