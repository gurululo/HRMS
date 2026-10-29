package com.hrms.service;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Department;

import java.util.List;

/**
 * 部门业务服务接口
 */
public interface DepartmentService {

    List<Department> getAllDepartments();

    List<Department> getDepartmentTree();

    Department getDepartmentById(Long id);

    ResultMsg addDepartment(Department department);

    ResultMsg updateDepartment(Department department);

    ResultMsg deleteDepartment(Long id);
}
