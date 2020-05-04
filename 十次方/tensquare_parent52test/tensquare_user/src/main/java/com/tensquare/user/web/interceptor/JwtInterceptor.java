package com.tensquare.user.web.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    //注入JwtUtil
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*获取头信息，约定头信息key为Authorization*/
        final String authorizationHeader = request.getHeader("JwtAuthorization");
        /*判断获取出来的请求头信息是否合法*/
        if (null != authorizationHeader && authorizationHeader.startsWith("Bearer ")) {
            /* 获取令牌，The part after "Bearer "*/
            final String token = authorizationHeader.substring(7);
            /*获取载荷 从中获取用户信息*/
            Claims claims = null;
            try {
                claims = jwtUtil.parseJWT(token);
            } catch (Exception e) {
                System.out.println("异常信息");
                e.printStackTrace();
            }
            if (null != claims) {
                /*判断令牌中的自定义载荷中的角色是否是admin（管理员）*/
                if ("admin".equals(claims.get("roles"))) {
                    System.out.println("管理员登录");
                    request.setAttribute("admin_claims", claims);
                }
                /*判断令牌中的自定义载荷中的角色是否是user（普通用户）*/
                if ("user".equals(claims.get("roles"))) {
                    System.out.println("普通用户登录");
                    request.setAttribute("user_claims", claims);
                }
            }
        }
        return true;
    }
}
