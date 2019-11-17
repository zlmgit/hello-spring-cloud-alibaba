package com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLog {

    String value() default "";

    boolean printReturnArg() default false;

    /**
     * 业务模块名称
     *
     * @return
     */
    String module() default "";

    /**
     * 菜单
     *
     * @return
     */
    String menu() default "";

    /**
     * 操作名称
     *
     * @return
     */
    String action() default "";

    /**
     * 出现异常的时候返回消息
     * 此时会打印错误日志
     *
     * @return
     */
    String error() default "操作失败";
}

