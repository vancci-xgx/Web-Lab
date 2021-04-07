package com.ecjtu.lab.config;
import com.ecjtu.lab.constant.CommonConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig   implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //放到服务器需要改
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+ CommonConstant.UPLOAD_ADDR);
    }
}
