package com.hrms.controller;

import com.hrms.common.ResultMsg;
import com.hrms.entity.Position;
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
 * 岗位管理控制器
 */
@Controller
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    /**
     * 岗位列表与查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "positionName", required = false) String positionName, Model model) {
        List<Position> list = positionService.searchPositions(positionName);
        model.addAttribute("positionList", list);
        model.addAttribute("positionName", positionName);
        return "position/list";
    }

    /**
     * 前往新增岗位页面
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) {
        model.addAttribute("position", new Position());
        return "position/form";
    }

    /**
     * 前往编辑岗位页面
     */
    @RequestMapping(value = "/toEdit", method = RequestMethod.GET)
    public String toEdit(@RequestParam("id") Long id, Model model) {
        Position position = positionService.getPositionById(id);
        if (position == null) {
            model.addAttribute("errorMsg", "未找到指定岗位");
            return "redirect:/position/list";
        }
        model.addAttribute("position", position);
        return "position/form";
    }

    /**
     * 保存岗位（新增或更新）
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Position position, Model model, RedirectAttributes redirectAttributes) {
        ResultMsg result;
        if (position.getId() == null) {
            result = positionService.addPosition(position);
        } else {
            result = positionService.updatePosition(position);
        }

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMsg", result.getMsg());
            return "redirect:/position/list";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            model.addAttribute("position", position);
            return "position/form";
        }
    }

    /**
     * 删除岗位
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg deleteAjax(@RequestParam("id") Long id) {
        return positionService.deletePosition(id);
    }
}
