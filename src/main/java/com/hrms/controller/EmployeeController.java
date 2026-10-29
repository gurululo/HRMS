package com.hrms.controller;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Employee;
import com.hrms.service.DepartmentService;
import com.hrms.service.EmployeeService;
import com.hrms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 员工管理控制器
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    /**
     * 员工列表与查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "empNo", required = false) String empNo,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "deptId", required = false) Long deptId,
                       Model model) {
        List<Employee> employeeList = employeeService.searchEmployees(empNo, name, deptId);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("empNo", empNo);
        model.addAttribute("name", name);
        model.addAttribute("deptId", deptId);
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        return "employee/list";
    }

    /**
     * 前往新增员工页面
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        model.addAttribute("positionList", positionService.getAllPositions());
        return "employee/form";
    }

    /**
     * 前往编辑员工页面
     */
    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    public String toEdit(@RequestParam("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            model.addAttribute("errorMsg", "未找到指定员工");
            return "redirect:/employee/list";
        }
        model.addAttribute("employee", employee);
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        model.addAttribute("positionList", positionService.getAllPositions());
        return "employee/form";
    }

    /**
     * 保存员工信息（新增或修改）
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Employee employee, Model model, RedirectAttributes redirectAttributes) {
        ResultMsg result;
        if (employee.getId() == null) {
            result = employeeService.addEmployee(employee);
        } else {
            result = employeeService.updateEmployee(employee);
        }

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMsg", result.getMsg());
            return "redirect:/employee/list";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            model.addAttribute("employee", employee);
            model.addAttribute("departmentList", departmentService.getAllDepartments());
            model.addAttribute("positionList", positionService.getAllPositions());
            return "employee/form";
        }
    }

    /**
     * 删除员工
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg deleteAjax(@RequestParam("id") Long id) {
        return employeeService.deleteEmployee(id);
    }
}
