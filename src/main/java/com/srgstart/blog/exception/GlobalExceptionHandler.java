package com.srgstart.blog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author srgstart
 * @Create 2021/8/13 9:15
 * @Description 全局捕获异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 获取logger对象
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Integer NOT_FOUND_ERROR = 404;

    /**
     * 默认异常处理
     * @param request :为了获取请求路径
     * @param e :exception异常对象
     * @return modelAndView
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("/error/error");
        return mv;
    }

    /**
     * 指定异常处理
     * @param request :为了获取请求路径
     * @param e : BlogException对象
     * @return modelAndView
     */
    @ExceptionHandler(BlogException.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, BlogException e) {
        ModelAndView mv = new ModelAndView();
        if (NOT_FOUND_ERROR.equals(e.getCode())) {
            logger.warn("Request URL: {}, Exception.message: {}",request.getRequestURL(),e.getMessage());
            mv.setViewName("/error/404");
        } else {
            logger.error("Request URL: {}, Exception: {}",request.getRequestURL(),e);
            mv.addObject("url",request.getRequestURL());
            mv.addObject("exception",e);
            mv.setViewName("/error/error");
        }

        return mv;
    }


}
