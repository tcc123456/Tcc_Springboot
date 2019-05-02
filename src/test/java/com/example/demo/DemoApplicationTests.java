package com.example.demo;

import com.example.demo.model.Mood;
import com.example.demo.model.User;
import com.example.demo.service.MoodService;
import com.example.demo.service.UserService;
import com.example.demo.service.activemq.MoodProducer;
import com.example.demo.service.impl.UserServiceImpl;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Resource
    private JdbcTemplate jdbcTemplate;


    //测试集成mysql
    @Test
   public void myySqlTest(){
        String sql="select id ,name , password from user";
       List<User> userlist= (List<User>) jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs , int rowNum) throws SQLException {
                User u=new User();
                u.setId(rs.getString("id"));
                u.setName(rs.getString("name"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        });
        System.out.println("查询成功");
        for (User u:userlist) {
            System.out.println("id:"+ u.getId()+";name"+u.getName()+";password"+u.getPassword());

        }
    }



    //@Resource
    //private UserService userService;
    //测试jpa
    @Test
    public void testRespository(){
        //查询所有数据
       List<User> userList = userService.finAll();
       System.out.println("findAll()"+userList.size());


    /*   //通过name查询数据,实际是查询不到的
        List<User> userList12 = userService.findByName("李四");
        System.out.println("findByName()"+userList12.size());
        Assert.isTrue(userList12.get(0).getName().equals("李四"),"data error");*/

   /*     //通过name模糊查询  实际是查询不到的
        List<User> userList13 = userService.findByNameLike("%四%");
        System.out.println("findByNameLike()"+userList13.size());
        Assert.isTrue(userList13.get(0).getName().equals("李四"),"data error");*/

        //通过id列表查询
        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("2");
        List<User> userList14 = userService.findByIdIn(ids);
        System.out.println("findByIdIn()"+userList14.size());
        //分页查询
        PageRequest pageRequest = new PageRequest(0,10);
        Page<User> userList5 = userService.findAll(pageRequest);
        System.out.println("findAll(pageRequest)"+userList5.getTotalPages()+"/"+userList5.getSize());
        //新增数据
        User user = new User();
        user.setId("3");
        user.setName("test");
        user.setPassword("123");
        userService.save(user);
        //删除数据
    }



    //事务测试  具体见书54页
    @Test
    public void testTrantionnal(){
        User user = new User();
        user.setId("5");
        user.setName("test2");
        user.setPassword("1234");
        User user1 = userService.save(user);
        System.out.println(user1);
    }

    //redis测试
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("name","tcc");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    //log4j测试
    Logger logger = LogManager.getLogger(this.getClass());
    @Test
    public void testLo4j(){
        userService.delete("2");
        logger.info("delete sucess!!!");
    }


    //mybatis测试
    @Resource
    private UserService userService;
    @Test
    public void testMybatis(){
        User user = userService.findByNameAndPassword("李四","123");
        System.out.println(user);
        logger.info(user);
        logger.info(user.getId() + user.getName());
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("111");
        user.setPassword("2222");
        user.setId("564644");
        int insert = userService.insert(user);
        System.out.println(insert);
    }


    //同步消息队列测试
    @Resource
    private MoodService moodService;
    @Test
    public  void testMood(){
        Mood mood = new Mood();
        mood.setId("1");
        mood.setContent("第一条微信说说");
        mood.setPraise_num(0);
        mood.setPublish_time(new Date());
        mood.setUser_id("1");
        Mood m = moodService.save(mood);
        System.out.println(m);
    }

    //mq同步测试
    @Resource
    private MoodProducer moodProducer;
    @Test
    public void testActiveMQ(){
        ActiveMQQueue des = new ActiveMQQueue("tcc.queue");
        moodProducer.sendMessage(des,"hello mq");
    }

    //mq异步测试

    @Test
    public  void testActiveMQAsynSave(){
        Mood aymood = new Mood();
        aymood.setId("2");
        aymood.setContent("第2条微信说说");
        aymood.setPraise_num(0);
        aymood.setPublish_time(new Date());
        aymood.setUser_id("1");
        String msg = moodService.asynSave(aymood);
        System.out.println("异步说说发表"+msg);
    }


//这个使用异步前
    @Test
    public  void testAsync(){
        Long start = System.currentTimeMillis();
        System.out.println("第一次查询用户");
        List<User> userList = userService.finAll();
        System.out.println("第二次查询用户");
        List<User> userList2 = userService.finAll();
        System.out.println("第三次查询用户");
        List<User> userList3 = userService.finAll();
        Long end = System.currentTimeMillis();
        System.out.println("总共耗时"+(end-start)+"毫秒");
    }

   //future接口的使用及使用异步
    @Test
    public  void testAsync2() throws Exception{
        Long start = System.currentTimeMillis();
        System.out.println("第一次查询用户");
        Future<List<User>> userList = userService.findAsyncAll();
        System.out.println("第二次查询用户");
        Future<List<User>> userList2 = userService.findAsyncAll();
        System.out.println("第三次查询用户");
        Future<List<User>> userList3 = userService.findAsyncAll();
        while (true){
            if(userList.isDone() && userList2.isDone() && userList3.isDone()){
                break;
            }else {
                Thread.sleep(10);
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("总共耗时"+(end-start)+"毫秒");
    }





}
