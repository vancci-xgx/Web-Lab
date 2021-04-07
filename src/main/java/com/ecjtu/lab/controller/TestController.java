package com.ecjtu.lab.controller;

import com.ecjtu.lab.Annotation.UserLoginToken;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.entity.User;
import com.ecjtu.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.UUID;
@Controller
public class TestController {

@Autowired
RedisUtil redisUtil;

    @Resource
    UserService userService;

    @RequestMapping("/demo")
    public String demo(){
        for (int i=3;i<400;i++){
            userService.addUser(UUID.randomUUID().toString(),String.valueOf(i), UUID.randomUUID().toString(), "1");
        }
        for (int i=3;i<400;i++){
            userService.addUser(UUID.randomUUID().toString(),String.valueOf(i), UUID.randomUUID().toString(), "0");
        }
        return "demo";
    }

}
