package com.aison.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "aison")
public class TestController {

    @RequestMapping(value = "test1")
    public String test1(){
        return "测试搭建完成";
    }
}
