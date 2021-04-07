package com.ecjtu.lab.intercepter;

import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.entity.User;
import com.ecjtu.lab.enums.UserAuthEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台HTML页面访问前判断token
 */
public class AdminHtmlInterceptor implements HandlerInterceptor {
    @Autowired
    RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getParameter("token");
        if (StringUtils.isBlank(token)){
            response.sendRedirect("/admin_login.html");
            return false;
        }
        User admin =(User) redisUtil.hget("admin", token);
        if (admin==null ||!UserAuthEnum.ADMIN.getCode().equals(admin.getAuth())){
            response.sendRedirect("/admin_login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
