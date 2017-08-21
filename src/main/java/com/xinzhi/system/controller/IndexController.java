package com.xinzhi.system.controller;

import com.xinzhi.system.sevice.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
