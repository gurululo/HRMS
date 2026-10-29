package com.hrms.controller;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Department;
import com.hrms.service.DepartmentService;
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
 * 部门管理控制器
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 部门列表页面
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Department> list = departmentService.getAllDepartments();
        model.addAttribute("departmentList", list);
        return "department/list";
    }

    /**
     * 部门树形展示页面
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String tree(Model model) {
        List<Department> treeList = departmentService.getDepartmentTree();
        model.addAttribute("treeList", treeList);
        return "department/tree";
    }

    /**
     * 获取树形JSON数据
     */
    @RequestMapping(value = "/treeData", method = RequestMethod.GET)
    @ResponseBody
    public List<Department> treeData() {
        return departmentService.getDepartmentTree();
    }

    /**
     * 前往新增部门页面
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) {
        model.addAttribute("parentList", departmentService.getAllDepartments());
        model.addAttribute("department", new Department());
        return "department/form";
    }

    /**
     * 前往编辑部门页面
     */
    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    public String toEdit(@RequestParam("id") Long id, Model model) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept == null) {
            model.addAttribute("errorMsg", "未找到指定部门");
            return "redirect:/department/list";
        }
        model.addAttribute("department", dept);
        model.addAttribute("parentList", departmentService.getAllDepartments());
        return "department/form";
    }

    /**
     * 保存部门（新增或更新）
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Department department, Model model, RedirectAttributes redirectAttributes) {
        ResultMsg result;
        if (department.getId() == null) {
            result = departmentService.addDepartment(department);
        } else {
            result = departmentService.updateDepartment(department);
        }

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMsg", result.getMsg());
            return "redirect:/department/list";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            model.addAttribute("department", department);
            model.addAttribute("parentList", departmentService.getAllDepartments());
            return "department/form";
        }
    }

    /**
     * 删除部门（支持AJAX与普通提交）
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg deleteAjax(@RequestParam("id") Long id) {
        return departmentService.deleteDepartment(id);
    }
}
