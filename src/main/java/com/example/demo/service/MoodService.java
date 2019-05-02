package com.example.demo.service;

import com.example.demo.model.Mood;

//微信说说服务层
public interface MoodService {
   Mood save(Mood mood);//同步保存消息的接口
   String asynSave(Mood mood); //异步保存消息的接口
}
