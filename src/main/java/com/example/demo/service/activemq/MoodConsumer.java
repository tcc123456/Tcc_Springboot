package com.example.demo.service.activemq;

import com.example.demo.model.Mood;
import com.example.demo.service.MoodService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//消费者
@Component
public class MoodConsumer {
    @JmsListener(destination = "tcc.queue")
    public void receiveQueue(String text){
        System.out.println("用户发说说成功"+text);
    }
    @Resource
    private MoodService moodService;
    @JmsListener(destination = "tcc.queue.asyn.save")
    public void receiveQueue(Mood mood){
        System.out.println("监听到了消息推送到了MQ");
        moodService.save(mood);
        System.out.println("用户2发说说成功"+mood);
    }
}
