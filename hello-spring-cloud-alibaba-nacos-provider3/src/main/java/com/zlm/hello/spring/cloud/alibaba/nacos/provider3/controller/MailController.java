package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.controller;


import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Mail;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.service.MailService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send/{mail}")
    public String sendMail(@PathVariable("mail") String mail) throws IOException {
        Mail m = new Mail();
        m.setTo(mail);
        m.setSubject("邮件发送主题测试");
        m.setText("测试内容哈哈哈哈");
        String path = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        File mailFile = new File(path + "templates/file" +File.separator + "43.pdf");
        m.setMultipartFiles(new MultipartFile[]{Tools.fileToMultipartFile(mailFile)});
        mailService.sendMail(m);
        return "oK";
    }

    @GetMapping("/templateMail/{to}")
    public String mail(@PathVariable("to") String to){
        Mail mail = new Mail();
        mail.setSubject("这是模板主题");
        mail.setTo(to);
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("username","我变大了");
            mail.setAttachment(map);
            mailService.sendTemplateMail(mail);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Ok";
    }



}
