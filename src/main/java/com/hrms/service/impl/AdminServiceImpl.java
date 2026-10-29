package com.hrms.service.impl;

import com.hrms.common.ResultMsg;
import com.hrms.dao.AdminDao;
import com.hrms.entity.AdminUser;
import com.hrms.service.AdminService;
import com.hrms.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员认证服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminDao adminDao;

    @Override
    public ResultMsg login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResultMsg.error("用户名或密码不能为空");
        }

        AdminUser admin = adminDao.getAdmin();
        if (admin == null) {
            logger.error("管理员账户数据未初始化");
            return ResultMsg.error("系统配置异常，未找到管理员配置");
        }

        if (!admin.getUsername().equalsIgnoreCase(username.trim())) {
            return ResultMsg.error("用户名或密码错误");
        }

        boolean match = PasswordUtil.verify(password.trim(), admin.getSalt(), admin.getPassword());
        if (!match) {
            return ResultMsg.error("用户名或密码错误");
        }

        logger.info("管理员 [{}] 登录成功", username);
        return ResultMsg.success("登录成功", admin.getUsername());
    }

    @Override
    public AdminUser getAdmin() {
        return adminDao.getAdmin();
    }
}
