package com.hrms.entity;

import java.io.Serializable;

/**
 * 员工实体
 */
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String empNo;
    private String name;
    private Long deptId;
    private Long positionId;

    // 视图展示关联字段
    private String deptName;
    private String positionName;

    public Employee() {
    }

    public Employee(Long id, String empNo, String name, Long deptId, Long positionId) {
        this.id = id;
        this.empNo = empNo;
        this.name = name;
        this.deptId = deptId;
        this.positionId = positionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
