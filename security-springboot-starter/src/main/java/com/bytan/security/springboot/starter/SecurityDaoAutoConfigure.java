package com.bytan.security.springboot.starter;

import com.bytan.security.core.SecurityManager;
import com.bytan.security.core.data.dao.DefaultMemoryDao;
import com.bytan.security.core.data.dao.SecurityDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/3/13 15:39
 */
@ConditionalOnBean(SecurityManager.class)
@ConditionalOnMissingBean(value = SecurityDao.class)
public class SecurityDaoAutoConfigure {

    @Bean
    public SecurityDao securityDao() {
        return new DefaultMemoryDao();
    }

}
