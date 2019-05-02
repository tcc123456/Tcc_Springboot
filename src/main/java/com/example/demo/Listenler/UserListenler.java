package com.example.demo.Listenler;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Collection;
import java.util.List;

//监听器
@WebListener
public class UserListenler implements ServletContextListener {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    private static final String ALL_USER = "ALL_USER_LIST";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //查询数据库中所有的用户
        List<User> userList = userService.finAll();
        System.out.println(userList );//[User{id='1', name='李四', password='123456'}, User{id='2', name='王五', password='123456'}, User{id='3', name='test', password='123'}, User{id='4', name='test2', password='1234'}, User{id='5', name='test2', password='1234'}]
        //清楚缓存中的用户数据
        redisTemplate.delete(ALL_USER );
        //将数据存放到Redis缓存中   Long leftPushAll(K var1, Collection<V> var2);
        //redisTemplate.opsForList().leftPushAll(ALL_USER , userList);
        redisTemplate.opsForList().rightPush(ALL_USER, "one");
        //List<User> querylist = redisTemplate.opsForList().range(ALL_USER ,0,-1);
        List<String> list4 = redisTemplate.opsForList().range(ALL_USER , 0, -1);
        System.out.println("缓存中目前的用户数"+list4.size() );
        System.out.println("ServletContextEvent上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextEvent上下文销毁");
    }
}
