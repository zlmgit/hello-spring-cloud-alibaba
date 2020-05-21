package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Tools {
    /**
     * File 转换成MultipartFile
     * @param file
     * @return
     * @throws IOException
     */
    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                "text/plain", fileInputStream);
        //org.apache.http.entity.ContentType#APPLICATION_OCTET_STREAM
        return multipartFile;
    }
}
