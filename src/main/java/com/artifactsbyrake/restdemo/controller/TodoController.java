package com.artifactsbyrake.restdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/0")
public class TodoController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Rest demo";
    }

    @GetMapping(path = "/myerror")
    public String errorService() {
        throw new RuntimeException("Some Exception Occured");
    }
}
