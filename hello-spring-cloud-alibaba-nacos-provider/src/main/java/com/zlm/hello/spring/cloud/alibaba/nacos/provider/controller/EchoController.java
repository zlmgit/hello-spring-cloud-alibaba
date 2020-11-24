package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kai
 */

@RestController
public class EchoController {

  private static final Logger log = LoggerFactory.getLogger(EchoController.class);

  @GetMapping("echoByKai/{dynamicService}")
  public String echo(@PathVariable String dynamicService) {
    return dynamicService;
  }
}
