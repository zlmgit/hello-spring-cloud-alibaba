package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.exception.BizException;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.redis.RedisService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
@Api(tags = "图片验证码")
public class ImageController {

    private static Logger log = LoggerFactory.getLogger(ImageController.class);

    /* 注入Kaptcha */
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/code")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response){
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        /**
         * 生成验证码字符串并保存到redis中,保存60秒
         */
        String createText = defaultKaptcha.createText();
        redisService.put("KAPTCHA_CACHE_KEY", createText, 60);
        //HttpSession session = request.getSession();
        //session.setAttribute(KAPTCHA_SESSION_KEY, createText);
        /**
         * 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
         */
        BufferedImage challenge = defaultKaptcha.createImage(createText);
        try {
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (IOException e) {
            log.error("生成图形验证码失败",e);
            throw new BizException("生成图形验证码失败");	// 抛出异常，可以不用关心
        }
        /**
         * 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
         */
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
            servletOutputStream.write(captchaChallengeAsJpeg);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException e) {
            log.error("输出验证码失败",e);
            throw new BizException("生成图形验证码失败");	// 抛出异常，可以不用关心
        }finally {
            if(servletOutputStream!=null){
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //return ResultUtil.success();	// 返回成功提示信息
    }
}
