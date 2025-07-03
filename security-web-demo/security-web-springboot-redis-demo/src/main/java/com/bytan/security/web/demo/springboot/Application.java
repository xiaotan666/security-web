package com.bytan.security.web.demo.springboot;

import com.bytan.security.springboot.starter.EnabledSecurityWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//使用自定义endpoint，则关闭默认的endpoint
@EnabledSecurityWeb(endpoint = false)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
