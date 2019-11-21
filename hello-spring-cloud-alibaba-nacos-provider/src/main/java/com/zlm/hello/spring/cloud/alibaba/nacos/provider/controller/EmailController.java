package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import io.swagger.annotations.Api;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "邮件服务")
public class EmailController {

    @PostMapping(value = "/sendMail")
    public void sendSimpleMail() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("1552005882@qq.com", "yhvdrdpsdubpbaae"));
        email.setSSLOnConnect(true);
        email.setFrom("1552005882@qq.com");
        email.setSubject("测试邮件主题");
        email.setMsg("测试邮件内容");
        email.addTo("13477407066@163.com");
        email.send();
    }
}
