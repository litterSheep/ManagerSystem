package com.xinzhi.system.controller;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.forms.ChangePswForm;
import com.xinzhi.system.sevice.UserInfoService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by ly on 2017/7/20 10:11.
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Resource
    private UserInfoService userInfoService;

    @GetMapping
    public ModelAndView addUserForm() {
        return new ModelAndView("");
    }

    @GetMapping("/addUser")
    public String addUser(UserInfo userInfo) {

        userInfo.setUsername("ly");
        userInfo.setPassword("123456");
        userInfoService.addUser(userInfo);

        userInfo.setUsername("admin");
        userInfo.setPassword("123456");

        userInfoService.addUser(userInfo);

        return "login";
    }

    @GetMapping(value = "/changePsw")
    public String introPageChangePsw() {
        Subject currentUser = SecurityUtils.getSubject();
        return "change_psw";
    }

    @PostMapping(value = "/doChangePsw")
    public ModelAndView doChangePsw(ModelAndView modelAndView, @Valid ChangePswForm pswForm, BindingResult bindingResult) {
        Subject currentUser = SecurityUtils.getSubject();
        logger.info("pswMap:" + pswForm.toString());
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("msg", "出错了:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            modelAndView.addObject("msg", "操作成功");
        }
        modelAndView.setViewName("change_psw");
        return modelAndView;
    }
}
