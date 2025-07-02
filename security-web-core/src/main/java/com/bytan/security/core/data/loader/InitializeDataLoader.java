package com.bytan.security.core.data.loader;

import com.bytan.security.core.data.dao.DefaultMemoryDao;
import com.bytan.security.core.data.dao.SecurityDao;

/**
 * 初始化加载器
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/21 11:25
 */
public abstract class InitializeDataLoader implements DataLoader {

    protected SecurityDao securityDao = new DefaultMemoryDao();

    @Override
    public SecurityDao getSecurityDao() {
        return securityDao;
    }

    @Override
    public void setSecurityDao(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }

}
