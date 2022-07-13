package cn.itsource.pethome.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;
@Component
public class MailUtils {
    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 发送商家入驻的激活邮件
     * @param to    收件人 商家入驻的时候填写的邮箱地址
     * @param shopid    商家用户的id
     */
    public void sendActivityMail(String to, Long shopid){
        try {
            //创建复杂邮件对象
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //发送复杂邮件的工具类
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
            helper.setFrom("1156161220@qq.com");
            helper.setSubject("宠物之家商家激活");
            helper.setText("<h1>宠物之家商家入驻激活</h1>"+
                    "<a style='color:blue;' href=\"http://localhost/shopActivity/"+shopid+"\">点击此处进行激活</a>",true);
            //添加附件
            helper.addAttachment("附件.png",new File("D:/JAVA/1.jpg"));
            //收件人
            helper.setTo(to);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendActivityMailForUser(String email, Long userid) {
        try {
            //创建复杂邮件对象
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //发送复杂邮件的工具类
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
            helper.setFrom("1156161220@qq.com");
            helper.setSubject("宠物之家用户激活");
            helper.setText("<h1>宠物之家用户入驻激活</h1>"+
                    "<a style='color:blue;' href=\"http://localhost/userActivity/"+userid+"\">点击此处进行激活</a>",true);
            //添加附件
            helper.addAttachment("附件.png",new File("D:/JAVA/1.jpg"));
            //收件人
            helper.setTo(email);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
