package com.zlm.hello.spring.cloud.alibaba.nacos.provider.log;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * 扩展 Web 请求日志输出
 *
 * @author qi.ren@paat.com
 * @since 2019/5/11
 */
public interface WebLogHandler {

    default void preHandle(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
    }

    void handle(HttpServletRequest request, ProceedingJoinPoint joinPoint);

    default void afterHandle(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
    }

}
