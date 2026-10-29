package com.hrms.controller;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Contract;
import com.hrms.service.ContractService;
import com.hrms.service.EmployeeService;
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
 * 合同管理控制器
 */
@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 合同列表与查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Contract> contractList = contractService.searchContracts(keyword);
        model.addAttribute("contractList", contractList);
        model.addAttribute("keyword", keyword);
        return "contract/list";
    }

    /**
     * 前往新增合同页面
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) {
        model.addAttribute("contract", new Contract());
        model.addAttribute("employeeList", employeeService.getAllEmployees());
        return "contract/form";
    }

    /**
     * 前往编辑合同页面
     */
    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    public String toEdit(@RequestParam("id") Long id, Model model) {
        Contract contract = contractService.getContractById(id);
        if (contract == null) {
            model.addAttribute("errorMsg", "未找到指定合同记录");
            return "redirect:/contract/list";
        }
        model.addAttribute("contract", contract);
        model.addAttribute("employeeList", employeeService.getAllEmployees());
        return "contract/form";
    }

    /**
     * 保存合同（新增或修改）
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Contract contract, Model model, RedirectAttributes redirectAttributes) {
        ResultMsg result;
        if (contract.getId() == null) {
            result = contractService.addContract(contract);
        } else {
            result = contractService.updateContract(contract);
        }

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMsg", result.getMsg());
            return "redirect:/contract/list";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            model.addAttribute("contract", contract);
            model.addAttribute("employeeList", employeeService.getAllEmployees());
            return "contract/form";
        }
    }

    /**
     * 删除合同
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg deleteAjax(@RequestParam("id") Long id) {
        return contractService.deleteContract(id);
    }
}
