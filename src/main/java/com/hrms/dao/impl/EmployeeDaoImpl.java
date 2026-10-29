package com.hrms.dao.impl;

import com.hrms.dao.EmployeeDao;
import com.hrms.entity.Employee;
import com.hrms.util.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * 员工数据访问实现类
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static final String FILE_NAME = "employees.json";

    @Autowired
    private DataStorageManager dataStorageManager;

    @Override
    public List<Employee> selectAll() {
        return dataStorageManager.loadList(FILE_NAME, Employee.class);
    }

    @Override
    public Employee selectById(Long id) {
        if (id == null) {
            return null;
        }
        List<Employee> list = selectAll();
        for (Employee e : list) {
            if (id.equals(e.getId())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Employee selectByEmpNo(String empNo) {
        if (empNo == null || empNo.trim().isEmpty()) {
            return null;
        }
        List<Employee> list = selectAll();
        for (Employee e : list) {
            if (empNo.trim().equalsIgnoreCase(e.getEmpNo())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public synchronized void insert(Employee employee) {
        List<Employee> list = selectAll();
        if (employee.getId() == null) {
            employee.setId(dataStorageManager.generateNextId(FILE_NAME));
        }
        list.add(employee);
        dataStorageManager.saveList(FILE_NAME, list);
    }

    @Override
    public synchronized void update(Employee employee) {
        if (employee == null || employee.getId() == null) {
            return;
        }
        List<Employee> list = selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (employee.getId().equals(list.get(i).getId())) {
                list.set(i, employee);
                break;
            }
        }
        dataStorageManager.saveList(FILE_NAME, list);
    }

    @Override
    public synchronized void delete(Long id) {
        if (id == null) {
            return;
        }
        List<Employee> list = selectAll();
        Iterator<Employee> it = list.iterator();
        while (it.hasNext()) {
            Employee e = it.next();
            if (id.equals(e.getId())) {
                it.remove();
                break;
            }
        }
        dataStorageManager.saveList(FILE_NAME, list);
    }
}
