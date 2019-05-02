package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

//类级别事务  意味着所有的public方法都是开启事务的
//@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Resource(name="userRepository")
    private UserRepository userRepository;

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    // List<T> findAll(Sort var1);
    @Override
    public List<User> finAll() {
        try {
            System.out.println("开始做任务");
            Long start = System.currentTimeMillis();
            List<User>  userList = userRepository.findAll();
            Long end = System.currentTimeMillis();
            System.out.println("完成任务耗时"+(end-start)+"毫秒");
            return userList;
        }catch (Exception e){
            System.out.println(e);
            return Collections.EMPTY_LIST;
        }

    }

    @Override
    @Async
    public Future<List<User>> findAsyncAll() {
        try {
            System.out.println("开始做任务");
            Long start = System.currentTimeMillis();
            List<User>  userList = userRepository.findAll();
            Long end = System.currentTimeMillis();
            System.out.println("完成任务耗时"+(end-start)+"毫秒");
            return new AsyncResult<List<User>>(userList);
        }catch (Exception e){
            System.out.println(e);
            return new AsyncResult<List<User>>(null);
        }


    }

    //方法级别事务会覆盖类级别事务
    @Transactional
    @Override
    public User save(User user) {
        User saveUser = userRepository.save(user);
        String error = null;
        error.split("/");
        return saveUser;
    }
    Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
        logger.info("userid:" + id +"用户被删除");
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public List<User> findByName(String name) {

        return userRepository.findByName(name);
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userRepository.findByNameLike(name);
    }

    @Override
    public List<User> findByIdIn(Collection<String> ids) {
        return userRepository.findByIdIn(ids);
    }



    //mybatis
    @Resource
    private UserDao userDao;

    @Override
    public User findByNameAndPassword(String name, String password) {
        return userDao.findByNameAndPassword(name,password);
    }

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }
}
