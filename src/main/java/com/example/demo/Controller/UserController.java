package com.example.demo.Controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    public UserService userService;
    @RequestMapping("/test")
    public String test(Model model){
        List<User> users = userService.finAll();
        model.addAttribute("users",users);
        return "user";
    }
}
