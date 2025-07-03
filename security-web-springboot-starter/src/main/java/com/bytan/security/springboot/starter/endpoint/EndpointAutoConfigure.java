package com.bytan.security.springboot.starter.endpoint;

import com.bytan.security.springboot.starter.EnabledSecurityWeb;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class EndpointAutoConfigure implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importMetadata.getAnnotationAttributes(EnabledSecurityWeb.class.getName());
        if (attributes != null) {
            Object endpoint = attributes.get("endpoint");
            if (Boolean.TRUE.equals(endpoint)) {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AuthenticationEndpoint.class);
//                builder.addDependsOn("securityManager");
                registry.registerBeanDefinition("authenticationEndpoint", builder.getBeanDefinition());
            }
        }
    }
}
