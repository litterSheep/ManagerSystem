package com.xinzhi.system.config;

import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.sevice.UserInfoService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器，判断用户是通过记住我登录时，查询数据库后台自动登录，同时把用户放入session中
 * Created by ly on 2017/7/19 15:32.
 */
public class SessionInterceptor implements HandlerInterceptor {

    private final Logger logger = Logger.getLogger(SessionInterceptor.class);
    @Resource
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("---preHandle---");
        System.out.println(request.getContextPath());
        Subject currentUser = SecurityUtils.getSubject();
        //判断用户是通过记住我功能自动登录,此时session失效
        if (!currentUser.isAuthenticated() && currentUser.isRemembered()) {
            try {
                UserInfo user = userInfoService.findByUsername(currentUser.getPrincipals().toString());
                //对密码进行加密后验证
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), currentUser.isRemembered());
                //把当前用户放入session
                currentUser.login(token);
                Session session = currentUser.getSession();
                session.setAttribute("currentUser", user);
                //设置会话的过期时间--ms,默认是30分钟，设置负数表示永不过期
                session.setTimeout(-1000l);
            } catch (Exception e) {
                //自动登录失败,跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/system/login");
                return false;
            }
            if (!currentUser.isAuthenticated()) {
                //自动登录失败,跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/system/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("---postHandle---");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("---afterCompletion---");
    }

}
