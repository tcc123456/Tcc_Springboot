package com.example.demo.service.activemq;
import com.example.demo.model.Mood;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import javax.jms.Destination;
import javax.annotation.Resource;

//生产者
@Service
public class MoodProducer {
    @Resource
    // JmsMessagingTemplate 发消息的工具类，Destination 是发送到队列的 message待发送的消息
    private JmsMessagingTemplate jmsMessagingTemplate;
    public void sendMessage(Destination destination,final String message){
        jmsMessagingTemplate.convertAndSend(destination,message);
    }
    public void sendMessage(Destination destination,final Mood aymood){
        jmsMessagingTemplate.convertAndSend(destination,aymood);
    }
}
