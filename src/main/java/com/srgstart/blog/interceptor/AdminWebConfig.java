package com.srgstart.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author srgstart
 * @Create 2021/8/14 19:21
 * @Description TODO
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // 拦截的资源
                .addPathPatterns("/user/**","/blog/**","/comment/**")
                // 放行的url
                .excludePathPatterns("/user/userLogin","/user/userRegister","/blog/getAllBlog/**","/blog/searchBlog");
    }
}
