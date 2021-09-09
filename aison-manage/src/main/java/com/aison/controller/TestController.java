package com.aison.controller;

import com.aison.service.MInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "aison")
public class TestController {

    @Autowired
    private MInfoService mInfoService;

    @RequestMapping(value = "test1")
    public String test1(){
        return "测试搭建完成";
    }

    @RequestMapping(value = "test2")
    public String test2(){
        try {
            mInfoService.test();
        }catch (Exception e){
            e.printStackTrace();
            return "请求失败";
        }
        return "请求成功";
    }
}
