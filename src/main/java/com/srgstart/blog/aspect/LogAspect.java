package com.srgstart.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author srgstart
 * @Create 2021/8/13 15:13
 * @Description 使用aop横切，实现记录调用controller方法、返回值的日志
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 声明logger对象
     */
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 在指定的包下，在调用方法之前触发
     */
    @Pointcut("execution(* com.srgstart.blog.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String url = request.getRequestURL().toString();
            String ip = request.getRemoteAddr();
            String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            RequestLogs requestLogs = new RequestLogs(url,ip,classMethod,args);
            logger.info("======RequestLogs : {}",requestLogs);
        }


    }

    @After("log()")
    public void doAfter() {}

    /**
     * 调用方法之后，记录返回值，returning中的值和定义的object的变量要一样
     * @param result: 记录返回值
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("======Result : {}",result);
    }


    /**
     * 自定义内部类，用于记录访问日志
     */
    @Data
    @AllArgsConstructor
    private static class RequestLogs {
        /**
         * 记录访问url
         */
        private String url;
        /**
         * 记录访问IP
         */
        private String iP;
        /**
         * 记录访问的类和方法
         */
        private String classMethod;
        /**
         * 记录访问的方法的参数列表
         */
        private Object[] args;
    }

}
