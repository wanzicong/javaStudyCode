package com.tensquare.user.config;

import com.tensquare.user.web.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    /*注入JwtInterceptor*/
    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * WebMvc中添加拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                /*添加需要拦截器拦截的资源*/
                .addPathPatterns("/**")
                /*排除不需要拦截的资源,如登录*/
                .excludePathPatterns("/**/login");
    }

}
