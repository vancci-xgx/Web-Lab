package com.ecjtu.lab.intercepter;

import com.ecjtu.lab.Annotation.PassToken;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.entity.User;
import com.ecjtu.lab.enums.UserAuthEnum;
import com.ecjtu.lab.service.UserService;
import com.ecjtu.lab.tools.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object object) throws Exception {

        // 如果不是映射到方法跳转404
        if (!(object instanceof HandlerMethod)) {
            httpServletResponse.sendRedirect("/404.html");
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //没有passtoken
        String token = httpServletRequest.getParameter("token");// 从 http 请求头中取出 token
        if (StringUtils.isBlank(token)) {
            httpServletResponse.sendRedirect("/admin_login.html");
            return false;
        }
        User admin =(User) redisUtil.hget("admin", token);
        if (admin==null ||!UserAuthEnum.ADMIN.getCode().equals(admin.getAuth())){
            httpServletResponse.sendRedirect("/admin_login.html");
            return false;
        }

        return  true;
    }


        //UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
//            if (userLoginToken.required()) {
//                // 执行认证
//                if (token == null) {
//                    throw new TokenException();
//                }
//                // 获取 token 中的 user id
//                String userId;
//                try {
//                    userId = JWT.decode(token).getAudience().get(0);
//                } catch (JWTDecodeException j) {
//                    throw new TokenException();
//                }
//                User user = userService.findAdminByAccount(userId);
//                if (user == null) {
//                    throw new TokenException();
//                }
//                // 验证 token
//                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
//                try {
//                    jwtVerifier.verify(token);
//                } catch (JWTVerificationException e) {
//                    throw new TokenException();
//
//                }
//                return true;
//            }

    //    return true;
   // }

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
