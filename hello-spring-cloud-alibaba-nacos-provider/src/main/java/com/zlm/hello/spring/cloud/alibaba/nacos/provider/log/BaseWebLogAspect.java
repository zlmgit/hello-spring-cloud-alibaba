package com.zlm.hello.spring.cloud.alibaba.nacos.provider.log;


import com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志切面拦截
 *
 * @author Lister
 * @since 2019/6/6 15:59.
 */
@Slf4j
@Order(0)
@Aspect
@Component
@NoArgsConstructor
public class BaseWebLogAspect extends WebRequestLogProcess {

    @Autowired(required = false)
    WebLogHandler webLogHandler;

    /*@Pointcut("@annotation(com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog)")
    public void webLog() {
    }*/

    @Pointcut("@annotation(actionLog)")
    public  void ActionLogPointcut(ActionLog actionLog){};

    /**
     * 所有有 @ActionLog 注解的请求拦截并打印相关日志
     * @param proceedingJoinPoint
     * @param actionLog
     * @return
     */
    @Around("ActionLogPointcut(actionLog)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, ActionLog actionLog) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        if (null != webLogHandler) {
            webLogHandler.preHandle(request, proceedingJoinPoint);
            webLogHandler.handle(request, proceedingJoinPoint);
        }
        super.setBody((String) request.getAttribute(JSON_REQUEST_BODY));
        //super.setIpAddr(IpHelper.getIpAddr(request));
        super.setMethod(request.getMethod());
        super.setRequestURI(request.getRequestURI());
        super.setUrlParams(super.getRequestParams(request));
        //SSOToken st = SSOHelper.getSSOToken(request);
        /*if (st != null) {
            super.setUserId(st.getId());
        }*/
        Object object = opertion(proceedingJoinPoint,actionLog);
        if (null != webLogHandler) {
            webLogHandler.afterHandle(request, proceedingJoinPoint);
        }
        return object;
    }

}
