package com.bytan.security.springboot.web;

import com.bytan.security.core.context.HttpContext;
import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.http.SecurityServletRequest;
import com.bytan.security.core.http.SecurityServletResponse;
import com.bytan.security.springboot.utils.SpringMVCUtil;

/**
 * 默认http上下文
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/10 14:08
 */
public class DefaultHttpContext implements HttpContext {

    @Override
    public SecurityRequest getRequest() {
        return new SecurityServletRequest(SpringMVCUtil.getRequest());
    }

    @Override
    public SecurityResponse getResponse() {
        return new SecurityServletResponse(SpringMVCUtil.getResponse());
    }

}
