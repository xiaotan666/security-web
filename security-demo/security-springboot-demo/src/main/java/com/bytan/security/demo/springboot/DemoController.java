package com.bytan.security.demo.springboot;

import com.bytan.security.core.annotation.RequiresLogin;
import com.bytan.security.core.annotation.RequiresPermissions;
import com.bytan.security.core.annotation.RequiresRoles;
import com.bytan.security.core.http.SecurityRequest;
import com.bytan.security.core.http.SecurityResponse;
import com.bytan.security.core.subject.SubjectContext;
import com.bytan.security.core.subject.SubjectType;
import com.bytan.security.springboot.starter.endpoint.AuthenticationEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:28
 */
@Controller
@RequestMapping("/admin")
public class DemoController extends AuthenticationEndpoint {

    /**
     * 区分认证类型
     * @return 类型
     */
    @Override
    public String getSubjectType() {
        return SubjectType.ADMIN;
    }

    @ResponseBody
    @GetMapping("/login")
    public Object login(SecurityRequest request, SecurityResponse response) {
        return super.login(request, response);
    }

    @RequiresLogin(type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/is_login")
    public Object isLogin() {
        return SubjectContext.getSubjectId();
    }

    @RequiresRoles(value = {"admin"}, type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/is_role")
    public Object isRole() {
        return SubjectContext.getSubjectId();
    }

    @RequiresPermissions(value = {"permissions_admin"}, type = SubjectType.ADMIN)
    @ResponseBody
    @GetMapping("/is_permissions")
    public Object isPermissions() {
        return SubjectContext.getSubjectId();
    }
}
