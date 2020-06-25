package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
@RestController
@RequestMapping("image")
public class ImageController {

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("file1") MultipartFile file1,
                         @RequestParam("other")String other,
                         HttpServletRequest req)
            throws IllegalStateException, IOException {
        System.out.println(other);
        // 判断文件是否为空，空则返回失败页面
        if (file.isEmpty()) {
            return "failed";
        }
        // 获取文件存储路径（绝对路径）
        String path = req.getServletContext().getRealPath("/WEB-INF/file");
        // 获取原文件名
        String fileName = file.getOriginalFilename();
        String fileName1 = file1.getOriginalFilename();
        // 创建文件实例
        File filePath = new File(path, fileName);
        File filePath1 = new File(path, fileName1);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
        file1.transferTo(filePath1);
        return "success";
    }
}
