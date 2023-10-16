package com.xkxt.xuankexitong.controller;

import com.xkxt.xuankexitong.bean.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/api/users/${id}/pwd")
    public String login(@RequestBody User user){
        System.out.println("user:"+user);
        return "ok";
    }
}
