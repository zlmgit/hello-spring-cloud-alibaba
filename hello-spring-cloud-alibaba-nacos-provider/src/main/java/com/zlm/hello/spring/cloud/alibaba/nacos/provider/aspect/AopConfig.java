package com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

//@Order(0)
//@Aspect
//@Component
public class AopConfig {

    @Pointcut("@annotation(actionLog)")
    public  void ActionLogPointcut(ActionLog actionLog){};

    private static final String LOG_START_TEMPLATE = "[*在操作*：%s]->[*服务开始*]->[*请求参数*]->%s";

    private static final String LOG_END_TEMPLATE_1 = "[*在操作*：%s]->[*服务结束*]";

    private static final String LOG_END_TEMPLATE_2 = "[*在操作*：%s]->[*服务结束*]->[*返回结果*]->%s";


    @Before("ActionLogPointcut(actionLog)")
    public void doBefore(JoinPoint joinPoint,ActionLog actionLog) {
        Map<String, Object> requestParams = getRequestParams(joinPoint);
         LoggerFactory.getLogger(getThisSourceClass(joinPoint.getThis()))
                .info(
                        String.format(
                                LOG_START_TEMPLATE,
                                Strings.nullToEmpty(actionLog.value()),
                                CollectionUtils.isEmpty(requestParams)
                                        ? ""
                                        : JSON.toJSONString(requestParams, SerializerFeature.WriteMapNullValue)));
    }

    @After("ActionLogPointcut(actionLog)")
    public void doAfter(JoinPoint joinPoint, ActionLog actionLog) {
        if (actionLog.printReturnArg()) {
            return;
        }
        LoggerFactory.getLogger(getThisSourceClass(joinPoint.getThis()))
                .info(String.format(LOG_END_TEMPLATE_1, Strings.nullToEmpty(actionLog.value())));
    }

    @AfterReturning(value = "ActionLogPointcut(actionLog)", returning = "returnObj")
    public void doAfterReturning(JoinPoint joinPoint, ActionLog actionLog, Object returnObj) {
        if (!actionLog.printReturnArg()) {
            return;
        }
        LoggerFactory.getLogger(getThisSourceClass(joinPoint.getThis()))
                .info(
                        String.format(
                                LOG_END_TEMPLATE_2,
                                Strings.nullToEmpty(actionLog.value()),
                                returnObj == null
                                        ? returnObj
                                        : JSON.toJSONString(returnObj, SerializerFeature.WriteMapNullValue)));
    }

    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Object[] methodArgs = joinPoint.getArgs();
        if (methodArgs == null || methodArgs.length == 0) {
            return null;
        }
        Map<String, Object> requestParams = Maps.newTreeMap();
        int index = 0;
        for (Object methodArg : methodArgs) {
            if (methodArg instanceof HttpServletRequest || methodArg instanceof HttpServletResponse) {
                index++;
                continue;
            }
            requestParams.put(
                    getMethodTypeName(
                            ((MethodSignature) joinPoint.getSignature()).getMethod(), methodArgs.length, index++),
                    methodArg);
        }
        return requestParams;
    }

    private String getMethodTypeName(Method method, int argsLen, int index) {
        Parameter[] parameters = method.getParameters();
        if (argsLen != parameters.length) {
            return "";
        }
        return String.format(
                "(%s)%s", parameters[index].getType().getName(), parameters[index].getName());
    }

    private String getThisSourceClass(Object thisObj) {
        int index = thisObj.getClass().getName().indexOf("$");
        return index > -1 ? thisObj.getClass().getName().substring(0, index) : "";
    }

}
