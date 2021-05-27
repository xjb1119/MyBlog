package com.xjb.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bo
 * @create 2021-04-15 17:10
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 全局异常处理
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {

        //日志输出到控制台
        log.info("Request URL : {} , Exception : {}", request.getRequestURL(), e);

        //当异常标识了状态码，则进行处理，直接交给springboot
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.addObject("exception", e);

        //去错误页面
        mav.setViewName("error/error");

        return mav;
    }
}
