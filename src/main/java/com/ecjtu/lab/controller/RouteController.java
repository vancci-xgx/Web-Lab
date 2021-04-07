package com.ecjtu.lab.controller;

import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.entity.ResponseMsg;
import com.ecjtu.lab.entity.User;
import com.ecjtu.lab.enums.UserAuthEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 做页面转换
 */
@Controller
public class RouteController {

    @Resource
    RedisUtil redisUtil;
    @RequestMapping(value = {"/labWebAdmin.html"})
    public String adminIndex(HttpServletRequest request){
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)){
            return "redirect:/admin_login.html";
        }
      User admin=  (User) redisUtil.hget("admin", token);
        if (admin==null ||!UserAuthEnum.ADMIN.getCode().equals(admin.getAuth())){
            return "redirect:/admin_login.html";
        }
        return "admin_index";
    }

    @RequestMapping(value = {"/404","/admin/404"})
    public String notFound(){
        return "404";
    }

    @RequestMapping(value = {"/500","/admin/500"})
    public String errorPage(){
        return "500";
    }

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "redirect:/index.html";
    }

    @RequestMapping(value = {"/manage"})
    public String admin(){
        return "redirect:/admin_login.html";
    }







//    @PostMapping("/hasToken")
//    public void   hasToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String token = request.getHeader("token");
//        if (StringUtils.isNotBlank(token)){
//            User admin =(User) redisUtil.hget("admin", token);
//            if (admin!=null &&UserAuthEnum.ADMIN.getCode().equals(admin.getAuth())){
//                response.sendRedirect("/index");
//            }
//        }
//
//    }
}
