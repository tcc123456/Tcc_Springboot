package com.example.demo.service.impl;

import com.example.demo.model.Mood;
import com.example.demo.repository.MoodRespository;
import com.example.demo.service.MoodService;
import com.example.demo.service.activemq.MoodProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service
public class MoodServiceImpl implements MoodService {
    @Resource
    private MoodRespository moodRespository;

    @Override
    public Mood save(Mood mood) {
        return moodRespository.save(mood);
    }


    private static Destination destination = new ActiveMQQueue("tcc.queue.asyn.save");
    @Resource
    private MoodProducer moodProducer;

    @Override
    public String asynSave(Mood mood) {
        //往队列tcc.queue.asyn.save推送消息，消息内容为说说实体
        moodProducer.sendMessage(destination,mood);
        System.out.println("往队列添加消息成功");
        return "success";

    }
}
