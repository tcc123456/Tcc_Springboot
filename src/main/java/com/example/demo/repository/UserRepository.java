package com.example.demo.repository;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    //自定义查询方法
    //1.通过名字相等查询
    List<User> findByName(String name);
    //通过名字like查询
    List<User> findByNameLike(String name);
    //通过主键ID集合查询，参数为id集合
    List<User> findByIdIn(Collection<String> ids);

}
