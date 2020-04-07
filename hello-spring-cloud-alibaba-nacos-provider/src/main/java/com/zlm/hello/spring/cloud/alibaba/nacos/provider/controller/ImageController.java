package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.exception.BizException;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.redis.RedisService;
import io.swagger.annotations.Api;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;


@RestController
@Api(tags = "图片验证码")
public class ImageController {

    private static Logger log = LoggerFactory.getLogger(ImageController.class);

    public static final int cache = 10 * 1024;

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

    /**
     * 远程下载图片响应出去
     * @param resp
     */
    @RequestMapping(value = "/receiveImage")
    public void receiveImage(HttpServletResponse resp) {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585915316276&di=31dfdb77a6ca2f891766bc361c2e4eaf&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D3616242789%2C1098670747%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D900%26h%3D1350";
        resp.setContentType("image/jpeg");
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            HttpUtil.download(url,outputStream,true);
        }catch (Exception e){
            log.error("图片读取失败",e);
        }
        /*InputStream in = null;
        OutputStream out = null;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            in = entity.getContent();
            out = resp.getOutputStream();
            IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        } catch (Exception e) {
            log.error("图片读取失败",e);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    log.error("响应流关闭失败",e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("读取流流关闭失败",e);
                }
            }
        }*/
    }

}
