package com.zlm.hello.spring.cloud.alibaba.nacos.consumer.util;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

//@Component
public class ExceptionUtil {

  public static ClientHttpResponse handleException(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution, BlockException exception) {

    return new SentinelClientHttpResponse("由于限流，不能调用底层服务");
  }

  public static ClientHttpResponse handleFallBack(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution, BlockException exception) {

    return new SentinelClientHttpResponse("由于降级，不能调用底层服务");
  }
}