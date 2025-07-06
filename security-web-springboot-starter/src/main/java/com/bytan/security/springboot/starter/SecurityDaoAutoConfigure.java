package com.bytan.security.springboot.starter;

import com.bytan.security.core.data.dao.DefaultMemoryDao;
import com.bytan.security.core.data.dao.SecurityDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnBean(annotation = EnabledSecurityWeb.class)
public class SecurityDaoAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(value = SecurityDao.class)
    public SecurityDao securityDao() {
        return new DefaultMemoryDao();
    }

}
