package com.fs.swms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.fs.swms.*.mapper")
@ComponentScan(basePackages = "com.fs.swms.*.service")
@ComponentScan(basePackages = "com.fs.swms.*.aspect")
@ComponentScan(basePackages = "com.fs.swms.*.component")
@ComponentScan(basePackages = "com.fs.swms.*.controller")
public class SwmsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SwmsApplication.class, args);
    }
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(SwmsApplication.class);
    }
}
