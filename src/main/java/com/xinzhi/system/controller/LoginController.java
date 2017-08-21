package com.xinzhi.system.controller;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.sevice.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by ly on 2017/7/7.
 */
@Controller
@RequestMapping("/system")
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Resource
    private UserInfoService userInfoService;


    @GetMapping(value = "/login")
    public ModelAndView loginForm() {
        ModelAndView model = new ModelAndView();
        UserInfo userInfo = null;
        //获取当前登录用户，这样登录成功点返回时不会进入到登录界面
        Subject currentUser = null;
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

    @PostMapping(value = "/index")
    public ModelAndView doLogin(ModelAndView modelAndView, @Valid UserInfo user, BindingResult bindingResult
            , RedirectAttributes redirectAttributes, boolean rememberMe
            , HttpServletRequest request, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:login");
            return modelAndView;
        }
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            logger.info("用户名或密码为空! ");
            redirectAttributes.addFlashAttribute("msg", "用户名或密码为空!");
            modelAndView.setViewName("redirect:login");
            modelAndView.addObject("username", user.getName());
            return modelAndView;
        }
        String userName = user.getUsername();
        //对密码进行加密后验证
        UsernamePasswordToken token = new UsernamePasswordToken(userName,
                user.getPassword(), rememberMe);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + userName + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + userName + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("msg", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("msg", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("msg", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数大于5次,账户已锁定");
            redirectAttributes.addFlashAttribute("msg", "用户名或密码错误次数大于5次,账户已锁定");
        } catch (DisabledAccountException sae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,帐号已经禁止登录");
            redirectAttributes.addFlashAttribute("msg", "帐号已经禁止登录");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + userName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //把当前用户放入session
            Session session = currentUser.getSession();
            UserInfo tUser = userInfoService.findByUsername(userName);
            session.setAttribute("user", tUser);
            modelAndView.addObject("user", tUser);
            modelAndView.setViewName("/index");
            return modelAndView;
        } else {
            token.clear();
            modelAndView.setViewName("redirect:login");
            modelAndView.addObject("username", user.getName());
            return modelAndView;
        }


//        // 登录失败从request中获取shiro处理的异常信息。
//        // shiroLoginFailure:就是shiro异常类的全类名.
//        String exception = (String) request.getAttribute("shiroLoginFailure");
//        logger.info("exception:" + exception);
//        String msg = "";
//        if (exception != null) {
//            if (UnknownAccountException.class.getName().equals(exception)) {
//                msg = "账号不存在";
//            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
//                msg = "密码不正确";
//            } else if (AuthenticationException.class.getName().equals(exception)) {
//                msg = "用户名或密码不正确";
//            } else if (LockedAccountException.class.getName().equals(exception)) {
//                msg = "账户已锁定";
//            } else if (DisabledAccountException.class.getName().equals(exception)) {
//                msg = "帐号禁止登录";
//            } else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
//                msg = "用户名或密码错误次数大于5次,账户已锁定";
//            } else {
//                msg = exception;
//            }
//            redirectAttributes.addFlashAttribute("msg", msg);
//        }
//        return "redirect:login";
    }

}
