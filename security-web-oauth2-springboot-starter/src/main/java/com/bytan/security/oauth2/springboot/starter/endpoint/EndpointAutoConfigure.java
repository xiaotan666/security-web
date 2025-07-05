package com.bytan.security.oauth2.springboot.starter.endpoint;

import com.bytan.security.oauth2.springboot.starter.EnabledSecurityOauth2;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 端点服务自动配置类
 */
public class EndpointAutoConfigure implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importMetadata.getAnnotationAttributes(EnabledSecurityOauth2.class.getName());
        if (attributes != null) {
            Object endpoint = attributes.get("endpoint");
            if (Boolean.TRUE.equals(endpoint)) {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(OAuth2Endpoint.class);
//                builder.addDependsOn("securityManager");
                registry.registerBeanDefinition("oAuth2Endpoint", builder.getBeanDefinition());
            }
        }
    }
}
