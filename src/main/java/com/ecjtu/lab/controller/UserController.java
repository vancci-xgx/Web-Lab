package com.ecjtu.lab.controller;

import com.ecjtu.lab.entity.ResponseMsg;
import com.ecjtu.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseMsg registerUser(String account,String passWord){

        return null;
    }
}
