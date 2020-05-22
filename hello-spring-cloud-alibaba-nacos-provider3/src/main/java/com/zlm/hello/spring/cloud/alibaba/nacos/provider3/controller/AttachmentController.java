package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.controller;


import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Attachment;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Mail;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.service.AttachmentService;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


@RestController
@Slf4j
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private MailService mailService;
    @Autowired
    private AttachmentService attachmentService;


    @GetMapping("/insert")
    public String insertAttachment() throws IOException, SQLException {
        Attachment attachment = new Attachment();
        File file = new File(ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "templates/file" +File.separator + "43.pdf");
        final MultipartFile multipartFile = Tools.fileToMultipartFile(file);
        attachment.setFile(multipartFile.getBytes());
        attachment.setName(file.getName());
        attachmentService.insertAttachment(attachment);
        return "ok";
    }
    @GetMapping("/get/{id}")
    public String insertAttachment(@PathVariable("id") Integer id) throws IOException {
        Attachment attachment = attachmentService.selectAttachmentById(id);
        byte[] result = (byte[])attachment.getFile();
        Mail m = new Mail();
        m.setTo("1552005882@qq.com");
        m.setSubject("邮件发送主题测试");
        m.setText("测试内容哈哈哈哈");
        m.setMultipartFiles(new MultipartFile[]{new MockMultipartFile(attachment.getName(),attachment.getName(),
                "text/plain", new ByteArrayInputStream(result))});
        mailService.sendMail(m);
        return "ok";
    }
}
