package com.hrms.service.impl;

import com.hrms.common.ResultMsg;
import com.hrms.dao.EmployeeDao;
import com.hrms.dao.PositionDao;
import com.hrms.entity.Employee;
import com.hrms.entity.Position;
import com.hrms.service.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位业务服务实现类
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Position> getAllPositions() {
        return positionDao.selectAll();
    }

    @Override
    public List<Position> searchPositions(String positionName) {
        List<Position> all = positionDao.selectAll();
        if (StringUtils.isBlank(positionName)) {
            return all;
        }
        String keyword = positionName.trim().toLowerCase();
        List<Position> filtered = new ArrayList<Position>();
        for (Position p : all) {
            if (p.getPositionName() != null && p.getPositionName().toLowerCase().contains(keyword)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    @Override
    public Position getPositionById(Long id) {
        return positionDao.selectById(id);
    }

    @Override
    public ResultMsg addPosition(Position position) {
        if (position == null || StringUtils.isBlank(position.getPositionName())) {
            return ResultMsg.error("岗位名称不能为空");
        }
        String name = position.getPositionName().trim();
        position.setPositionName(name);

        List<Position> all = positionDao.selectAll();
        for (Position p : all) {
            if (p.getPositionName().equalsIgnoreCase(name)) {
                return ResultMsg.error("已存在相同名称的岗位");
            }
        }

        positionDao.insert(position);
        return ResultMsg.success("添加岗位成功");
    }

    @Override
    public ResultMsg updatePosition(Position position) {
        if (position == null || position.getId() == null) {
            return ResultMsg.error("岗位参数错误");
        }
        if (StringUtils.isBlank(position.getPositionName())) {
            return ResultMsg.error("岗位名称不能为空");
        }
        String name = position.getPositionName().trim();
        position.setPositionName(name);

        Position existing = positionDao.selectById(position.getId());
        if (existing == null) {
            return ResultMsg.error("岗位不存在或已被删除");
        }

        List<Position> all = positionDao.selectAll();
        for (Position p : all) {
            if (!p.getId().equals(position.getId()) && p.getPositionName().equalsIgnoreCase(name)) {
                return ResultMsg.error("已存在同名岗位");
            }
        }

        positionDao.update(position);
        return ResultMsg.success("修改岗位成功");
    }

    @Override
    public ResultMsg deletePosition(Long id) {
        if (id == null) {
            return ResultMsg.error("参数错误");
        }

        // 检查岗位下是否有关联员工
        List<Employee> employees = employeeDao.selectAll();
        for (Employee e : employees) {
            if (id.equals(e.getPositionId())) {
                return ResultMsg.error("该岗位下存在在职员工（" + e.getName() + "），禁止直接删除");
            }
        }

        positionDao.delete(id);
        return ResultMsg.success("删除岗位成功");
    }
}
