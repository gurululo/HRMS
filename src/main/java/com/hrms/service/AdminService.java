package com.hrms.service;

import com.hrms.common.ResultMsg;
import com.hrms.entity.AdminUser;

/**
 * 管理员认证服务接口
 */
public interface AdminService {

    ResultMsg login(String username, String password);

    AdminUser getAdmin();
}
