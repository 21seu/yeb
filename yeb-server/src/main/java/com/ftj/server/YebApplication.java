package com.ftj.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by fengtj on 2021/8/27 1:21
 */
@SpringBootApplication
@MapperScan("com.ftj.server.mapper")
public class YebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class, args);
    }
}
