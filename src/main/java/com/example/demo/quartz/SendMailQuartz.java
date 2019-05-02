package com.example.demo.quartz;

import com.example.demo.mail.SendJunkMailService;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
//扫描初始化
@Configurable
//开启对计划任务的支持
@EnableScheduling
//定时器类
public class SendMailQuartz {

    private static final Logger logger = LogManager.getLogger(SendMailQuartz.class);
    @Resource
    private SendJunkMailService sendJunkMailService;
    @Resource
    private UserService userService;

    //注解为定时任务
    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentByCron(){
        List<User> userList = userService.finAll();
        if(userList == null || userList.size()<=0){
            return;
        }
        sendJunkMailService.senJnkMail(userList);
        logger.info("定时器运行了");
    }
}
