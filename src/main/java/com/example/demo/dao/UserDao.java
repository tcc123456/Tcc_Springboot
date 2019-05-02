package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

//@Mapper mybatis根据接口定义域Mapper文件中的sql语句动态创建接口实现
@Mapper
public interface UserDao {
    //通过用户名和密码查询用户
    //@Param:注解参数，在Mapper.xml配置文件中，可以采用#{}的方式对@param注解括号内的参数进行引用
    User findByNameAndPassword(String name, String password);
    int insert(User user);
}
