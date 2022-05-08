package com.ftj.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fengtj on 2022/5/8 13:58
 */
@RestController
public class HelloController {

    @GetMapping("/employee/basic/hello")
    public String basic() {
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String advanced() {
        return "/employee/advanced/hello";
    }
}
