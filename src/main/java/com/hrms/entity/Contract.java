package com.hrms.entity;

import java.io.Serializable;

/**
 * 劳动合同实体
 */
public class Contract implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String empNo;
    private String startDate;
    private String endDate;

    // 视图展示关联字段
    private String employeeName;

    public Contract() {
    }

    public Contract(Long id, String empNo, String startDate, String endDate) {
        this.id = id;
        this.empNo = empNo;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
