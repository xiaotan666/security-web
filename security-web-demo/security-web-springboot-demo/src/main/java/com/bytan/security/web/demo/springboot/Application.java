package com.bytan.security.web.demo.springboot;

import com.bytan.security.springboot.starter.EnabledSecurityWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnabledSecurityWeb
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
