package com.hrms.dao;

import com.hrms.entity.AdminUser;

/**
 * 管理员数据访问接口
 */
public interface AdminDao {

    AdminUser getAdmin();

    void saveAdmin(AdminUser adminUser);
}
