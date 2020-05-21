package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.controller;


import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Mail;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.service.MailService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


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
        File mailFile = new File(path + "file" +File.separator + "43.pdf");
        m.setMultipartFiles(new MultipartFile[]{Tools.fileToMultipartFile(mailFile)});
        mailService.sendMail(m);
        return "oK";
    }




}
