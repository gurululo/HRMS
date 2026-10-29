package com.hrms.entity;

import java.io.Serializable;

/**
 * 系统管理员实体
 */
public class AdminUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String salt;

    public AdminUser() {
    }

    public AdminUser(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
