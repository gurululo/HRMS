package com.hrms.service.impl;

import com.hrms.common.ResultMsg;
import com.hrms.dao.ContractDao;
import com.hrms.dao.EmployeeDao;
import com.hrms.entity.Contract;
import com.hrms.entity.Employee;
import com.hrms.service.ContractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同业务服务实现类
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private EmployeeDao employeeDao;

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 补充合同关联员工姓名
     */
    private void enrichContractInfo(List<Contract> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Employee> employeeList = employeeDao.selectAll();
        Map<String, String> empMap = new HashMap<String, String>();
        for (Employee e : employeeList) {
            empMap.put(e.getEmpNo(), e.getName());
        }

        for (Contract c : list) {
            if (c.getEmpNo() != null && empMap.containsKey(c.getEmpNo())) {
                c.setEmployeeName(empMap.get(c.getEmpNo()));
            } else {
                c.setEmployeeName("未知员工");
            }
        }
    }

    @Override
    public List<Contract> getAllContracts() {
        List<Contract> list = contractDao.selectAll();
        enrichContractInfo(list);
        return list;
    }

    @Override
    public List<Contract> searchContracts(String keyword) {
        List<Contract> all = contractDao.selectAll();
        enrichContractInfo(all);

        if (StringUtils.isBlank(keyword)) {
            return all;
        }

        String kw = keyword.trim().toLowerCase();
        List<Contract> filtered = new ArrayList<Contract>();
        for (Contract c : all) {
            boolean match = false;
            if (c.getEmpNo() != null && c.getEmpNo().toLowerCase().contains(kw)) {
                match = true;
            }
            if (!match && c.getEmployeeName() != null && c.getEmployeeName().toLowerCase().contains(kw)) {
                match = true;
            }
            if (match) {
                filtered.add(c);
            }
        }
        return filtered;
    }

    @Override
    public Contract getContractById(Long id) {
        Contract c = contractDao.selectById(id);
        if (c != null) {
            List<Contract> list = new ArrayList<Contract>();
            list.add(c);
            enrichContractInfo(list);
        }
        return c;
    }

    private ResultMsg validateContract(Contract contract) {
        if (contract == null) {
            return ResultMsg.error("合同信息不能为空");
        }
        if (StringUtils.isBlank(contract.getEmpNo())) {
            return ResultMsg.error("关联员工编号不能为空");
        }
        if (StringUtils.isBlank(contract.getStartDate())) {
            return ResultMsg.error("合同开始日期不能为空");
        }
        if (StringUtils.isBlank(contract.getEndDate())) {
            return ResultMsg.error("合同结束日期不能为空");
        }

        contract.setEmpNo(contract.getEmpNo().trim());
        contract.setStartDate(contract.getStartDate().trim());
        contract.setEndDate(contract.getEndDate().trim());

        // 验证员工是否存在
        Employee employee = employeeDao.selectByEmpNo(contract.getEmpNo());
        if (employee == null) {
            return ResultMsg.error("未找到编号为 [" + contract.getEmpNo() + "] 的员工记录");
        }

        // 验证日期有效性
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        sdf.setLenient(false);
        try {
            Date start = sdf.parse(contract.getStartDate());
            Date end = sdf.parse(contract.getEndDate());
            if (start.after(end)) {
                return ResultMsg.error("合同开始日期不能晚于结束日期");
            }
        } catch (ParseException e) {
            return ResultMsg.error("日期格式不合法，应为 yyyy-MM-dd");
        }

        return ResultMsg.success();
    }

    @Override
    public ResultMsg addContract(Contract contract) {
        ResultMsg validation = validateContract(contract);
        if (!validation.isSuccess()) {
            return validation;
        }

        contractDao.insert(contract);
        return ResultMsg.success("添加合同成功");
    }

    @Override
    public ResultMsg updateContract(Contract contract) {
        if (contract == null || contract.getId() == null) {
            return ResultMsg.error("合同参数错误");
        }
        Contract existing = contractDao.selectById(contract.getId());
        if (existing == null) {
            return ResultMsg.error("合同记录不存在或已被删除");
        }

        ResultMsg validation = validateContract(contract);
        if (!validation.isSuccess()) {
            return validation;
        }

        contractDao.update(contract);
        return ResultMsg.success("修改合同成功");
    }

    @Override
    public ResultMsg deleteContract(Long id) {
        if (id == null) {
            return ResultMsg.error("参数错误");
        }
        contractDao.delete(id);
        return ResultMsg.success("删除合同成功");
    }
}
