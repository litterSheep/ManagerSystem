package com.xinzhi.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ly on 2017/7/1 16:34.
 */
@Controller
@RequestMapping("/system")
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/index")
    public ModelAndView index() {

        logger.info(">>>>>>>>>>>>>>index");
        return new ModelAndView("index");
    }


}
