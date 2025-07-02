package com.bytan.security.core.data.loader;

import com.bytan.security.core.data.dao.SecurityDao;
import com.bytan.security.core.subject.SubjectType;

/**
 * 数据加载器接口
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/20 16:43
 */
public interface DataLoader extends SubjectType {

    /**
     * 获取数据存储方式
     * @return SecurityDao
     */
    SecurityDao getSecurityDao();

    /**
     * 设置数据存储方式
     */
    void setSecurityDao(SecurityDao securityDao);

}
