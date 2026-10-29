package com.hrms.dao.impl;

import com.hrms.dao.AdminDao;
import com.hrms.entity.AdminUser;
import com.hrms.util.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 管理员数据访问实现类
 */
@Repository
public class AdminDaoImpl implements AdminDao {

    private static final String FILE_NAME = "admin.json";

    @Autowired
    private DataStorageManager dataStorageManager;

    @Override
    public AdminUser getAdmin() {
        return dataStorageManager.loadObject(FILE_NAME, AdminUser.class);
    }

    @Override
    public void saveAdmin(AdminUser adminUser) {
        dataStorageManager.saveObject(FILE_NAME, adminUser);
    }
}
