package com.hrms.service.impl;

import com.hrms.common.ResultMsg;
import com.hrms.dao.ContractDao;
import com.hrms.dao.DepartmentDao;
import com.hrms.dao.EmployeeDao;
import com.hrms.dao.PositionDao;
import com.hrms.entity.Contract;
import com.hrms.entity.Department;
import com.hrms.entity.Employee;
import com.hrms.entity.Position;
import com.hrms.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工业务服务实现类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private ContractDao contractDao;

    /**
     * 补充关联部门与岗位名称
     */
    private void enrichEmployeeInfo(List<Employee> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Department> deptList = departmentDao.selectAll();
        Map<Long, String> deptMap = new HashMap<Long, String>();
        for (Department d : deptList) {
            deptMap.put(d.getId(), d.getDeptName());
        }

        List<Position> posList = positionDao.selectAll();
        Map<Long, String> posMap = new HashMap<Long, String>();
        for (Position p : posList) {
            posMap.put(p.getId(), p.getPositionName());
        }

        for (Employee e : list) {
            if (e.getDeptId() != null && deptMap.containsKey(e.getDeptId())) {
                e.setDeptName(deptMap.get(e.getDeptId()));
            } else {
                e.setDeptName("未分配");
            }

            if (e.getPositionId() != null && posMap.containsKey(e.getPositionId())) {
                e.setPositionName(posMap.get(e.getPositionId()));
            } else {
                e.setPositionName("未分配");
            }
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> list = employeeDao.selectAll();
        enrichEmployeeInfo(list);
        return list;
    }

    @Override
    public List<Employee> searchEmployees(String empNo, String name, Long deptId) {
        List<Employee> all = employeeDao.selectAll();
        enrichEmployeeInfo(all);

        // 维护调整：过滤首尾空格并支持工号不区分大小写检索
        String trimmedEmpNo = StringUtils.trimToNull(empNo);
        String trimmedName = StringUtils.trimToNull(name);

        List<Employee> filtered = new ArrayList<Employee>();
        for (Employee e : all) {
            boolean match = true;
            if (trimmedEmpNo != null) {
                if (e.getEmpNo() == null || !e.getEmpNo().toLowerCase().contains(trimmedEmpNo.toLowerCase())) {
                    match = false;
                }
            }
            if (match && trimmedName != null) {
                if (e.getName() == null || !e.getName().contains(trimmedName)) {
                    match = false;
                }
            }
            if (match && deptId != null && deptId > 0) {
                if (e.getDeptId() == null || !deptId.equals(e.getDeptId())) {
                    match = false;
                }
            }
            if (match) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Employee e = employeeDao.selectById(id);
        if (e != null) {
            List<Employee> temp = new ArrayList<Employee>();
            temp.add(e);
            enrichEmployeeInfo(temp);
        }
        return e;
    }

    @Override
    public Employee getEmployeeByEmpNo(String empNo) {
        Employee e = employeeDao.selectByEmpNo(empNo);
        if (e != null) {
            List<Employee> temp = new ArrayList<Employee>();
            temp.add(e);
            enrichEmployeeInfo(temp);
        }
        return e;
    }

    @Override
    public ResultMsg addEmployee(Employee employee) {
        if (employee == null) {
            return ResultMsg.error("员工信息不能为空");
        }
        if (StringUtils.isBlank(employee.getEmpNo())) {
            return ResultMsg.error("员工编号不能为空");
        }
        if (StringUtils.isBlank(employee.getName())) {
            return ResultMsg.error("员工姓名不能为空");
        }
        employee.setEmpNo(employee.getEmpNo().trim());
        employee.setName(employee.getName().trim());

        // 校验员工编号是否唯一
        Employee existing = employeeDao.selectByEmpNo(employee.getEmpNo());
        if (existing != null) {
            return ResultMsg.error("员工编号已存在: " + employee.getEmpNo());
        }

        // 校验部门和岗位是否存在
        if (employee.getDeptId() != null && employee.getDeptId() > 0) {
            Department dept = departmentDao.selectById(employee.getDeptId());
            if (dept == null) {
                return ResultMsg.error("所选部门不存在");
            }
        }
        if (employee.getPositionId() != null && employee.getPositionId() > 0) {
            Position pos = positionDao.selectById(employee.getPositionId());
            if (pos == null) {
                return ResultMsg.error("所选岗位不存在");
            }
        }

        employeeDao.insert(employee);
        return ResultMsg.success("添加员工成功");
    }

    @Override
    public ResultMsg updateEmployee(Employee employee) {
        if (employee == null || employee.getId() == null) {
            return ResultMsg.error("员工参数错误");
        }
        if (StringUtils.isBlank(employee.getEmpNo())) {
            return ResultMsg.error("员工编号不能为空");
        }
        if (StringUtils.isBlank(employee.getName())) {
            return ResultMsg.error("员工姓名不能为空");
        }
        employee.setEmpNo(employee.getEmpNo().trim());
        employee.setName(employee.getName().trim());

        Employee current = employeeDao.selectById(employee.getId());
        if (current == null) {
            return ResultMsg.error("员工不存在或已被删除");
        }

        // 校验员工编号是否冲突
        Employee checkNo = employeeDao.selectByEmpNo(employee.getEmpNo());
        if (checkNo != null && !checkNo.getId().equals(employee.getId())) {
            return ResultMsg.error("员工编号与其他员工冲突");
        }

        // 部门与岗位有效性
        if (employee.getDeptId() != null && employee.getDeptId() > 0) {
            Department dept = departmentDao.selectById(employee.getDeptId());
            if (dept == null) {
                return ResultMsg.error("所选部门不存在");
            }
        }
        if (employee.getPositionId() != null && employee.getPositionId() > 0) {
            Position pos = positionDao.selectById(employee.getPositionId());
            if (pos == null) {
                return ResultMsg.error("所选岗位不存在");
            }
        }

        employeeDao.update(employee);
        return ResultMsg.success("修改员工成功");
    }

    @Override
    public ResultMsg deleteEmployee(Long id) {
        if (id == null) {
            return ResultMsg.error("参数错误");
        }
        Employee e = employeeDao.selectById(id);
        if (e == null) {
            return ResultMsg.error("未找到对应员工记录");
        }

        // 检查该员工是否有关联合同记录
        List<Contract> contracts = contractDao.selectByEmpNo(e.getEmpNo());
        if (contracts != null && !contracts.isEmpty()) {
            return ResultMsg.error("该员工名下存在 " + contracts.size() + " 份劳动合同，请先清理或归档合同后删除员工");
        }

        employeeDao.delete(id);
        return ResultMsg.success("删除员工成功");
    }
}
