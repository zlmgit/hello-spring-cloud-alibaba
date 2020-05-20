package com.zlm.hello.spring.cloud.alibaba.nacos.provider3;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Mail;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.ServiceLog;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication3.class)
public class MongoDBServiceTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Test
    public void testSave(){
        ServiceLog serviceLog = new ServiceLog();
        serviceLog.setServiceDate(new Date());
        serviceLog.setServiceName("test");
        serviceLog.setMsg("test_msg");
        mongoTemplate.save(serviceLog);
        System.out.println(serviceLog);
    }


    @Test
    public void testRemove() throws ParseException {
        Query queryOne = new Query(Criteria.where("id").is("5e101b3248758a3938a4a447"));
        ServiceLog serviceLog = mongoTemplate.findOne(queryOne, ServiceLog.class);
        Query query = new Query(Criteria.where("service_name")
                .is("test")
                .and("msg").is("test-msg")
                .and("service_date").lt(new Date()));
        List<ServiceLog> serviceLogs = mongoTemplate.findAllAndRemove(query, ServiceLog.class);
        System.out.println(serviceLogs);
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println(maxMemory/(double)1024/1024);
        System.out.println(totalMemory/(double)1024/1024);
        Collection collection = null;
        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(2);
        Executors.newCachedThreadPool();
    }

    @Test
    public void sendQQMail() throws MessagingException {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("1552005882@qq.com");
        simpleMailMessage.setTo("13477407066@163.com");
        simpleMailMessage.setSubject("BugBugBug");
        simpleMailMessage.setText("一杯茶，一根烟，一个Bug改一天");
        mailSender.send(simpleMailMessage);
        //复杂邮件
        /*MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("1552005882@qq.com");
        messageHelper.setTo("13477407066@163.com");
        messageHelper.setSubject("BugBugBug");
        messageHelper.setText("一杯茶，一根烟，一个Bug改一天！");
        messageHelper.addInline("bug.gif",new File("C:\\Users\\zy\\Desktop\\漏打卡.txt"));
        messageHelper.addAttachment("bug.docx", new File("C:\\Users\\zy\\Desktop\\漏打卡.txt"));
        mailSender.send(mimeMessage);*/
   }
   @Autowired
   private MailService mailService;
    @Test
    public void send163Mail(){
        String companyName = "企业名称";
        String companyAccount = "1002Ih13477407066";
        String passwd = "dsfewvqdvwrv";
        String text = "</html>\n" +
                "<style>\n" +
                "    p{text-indent: 2em;padding:0;margin: 0}\n" +
                "</style>\n" +
                "<body>\n" +
                "尊敬的合作伙伴：\n" +
                "<p>您的灵猴企业账户已开通</p>\n" +
                "<p>企业名称："+companyName+"</p>\n" +
                "<p>账号："+companyAccount+"</p>\n" +
                "<p>密码："+passwd+"</p>\n" +
                "<p>后台登录网址：<a href=\"https://www.linghouzy.com\" style='text-decoration:none;' >https://www.linghouzy.com</a></p>\n" +
                "<p>初始密码设置较为简单，为保护您的账户安全，首次登录后请您尽快修改您的初始密码。</p>\n" +
                "<p>附件为灵猴网企业操作指引及个人操作指引，若有疑问可联系客服：4006865050。</p>\n" +
                "</body>\n" +
                "</html>";
        Mail mailDTO = new Mail();
        mailDTO.setTo("luming.zhu@ezhiyang.com");
        mailDTO.setBcc("13477407066@163.com");
        mailDTO.setCc("1552005882@qq.com");
        mailDTO.setSubject("测试邮件2");
        mailDTO.setText("hello");
        mailService.sendMail(mailDTO);
    }

}
