package com.bytan.security.web.demo.springboot;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.annotation.RequiresLogin;
import com.bytan.security.core.annotation.RequiresPermissions;
import com.bytan.security.core.annotation.RequiresRoles;
import com.bytan.security.core.config.AccessTokenConfig;
import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.service.AuthenticationService;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义端点服务
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:28
 */
@Controller
@RequestMapping("/admin")
public class DemoController implements SubjectType {

    private final DemoDataLoader demoDataLoader;
    private final SecurityManager securityManager;
    private final AuthenticationService authenticationService;

    @Autowired
    public DemoController(SecurityManager securityManager, DemoDataLoader demoDataLoader) {
        this.demoDataLoader = demoDataLoader;
        this.authenticationService = new AuthenticationService(securityManager, getSubjectType());
        this.securityManager = securityManager;
    }

    /**
     * 区分认证类型
     * @return 类型
     */
    @Override
    public String getSubjectType() {
        return SubjectType.ADMIN;
    }

    /**
     * 登录接口
     * @param request 当前请求
     * @param response 响应
     * @return LoginResponseModel
     */
    @ResponseBody
    @GetMapping("/login")
    public Object login(SecurityRequest request, SecurityResponse response) {
        Map<String, Object> parameterMap = request.getParameterMap()
                .entrySet()
                .stream()
                .flatMap(entry -> Arrays.stream(entry.getValue()).map(value -> new AbstractMap.SimpleEntry<>(entry.getKey(), value)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> newVal));

        return authenticationService.login(parameterMap);
    }

    /**
     * 登出接口
     * @param request 当前请求
     * @param response 响应
     * @return 已登出账号
     */
    @RequiresLogin(type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/logout")
    public Object logout(SecurityRequest request, SecurityResponse response) {
        AccessTokenConfig tokenConfig = securityManager.getAccessTokenConfig(getSubjectType());
        String accessToken = request.getHeader(tokenConfig.getRequestHeader());
        authenticationService.logout(accessToken, SubjectContext.getSubjectId());

        return "已登出账号";
    }

    /**
     * 校验当前请求主体的登录状态
     * @return 主体id （未登录则抛AuthenticationException异常）
     */
    @RequiresLogin(type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/is_login")
    public Object isLogin() {
        return SubjectContext.getSubjectId();
    }

    /**
     * 校验当前请求主体是否拥有该角色
     * @return 主体id （未登录则抛AuthenticationException异常）
     */
    @RequiresRoles(value = {"user"}, type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/is_role")
    public Object isRole() {
        return SubjectContext.getSubjectId();
    }

    /**
     * 校验当前请求主体是否拥有该权限
     * @return 主体id （未登录则抛AuthenticationException异常）
     */
    @RequiresPermissions(value = {"permissions_admin"}, type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/is_permissions")
    public Object isPermissions() {
        return SubjectContext.getSubjectId();
    }

    @RequiresLogin(type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/add_subject_role")
    public Object addSubjectRole(@RequestParam String roleKey) {
        String subjectId = SubjectContext.getSubjectId();
        List<String> subjectRole = demoDataLoader.getSubjectRole(subjectId);
        subjectRole.add(roleKey);
        demoDataLoader.setSubjectRole(subjectId, subjectRole);

        return "success";
    }
}
