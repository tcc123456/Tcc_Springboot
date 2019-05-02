package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
//用户服务层接口

public interface UserService {
    User findById(String id);
    User save(User user);
    void delete(String id);
    //分页,指定pagenumber为第几页和pagesize为每页的大小即可
    Page<User> findAll(Pageable pageable);
    //自定义查询方法
    //1.通过名字相等查询
    List<User> findByName(String name);
    //通过名字like查询
    List<User> findByNameLike(String name);
    //通过主键ID集合查询，参数为id集合
    List<User> findByIdIn(Collection<String> ids);
    User findByNameAndPassword(String name ,String password);
    int insert(User user);
    //同步查询
    List<User> finAll();
    //异步查询方法
   Future<List<User>>  findAsyncAll();

}
