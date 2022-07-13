package cn.itsource.pethome.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailTest {
    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void  send(){
        //发送普通邮件 只能纯文本
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //发送人
        mailMessage.setFrom("1156161220@qq.com");
        //邮件题目
        mailMessage.setSubject("简单邮件");
        //邮件内容
        mailMessage.setText("<h1>不能解析标签</h1>");
        //收件人
        mailMessage.setTo("liuchenchenanz@163.com");
        //发送邮件
        javaMailSender.send(mailMessage);
    }

    @Test
    public void  sendComplex() throws MessagingException {
        //发送复杂邮件 可以解析html标签
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom("1156161220@qq.com");
        helper.setSubject("复杂邮件");
        //true要解析html标签
        helper.setText("<h1>可以解析标签</h1>",true);
        //发送附件  文件名,文件(文件路径)
        helper.addAttachment("附件.jpg",new File("D:/JAVA/1.jpg"));
        helper.setTo("liuchenchenanz@163.com");
        //发送
        javaMailSender.send(mimeMessage);

    }



}