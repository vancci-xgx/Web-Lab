package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.User;

import java.util.List;

public interface UserService {
    User findAdminByAccount(String account);
    int  addUser(String name,String account ,String passWord,String auth);
    Page<User> findPageUsersByAuth(String authCode, Long current, Long size,String searchParam);
    User adminLogin(String account,String passWord);
    User getUserById(Long id);
    boolean updateUser(Long id,String name,String account,String passWord);
    boolean delUserAccount(Long id);
    boolean forbidAccount(Long id);
    boolean auditUserAccount(Long id,String status);
}
