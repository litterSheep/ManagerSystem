package com.xinzhi.system.controller;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.forms.ChangePswForm;
import com.xinzhi.system.sevice.UserInfoService;
import com.xinzhi.system.utils.PasswordEncrypt;
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
        String name = currentUser.getPrincipals().toString();
        logger.info("pswMap:" + pswForm.toString());

        modelAndView.setViewName("change_psw");
        if (bindingResult.hasErrors()) {
            if (pswForm != null) {
                if (pswForm.getOldPsw().isEmpty()) {
                    modelAndView.addObject("msg", "请输入原始密码");
                } else if (pswForm.getNewPsw().isEmpty()) {
                    modelAndView.addObject("msg", "请输入新密码");
                } else {
                    modelAndView.addObject("msg", "请再次输入新密码");
                }
            } else {
                modelAndView.addObject("msg", "出错了:" + bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
        } else {

            if (pswForm.getOldPsw().equals(pswForm.getNewPsw())) {
                modelAndView.addObject("msg", "新密码与旧密码一致");
                return modelAndView;
            }
            if (!pswForm.getNewPsw().equals(pswForm.getNewPswAgain())) {
                modelAndView.addObject("msg", "两次输入的新密码不一致");
                return modelAndView;
            }
            UserInfo userInfo = userInfoService.findByUsername(name);
            if (userInfo != null) {
                //验证旧密码是否正确
                if (PasswordEncrypt.isPasswordEquals(userInfo, pswForm.getOldPsw())) {
                    userInfo.setPassword(pswForm.getNewPsw());
                    userInfoService.addUser(userInfo);
                    modelAndView.addObject("username", name);
                    SecurityUtils.getSubject().logout();
                    modelAndView.setViewName("login");
                } else {
                    modelAndView.addObject("msg", "原始密码错误");
                }
            }
        }
        return modelAndView;
    }
}
