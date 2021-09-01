package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.User;
import com.ecjtu.lab.enums.UserAuthEnum;
import com.ecjtu.lab.mapper.UserMapper;
import com.ecjtu.lab.service.UserService;
import com.ecjtu.lab.tools.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public User adminLogin(String account, String passWord) {
        passWord= MD5Util.getMD5(passWord);
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", account).eq("passWord", passWord).eq("auth", UserAuthEnum.ADMIN.getCode()));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean updateUser(Long id,String name , String account, String passWord) {
        User user = userMapper.selectById(id);
        if (user == null) return false;
        user.setName(name);
        user.setAccount(account);
        user.setPassWord(passWord);
        int result = userMapper.updateById(user);
        return result == 1 ? true : false;
    }

    @Override
    public boolean delUserAccount(Long id) {
         int result =userMapper.deleteById(id);
        return result == 1 ? true : false;
    }

    @Override
    public boolean forbidAccount(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) return false;
        user.setStatus(CommonConstant.FORBID_USE);
        int result = userMapper.updateById(user);
        return result == 1 ? true : false;
    }

    @Override
    public boolean auditUserAccount(Long id ,String status) {
        User user = userMapper.selectById(id);
        if (user == null) return false;
        user.setStatus(status);
        int result = userMapper.updateById(user);
        return result == 1 ? true : false;
    }

    @Override
    public User findAdminByAccount(String account) {
        return userMapper.selectOne(new QueryWrapper<User>().
                eq("account", account).eq("Auth", UserAuthEnum.COMMON_USER.getCode()));
    }

    @Override
    public Page<User> findPageUsersByAuth(String authCode, Long current, Long size, String searchParam) {
        Page<User> userPage = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return userMapper.selectPage(userPage, new QueryWrapper<User>().
                    eq("auth", authCode));
        }
        return userMapper.selectPage(userPage, new QueryWrapper<User>().
                eq("auth", authCode).
                and(wrapper -> wrapper.like("name", searchParam).
                        or().like("account", searchParam).
                        or().like("password", searchParam)));
    }


    @Override
    public int addUser(String name, String account, String passWord, String auth) {
        User u = userMapper.selectOne(new QueryWrapper<User>().eq("account", account));
        if (u != null) {
            return 0;
        }
        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPassWord(passWord);
        user.setStatus(CommonConstant.COMMON_USE);
        user.setAuth(auth);
        return userMapper.insert(user);

    }
}
