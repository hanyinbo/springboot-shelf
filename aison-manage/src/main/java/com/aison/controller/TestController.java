package com.aison.controller;

import com.aison.entity.MInfo;
import com.aison.service.MInfoService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public MInfo testSave(){
        MInfo mInfo = new MInfo();
        try {
            mInfo = mInfoService.test();
        }catch (Exception e){
            e.printStackTrace();
            return mInfo;
        }
        return mInfo;
    }

    @GetMapping(value = "test2")
    public List<MInfo> testQuery() {
       List<MInfo> mInfos =  mInfoService.testquery();
        logger.info("请求成功:"+ JSONObject.toJSONString(mInfos));
        return mInfos;
    }

    @DeleteMapping(value = "test2")
    public String testDelete(){
        Integer e = mInfoService.testDelete();
        if(e<=0){
            return "删除失败";
        }
        return "删除成功";
    }
    @PutMapping(value = "test2")
    public String testPut(Long id){
        Integer in = mInfoService.testPut(id);
        return "更新成功";
    }
}
