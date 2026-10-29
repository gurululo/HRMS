package com.hrms.dao;

import com.hrms.entity.Employee;

import java.util.List;

/**
 * 员工数据访问接口
 */
public interface EmployeeDao {

    List<Employee> selectAll();

    Employee selectById(Long id);

    Employee selectByEmpNo(String empNo);

    void insert(Employee employee);

    void update(Employee employee);

    void delete(Long id);
}
