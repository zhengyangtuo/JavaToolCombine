package org.example.config;

import org.example.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
    }
    //配置虚拟路径映射访问，解决上传文件到服务器不重启无法访问资源的问题
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String path = System.getProperty("user.dir")+"\\Dubbo-service\\src\\main\\resources\\static\\editor-md-upload\\";
//        String path = "D:\\javaWorks\\Dubbo3Project\\Dubbo-service\\src\\main\\resources\\static\\editor-md-upload\\";

        registry.addResourceHandler("/editor-md-upload/**").addResourceLocations("file:"+path);
    }
}
