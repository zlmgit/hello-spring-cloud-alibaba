package com.zlm.hello.spring.cloud.alibaba.nacos.provider3;

import cn.hutool.core.io.FileUtil;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Attachment;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication3.class)
public class AttachmentServiceTest {

    @Autowired
    private AttachmentService attachmentService;

    @Test
    public void testInsert() throws IOException, SQLException {

        Attachment attachment = new Attachment();
        attachment.setName("hello");
        attachmentService.insertAttachment(attachment);
    }
    @Test
    public void select()  {
        final Attachment attachment = attachmentService.selectAttachmentById(1);
        System.out.println(attachment);
    }

    public static void main(String[] args) {

    }

}
