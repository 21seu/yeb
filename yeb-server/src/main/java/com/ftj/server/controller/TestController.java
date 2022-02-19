package com.ftj.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fengtj on 2022/2/19 23:38
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
