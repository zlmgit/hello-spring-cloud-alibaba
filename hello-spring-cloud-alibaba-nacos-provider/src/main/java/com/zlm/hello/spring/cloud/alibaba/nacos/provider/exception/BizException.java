package com.zlm.hello.spring.cloud.alibaba.nacos.provider.exception;

/**
 * 通用业务异常
 *
 * @author qi.ren@paat.com
 * @since 2019/3/7
 */
public class BizException extends RuntimeException {

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
