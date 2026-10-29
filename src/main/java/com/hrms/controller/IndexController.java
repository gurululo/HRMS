package com.hrms.controller;

import com.hrms.service.ContractService;
import com.hrms.service.DepartmentService;
import com.hrms.service.EmployeeService;
import com.hrms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 系统主页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        int employeeCount = employeeService.getAllEmployees().size();
        int departmentCount = departmentService.getAllDepartments().size();
        int positionCount = positionService.getAllPositions().size();
        int contractCount = contractService.getAllContracts().size();

        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("departmentCount", departmentCount);
        model.addAttribute("positionCount", positionCount);
        model.addAttribute("contractCount", contractCount);

        return "index";
    }
}
