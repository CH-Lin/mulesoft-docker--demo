package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Start extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
    }
}
