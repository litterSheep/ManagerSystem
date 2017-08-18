package com.xinzhi.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ly on 2017/7/19 15:31.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 此方法把该拦截器实例化成一个bean,否则在拦截器里无法注入其它bean
     *
     * @return
     */
    @Bean
    SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    /**
     * 配置拦截器
     *
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("system/login",
                        "/error");
    }

}
