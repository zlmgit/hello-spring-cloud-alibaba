package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import io.swagger.annotations.Api;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@Api(tags = "邮件服务")
public class EmailController {


    private final String TO_EMAIL = "13477407066@163.com";
    private final String FROM_EMAIL = "1552005882@qq.com";
    private final String EMAIL_PASSWORD = "yhvdrdpsdubpbaae";
    private final String EMAIL_HOST_NAME = "smtp.qq.com";
    /**
     * 简单邮件发送
     * @throws EmailException
     */
    @Async("send_mail")
    @PostMapping(value = "/sendMail")
    public void sendSimpleMail() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(EMAIL_HOST_NAME);
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
        email.setSSLOnConnect(true);
        email.setFrom(FROM_EMAIL);
        email.setSubject("测试邮件主题");
        email.setMsg("测试邮件内容");
        email.addTo(TO_EMAIL);
        email.send();
    }

    /**
     * 带附件邮件
     * @throws EmailException
     */
    @Async
    @PostMapping(value = "/attachmentsMail")
    public void attachmentsMail() throws EmailException {
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("C:/Users/zy/Desktop/企业微信截图_15742444498315.png");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Picture of John");
        attachment.setName("John");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(EMAIL_HOST_NAME);
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
        email.addTo(TO_EMAIL, "朱路明收件箱");
        email.setFrom(FROM_EMAIL, "朱路明发件箱");
        email.setSubject("带附件邮件的邮件");
        email.setMsg("这是一份带附件的邮件");

        // add the attachment
        email.attach(attachment);

        // send the email
        email.send();
    }

    /**
     * 带附件邮件附件来源于网络
     * @throws EmailException
     */
    @PostMapping(value = "/attachmentsMailWithUrl")
    public void attachmentsMailWithUrl() throws Exception {
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Apache logo");
        attachment.setName("Apache logo");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(EMAIL_HOST_NAME);
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
        email.addTo(TO_EMAIL, "朱路明收件箱");
        email.setFrom(FROM_EMAIL, "朱路明发件箱");
        email.setSubject("带附件邮件附件来源于网络");
        email.setMsg("这是带附件邮件附件来源于网络");

        // add the attachment
        email.attach(attachment);

        // send the email
        email.send();
    }

    /**
     * 发送带有内联图像的格式化HTML内容的电子邮件。
     * @throws Exception
     */
    @PostMapping(value = "/htmlEmail")
    public void htmlEmail() throws Exception {
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
        email.setHostName(EMAIL_HOST_NAME);
        email.addTo(TO_EMAIL, "收件人列表标题");
        email.setFrom(FROM_EMAIL, "Me");
        email.setSubject("这是HTML邮件");

        // embed the image and get the content id
        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
        String cid = email.embed(url, "Apache logo");
        System.out.println("cid");
        // set the html message
        email.setHtmlMsg("<html>The apache logo - <img src=\"cid:"+cid+"\"></html>");

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }

    /**
     * 发送带有嵌入式图像的HTML格式的电子邮件
     * @throws Exception
     */
    @PostMapping(value = "/imageHtmlEmail")
    public void imageHtmlEmail () throws Exception {
        // load your HTML email template
        String htmlEmailTemplate = ".... <img src=\"http://www.apache.org/images/feather.gif\"> ....";

        // define you base URL to resolve relative resource locations
        URL url = new URL("http://www.apache.org");

        // create the email message
        ImageHtmlEmail email = new ImageHtmlEmail();
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
        email.setDataSourceResolver(new DataSourceUrlResolver(url));
        email.setHostName(EMAIL_HOST_NAME);
        email.addTo(TO_EMAIL, "嵌入式图像的HTML格式");
        email.setFrom(FROM_EMAIL, "Me");
        email.setSubject("Test email with inline image");

        // set the html message
        email.setHtmlMsg(htmlEmailTemplate);

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // send the email
        email.send();
    }
}
