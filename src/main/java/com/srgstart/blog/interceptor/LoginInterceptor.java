package com.srgstart.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author srgstart
 * @Create 2021/8/14 18:24
 * @Description 配置登录拦截器
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
    public static final String LOGIN_USER_ = "loginUser";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求路径
        String requestUrl = request.getRequestURI();
        if (requestUrl != null) {
            String ip = request.getRemoteAddr();
            log.info("======拦截的ip: {}, 拦截的请求路径{}",ip,requestUrl);
            // 登录检查逻辑
            // 放行
            if (request.getSession().getAttribute(LOGIN_USER_)
                    != null){
                return true;
            }
            // 拦截,跳转到登陆页面
            request.setAttribute("msg","请先登录");
            response.sendRedirect("/login");
            return false;
        }
        return false;
    }
}
