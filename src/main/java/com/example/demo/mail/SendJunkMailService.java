package com.example.demo.mail;

import com.example.demo.model.User;

import java.util.List;

//发送用户邮件服务
public interface SendJunkMailService {
  boolean senJnkMail(List<User> user);
}
