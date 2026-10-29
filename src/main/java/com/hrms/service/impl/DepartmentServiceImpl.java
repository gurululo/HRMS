package com.hrms.service.impl;

import com.hrms.common.ResultMsg;
import com.hrms.dao.DepartmentDao;
import com.hrms.dao.EmployeeDao;
import com.hrms.entity.Department;
import com.hrms.entity.Employee;
import com.hrms.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门业务服务实现类
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Department> getAllDepartments() {
        List<Department> list = departmentDao.selectAll();
        Map<Long, String> nameMap = new HashMap<Long, String>();
        for (Department d : list) {
            nameMap.put(d.getId(), d.getDeptName());
        }
        for (Department d : list) {
            if (d.getParentId() != null && d.getParentId() > 0) {
                d.setParentName(nameMap.get(d.getParentId()));
            } else {
                d.setParentName("顶级部门");
            }
        }
        return list;
    }

    @Override
    public List<Department> getDepartmentTree() {
        List<Department> all = getAllDepartments();
        Map<Long, Department> nodeMap = new HashMap<Long, Department>();
        for (Department d : all) {
            d.setChildren(new ArrayList<Department>());
            nodeMap.put(d.getId(), d);
        }

        List<Department> rootList = new ArrayList<Department>();
        for (Department d : all) {
            Long parentId = d.getParentId();
            if (parentId == null || parentId <= 0 || !nodeMap.containsKey(parentId)) {
                rootList.add(d);
            } else {
                Department parentNode = nodeMap.get(parentId);
                parentNode.getChildren().add(d);
            }
        }
        return rootList;
    }

    @Override
    public Department getDepartmentById(Long id) {
        Department dept = departmentDao.selectById(id);
        if (dept != null && dept.getParentId() != null && dept.getParentId() > 0) {
            Department parent = departmentDao.selectById(dept.getParentId());
            if (parent != null) {
                dept.setParentName(parent.getDeptName());
            }
        }
        return dept;
    }

    @Override
    public ResultMsg addDepartment(Department department) {
        if (department == null || StringUtils.isBlank(department.getDeptName())) {
            return ResultMsg.error("部门名称不能为空");
        }
        department.setDeptName(department.getDeptName().trim());

        // 校验同级部门名称是否重复
        List<Department> all = departmentDao.selectAll();
        Long parentId = department.getParentId() == null ? 0L : department.getParentId();
        department.setParentId(parentId);

        for (Department d : all) {
            Long pId = d.getParentId() == null ? 0L : d.getParentId();
            if (pId.equals(parentId) && d.getDeptName().equalsIgnoreCase(department.getDeptName())) {
                return ResultMsg.error("同级下已存在同名部门");
            }
        }

        departmentDao.insert(department);
        return ResultMsg.success("添加部门成功");
    }

    @Override
    public ResultMsg updateDepartment(Department department) {
        if (department == null || department.getId() == null) {
            return ResultMsg.error("部门参数错误");
        }
        if (StringUtils.isBlank(department.getDeptName())) {
            return ResultMsg.error("部门名称不能为空");
        }
        department.setDeptName(department.getDeptName().trim());

        Department existing = departmentDao.selectById(department.getId());
        if (existing == null) {
            return ResultMsg.error("部门不存在或已被删除");
        }

        Long parentId = department.getParentId() == null ? 0L : department.getParentId();
        if (department.getId().equals(parentId)) {
            return ResultMsg.error("上级部门不能设置为自身");
        }

        // 校验是否将上级设置为自己的子部门
        if (isDescendant(department.getId(), parentId)) {
            return ResultMsg.error("上级部门不能选择自己的下级部门");
        }

        department.setParentId(parentId);
        departmentDao.update(department);
        return ResultMsg.success("修改部门成功");
    }

    private boolean isDescendant(Long currentId, Long targetParentId) {
        if (targetParentId == null || targetParentId <= 0) {
            return false;
        }
        Long cursor = targetParentId;
        while (cursor != null && cursor > 0) {
            if (cursor.equals(currentId)) {
                return true;
            }
            Department p = departmentDao.selectById(cursor);
            if (p == null) {
                break;
            }
            cursor = p.getParentId();
        }
        return false;
    }

    @Override
    public ResultMsg deleteDepartment(Long id) {
        if (id == null) {
            return ResultMsg.error("参数错误");
        }

        // 1. 检查是否存在子部门
        List<Department> all = departmentDao.selectAll();
        for (Department d : all) {
            if (id.equals(d.getParentId())) {
                return ResultMsg.error("该部门下存在子部门，禁止直接删除");
            }
        }

        // 2. 检查部门下是否存在关联员工
        List<Employee> employees = employeeDao.selectAll();
        for (Employee e : employees) {
            if (id.equals(e.getDeptId())) {
                return ResultMsg.error("该部门下存在在职员工（" + e.getName() + "），请先调岗或离职处理后重试");
            }
        }

        departmentDao.delete(id);
        return ResultMsg.success("删除部门成功");
    }
}
