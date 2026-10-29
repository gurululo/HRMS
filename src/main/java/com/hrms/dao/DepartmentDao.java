package com.hrms.dao;

import com.hrms.entity.Department;

import java.util.List;

/**
 * 部门数据访问接口
 */
public interface DepartmentDao {

    List<Department> selectAll();

    Department selectById(Long id);

    void insert(Department dept);

    void update(Department dept);

    void delete(Long id);
}
