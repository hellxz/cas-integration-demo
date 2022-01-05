package com.hellxz.cas;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.boot.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCasClient
public class CasAutoConfigApp {

    public static void main(String[] args) {
        SpringApplication.run(CasAutoConfigApp.class, args);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        return "服务A测试通过";
    }

}
