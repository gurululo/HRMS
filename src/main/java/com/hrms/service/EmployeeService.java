package com.hrms.service;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Employee;

import java.util.List;

/**
 * 员工业务服务接口
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<Employee> searchEmployees(String empNo, String name, Long deptId);

    Employee getEmployeeById(Long id);

    Employee getEmployeeByEmpNo(String empNo);

    ResultMsg addEmployee(Employee employee);

    ResultMsg updateEmployee(Employee employee);

    ResultMsg deleteEmployee(Long id);
}
