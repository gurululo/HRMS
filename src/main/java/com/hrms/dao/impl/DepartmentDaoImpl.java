package com.hrms.dao.impl;

import com.hrms.dao.DepartmentDao;
import com.hrms.entity.Department;
import com.hrms.util.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 部门数据访问实现类
 */
@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    private static final String FILE_NAME = "departments.json";

    @Autowired
    private DataStorageManager dataStorageManager;

    @Override
    public List<Department> selectAll() {
        return dataStorageManager.loadList(FILE_NAME, Department.class);
    }

    @Override
    public Department selectById(Long id) {
        if (id == null) {
            return null;
        }
        List<Department> list = selectAll();
        for (Department d : list) {
            if (id.equals(d.getId())) {
                return d;
            }
        }
        return null;
    }

    @Override
    public synchronized void insert(Department dept) {
        List<Department> list = selectAll();
        if (dept.getId() == null) {
            dept.setId(dataStorageManager.generateNextId(FILE_NAME));
        }
        list.add(dept);
        dataStorageManager.saveList(FILE_NAME, list);
    }

    @Override
    public synchronized void update(Department dept) {
        if (dept == null || dept.getId() == null) {
            return;
        }
        List<Department> list = selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (dept.getId().equals(list.get(i).getId())) {
                list.set(i, dept);
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
        List<Department> list = selectAll();
        Iterator<Department> it = list.iterator();
        while (it.hasNext()) {
            Department d = it.next();
            if (id.equals(d.getId())) {
                it.remove();
                break;
            }
        }
        dataStorageManager.saveList(FILE_NAME, list);
    }
}
