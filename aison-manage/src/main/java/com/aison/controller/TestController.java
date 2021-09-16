package com.aison.controller;

import com.aison.dto.MInfoDto;
import com.aison.dto.MInfoUnionQuery;
import com.aison.entity.MInfo;
import com.aison.service.MInfoService;
import com.aison.service.MInfoServices;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor //它是lombok上的注解，使用后添加一个构造函数，该函数含有所有已声明字段属性参数
@RestController
@RequestMapping(value = "aison")
@Slf4j
public class TestController {

    private MInfoService mInfoService;

    private MInfoServices mInfoServices;

    @RequestMapping(value = "test1")
    public String test1() {
        return "测试搭建完成";
    }

    @PostMapping(value = "test2")
    public MInfo testSave() {
        MInfo mInfo = new MInfo();
        try {
            mInfo = mInfoService.test();
        } catch (Exception e) {
            e.printStackTrace();
            return mInfo;
        }
        return mInfo;
    }

    @GetMapping(value = "test2")
    public List<MInfo> testQuery() {
        List<MInfo> mInfos = mInfoService.testquery();
        log.info("请求成功:" + JSONObject.toJSONString(mInfos));
        return mInfos;
    }

    @DeleteMapping(value = "test2")
    public String testDelete() {
        Integer e = mInfoService.testDelete();
        if (e <= 0) {
            return "删除失败";
        }
        return "删除成功";
    }

    @PutMapping(value = "test2")
    public String testPut(Long id) {
        Integer in = mInfoService.testPut(id);
        return "更新成功";
    }

    @GetMapping(value = "test3")
    public List<MInfo> testXML() {
        return mInfoService.tesXML();
    }

    @GetMapping(value = "test4")
    public List<MInfoUnionQuery> testUnionQuery() {
        return mInfoService.findUnionQuery();
    }

    @GetMapping(value = "test5")
    public List<MInfoDto> queryUnionAll() {
        return mInfoService.queryUnionAll();
    }

    @GetMapping(value = "test6")
    public List<MInfoDto> testLamdba(Date beginTime, Date endTime,String moid) {

        LambdaQueryWrapper<MInfoDto> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(MInfoDto::getCreatime,beginTime,endTime);
        wrapper.like(MInfoDto::getMOid, moid);

        return null;
    }

    @GetMapping(value = "test7")
    public MInfo testGetOne(){
        return mInfoServices.getOne(Wrappers.query(),false);
    }

}
