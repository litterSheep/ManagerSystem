package com.xinzhi.system.controller;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.sevice.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by ly on 2017/7/20 10:11.
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

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
}
