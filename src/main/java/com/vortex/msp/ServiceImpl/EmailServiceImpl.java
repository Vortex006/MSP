package com.vortex.msp.ServiceImpl;

import com.vortex.msp.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

/**
 * 邮件业务类
 *
 * @author qzz
 */
@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    /**
     * 注入邮件工具类
     */
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 检测邮件信息类
     *
     * @param to
     * @param subject
     * @param text
     */
    private void checkMail(String to, String subject, String text) {
        if (StringUtils.isEmpty(to)) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (StringUtils.isEmpty(subject)) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    /**
     * 发送纯文本邮件
     *
     * @param to
     * @param subject
     * @param text
     */
    @Override
    public void sendTextMailMessage(String to, String subject, String text) {

        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text,true);
            Resource resource = new ClassPathResource("static/index.html");
            try {
                mimeMessageHelper.addAttachment("index.html",resource.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

            System.out.println("发送邮件成功：" + sendMailer + "->" + to);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败：" + e.getMessage());
        }
    }


}

