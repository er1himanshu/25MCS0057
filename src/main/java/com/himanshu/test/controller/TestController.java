package com.himanshu.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.test.utils.LoggingService;

@RestController
public class TestController {

    @Autowired
    private LoggingService loggingService;

    @GetMapping("/hello")
    public String hello() {

        loggingService.log(
                "info",
                "controller",
                "Hello endpoint called"
        );

        return "my backend is working!";
    }
}