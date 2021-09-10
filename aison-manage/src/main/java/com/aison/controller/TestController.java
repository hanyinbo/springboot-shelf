package com.aison.controller;

import com.aison.service.MInfoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "aison")
public class TestController {

    Logger logger = LoggerFactory.getLogger(Object.class);
    @Autowired
    private MInfoService mInfoService;

    @RequestMapping(value = "test1")
    public String test1(){
        return "测试搭建完成";
    }

    @PostMapping(value = "test2")
    public String testSave(){
        try {
            mInfoService.test();
        }catch (Exception e){
            e.printStackTrace();
            return "请求失败";
        }
        return "请求成功";
    }

    @GetMapping(value = "test2")
    public String testQuery() {
        logger.info("请求成功:");
        return "请求成功";
    }

    @DeleteMapping(value = "test3")
    public String test3(){
        return "";
    }
}
