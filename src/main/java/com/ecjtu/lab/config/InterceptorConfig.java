package com.ecjtu.lab.config;

import com.ecjtu.lab.intercepter.AdminHtmlInterceptor;
import com.ecjtu.lab.intercepter.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置添加拦截器
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    private static List<String> htmlList = new ArrayList<String>() {
        {
            add("/labIntroduce.html");
            add("/add_bulletin_board.html");
            add("/add_hot_news.html");
            add("/add_case.html");
            add("/add_teacher_introduce.html");
            add("/add_teacher_ppt.html");
            add("/add_member_introduce.html");
            add("/add_paper.html");
            add("/paper_Info.html");
            add("/add_user.html");
            add("/admin_index.html");
            add("/admin_info.html");
            add("/bulletin_board_info.html");
            add("/common_user.html");
            add("/hot_news_info.html");
            add("/labIntroduce.html");
            add("/member_introduce_info.html");
            add("/teacher_introduce_info.html");
            add("/teaching_ppt_info.html");
            add("/update_hot_news.html");
            add("/update_case.html");
            add("/update_common_user.html");
            add("/update_bulletin_board.html");
            add("/update_teacher_introduce.html");
            add("/update_member_introduce.html");
        }
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/admin/**");// 拦截所有后台请求
        registry.addInterceptor(adminHtmlInterceptor()).addPathPatterns(htmlList);
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public AdminHtmlInterceptor adminHtmlInterceptor(){
        return new AdminHtmlInterceptor();
    }
}