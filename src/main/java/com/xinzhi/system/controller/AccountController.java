package com.xinzhi.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ly on 2017/8/18 17:33.
 */
@Controller
@RequestMapping("/system/index")
public class AccountController {

    @RequestMapping("/account")
    public String intoPageAccount(){
        return "account_info";
    }

}
