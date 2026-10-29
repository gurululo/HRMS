package com.hrms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门实体
 */
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String deptName;
    private Long parentId;

    // 非持久化展示辅助字段
    private String parentName;
    private List<Department> children = new ArrayList<Department>();

    public Department() {
    }

    public Department(Long id, String deptName, Long parentId) {
        this.id = id;
        this.deptName = deptName;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }
}
