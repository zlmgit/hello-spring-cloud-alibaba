package com.zlm.hello.spring.cloud.alibaba.nacos.provider.log;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.exception.BizException;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.exception.TipException;
import io.netty.util.internal.StringUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
public class WebRequestLogProcess {

    private static final String SPLIT_STRING_M   = "=";

    private static final String SPLIT_STRING_DOT = ", ";

    protected final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    @Setter
    private String userId;

    @Setter
    private String method;

    @Setter
    private String ipAddr;

    @Setter
    private String body;

    @Setter
    private String urlParams;

    @Setter
    private String requestURI;

    /**
     * 最大输出 20w 响应
     */
    private static final int MAX_RESPONSE_BODY_LOG = 20_0000;

    /**
     * Opertion object.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @param actionLog           the action log
     * @return the object
     */
    public Object opertion(ProceedingJoinPoint proceedingJoinPoint, ActionLog actionLog) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            // 打印请求 url
            log.debug("操作人 USER-ID：{}", userId);
            log.debug("请求 URI: {}\t{}", method, requestURI);
            log.debug("访问者 IP: {}", ipAddr);
            log.debug("操作轨迹: module[{}]-menu[{}]-action[{}]", actionLog.module(), actionLog.menu(), actionLog.action());

            if (StringUtils.isNotEmpty(urlParams)) {
                log.debug("请求参数: {}", urlParams);
            }
            if (StringUtils.isNotEmpty(body)) {
                log.debug("请求主体: \n{}", body);
            }

            stopwatch = Stopwatch.createStarted();
            Object result = proceedingJoinPoint.proceed();
            this.printTimes(stopwatch, result);
            return result;
        } catch (TipException e) {
            return this.tipExceptionHandle(stopwatch, e.getMessage());
        } catch (ConstraintViolationException e) {
            return this.violationExceptionHandle(stopwatch, e);
        } catch (BizException e) {
            return this.bizExceptionHandle(actionLog, stopwatch, e);
        } catch (Throwable e) {
            log.error("【{}-{}】 发生异常: ", actionLog.module(), actionLog.action(), e);
            String error = actionLog.error();
            if (StringUtil.isNullOrEmpty(error)) {
                error = actionLog.action() + "失败";
            }
            return this.tipExceptionHandle(stopwatch, error);
        }
    }

    private Object bizExceptionHandle(ActionLog actionLog, Stopwatch stopwatch, BizException e) {
        if (e.getCause() != null) {
            log.error("【{}-{}】 发生异常: ", actionLog.module(), actionLog.action(), e);
        }
        return this.tipExceptionHandle(stopwatch, e.getMessage());
    }

    private Object violationExceptionHandle(Stopwatch stopwatch, ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation  = violations.iterator().next();
        return this.tipExceptionHandle(stopwatch, violation.getMessage());
    }

    private Object tipExceptionHandle(Stopwatch stopwatch, String message) {
        //TODO message用返回标准封装BaseResponse<String> response = Response.fail(message).build();
        this.printTimes(stopwatch, message);
        return message;
    }


    private void printTimes(Stopwatch stopwatch, Object result) {
        log.debug("响应结果: {}", getMaxBody(JSON.toJSONString(result)));
        log.debug("执行耗时: {}ms\n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    protected static String getMaxBody(String value) {
        if (null == value || value.length() < MAX_RESPONSE_BODY_LOG) {
            return value;
        }
        log.warn("请求体过大，长度为: {}", value.length());
        return StringUtils.substring(value, MAX_RESPONSE_BODY_LOG);
    }

    /**
     * 获取请求地址上的参数
     *
     * @param request
     * @return
     */
    protected String getRequestParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> enu = request.getParameterNames();
        //获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            sb.append(name).append(SPLIT_STRING_M).append(request.getParameter(name));
            if (enu.hasMoreElements()) {
                sb.append(SPLIT_STRING_DOT);
            }
        }
        return sb.toString();
    }
}
