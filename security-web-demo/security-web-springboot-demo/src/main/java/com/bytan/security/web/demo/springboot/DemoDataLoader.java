package com.bytan.security.web.demo.springboot;

import com.bytan.security.core.data.loader.AuthenticationDataLoader;
import com.bytan.security.core.subject.SubjectType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义数据加载器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/6/30 16:27
 */
@Component
public class DemoDataLoader extends AuthenticationDataLoader {

    @Override
    public List<String> doGetRolePermission(String role) {
        //加载角色关联的权限信息
        return List.of("permissions_admin");
    }

    @Override
    public List<String> doGetSubjectRole(String subjectId) {
        //加载主体关联的角色信息
        return List.of("admin");
    }

    @Override
    public String getSubjectType() {
        //自定义主体类型
        return SubjectType.ADMIN;
    }
}
