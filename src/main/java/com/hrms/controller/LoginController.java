package com.hrms.controller;

import com.hrms.common.ResultMsg;
import com.hrms.interceptor.LoginInterceptor;
import com.hrms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员登录与退出控制器
 */
@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpSession session) {
        if (session != null && session.getAttribute(LoginInterceptor.SESSION_USER_KEY) != null) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * 执行登录
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          Model model) {
        ResultMsg result = adminService.login(username, password);
        if (result.isSuccess()) {
            HttpSession session = request.getSession(true);
            session.setAttribute(LoginInterceptor.SESSION_USER_KEY, result.getData());
            return "redirect:/index";
        } else {
            model.addAttribute("errorMsg", result.getMsg());
            model.addAttribute("username", username);
            return "login";
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute(LoginInterceptor.SESSION_USER_KEY);
            session.invalidate();
        }
        return "redirect:/login";
    }
}
