package com.zlm.hello.spring.cloud.alibaba.nacos.provider.exception;

/**
 * @author：yafeng.cai
 * @since 2019/08/05
 */
public class IncludesAndExcludesConflictException extends RuntimeException {

  public IncludesAndExcludesConflictException(String msg) {
    super(msg);
  }
}
