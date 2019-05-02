package com.example.demo.mail.impl;

import com.example.demo.mail.SendJunkMailService;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import java.util.List;
@Service
public class SendJunkMailServiceImp implements SendJunkMailService {
    @Autowired
    JavaMailSender mailSender;
    @Resource
    private UserService userService;
    @Value("${spring.mail.username}")
    private String from;
    public static final Logger logger = LogManager.getLogger(SendJunkMailServiceImp.class);


    @Override
    public boolean senJnkMail(List<User> userList) {
        try {
            if(userList==null || userList.size()<=0){
               return Boolean.FALSE;
            }
            for (User user : userList) {
                MimeMessage mimeMessage = this.mailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(from);
                //邮件主题
                message.setSubject("地瓜今日特卖");
                //邮件接收方
                message.setTo("1050011290@qq.com");
                message.setText(user.getName()+"你知道吗，地瓜特卖");
                //发送邮件
                this.mailSender.send(mimeMessage);
                logger.info("发送成功");

            }
        }catch (Exception ex){
            logger.error(ex);
            logger.error("出错了");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
