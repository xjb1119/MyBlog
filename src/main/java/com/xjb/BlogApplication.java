package com.xjb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// 由于部署上服务器，继承SpringBootServletInitializer
@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {

        SpringApplication.run(BlogApplication.class, args);
    }

    //重写此方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return super.configure(builder);
    }
}
