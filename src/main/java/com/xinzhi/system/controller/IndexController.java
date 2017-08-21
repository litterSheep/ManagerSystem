package com.xinzhi.system.controller;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.sevice.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by ly on 2017/7/1 16:34.
 */
@Controller
@RequestMapping("/system")
public class IndexController {

    @Resource
    private UserInfoService userInfoService;
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("")
    public ModelAndView index() {

        ModelAndView model = new ModelAndView();
        UserInfo userInfo = null;
        //获取当前登录用户，这样登录成功点返回时不会进入到登录界面
        Subject currentUser = SecurityUtils.getSubject();
        try {
            userInfo = userInfoService.findByUsername(currentUser.getPrincipals().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userInfo == null) {
            userInfo = new UserInfo();
            model.setViewName("login");
        } else {
            model.setViewName("index");
        }
        model.addObject("user", userInfo);

        return model;
    }

    @GetMapping(value = "/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("msg", "您已安全退出");
        return "redirect:login";
    }

    @GetMapping("/403")
    public String unauthorizedRole() {
        return "403";
    }
}
