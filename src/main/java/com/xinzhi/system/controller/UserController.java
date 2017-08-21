package com.xinzhi.system.controller;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.sevice.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

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
        return "change_psw";
    }

    @PostMapping(value = "/doChangePsw")
    public ModelAndView doChangePsw(@RequestBody Map<String, String> pswMap, BindingResult bindingResult) {

        logger.info("pswMap:" + pswMap.toString());
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:change_psw");
            return modelAndView;
        }
        modelAndView.setViewName("change_psw");
        return modelAndView;
    }
}
