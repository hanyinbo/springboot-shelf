package com.aison.controller;

import com.aison.dto.MInfoDto;
import com.aison.dto.MInfoUnionQuery;
import com.aison.entity.MInfo;
import com.aison.entity.TUser;
import com.aison.mapper.MInfoMapper;
import com.aison.service.MInfoService;
import com.aison.service.MInfoServices;
import com.aison.service.impl.TUserServiceImpl;
import com.aison.utils.JwtTokenUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(value="测试controller",tags={"测试操作接口"})
@RestController
@AllArgsConstructor //它是lombok上的注解，使用后添加一个构造函数，该函数含有所有已声明字段属性参数
@RequestMapping(value = "aison")
@Slf4j
public class TestController {

    private MInfoService mInfoService;

    private MInfoServices mInfoServices;

    private MInfoMapper mInfoMapper;

    private TUserServiceImpl tUserServiceImpl;

    @ApiOperation("测试项目搭建启动")
    @GetMapping(value = "test1")
    public String test1() {
        return "测试搭建完成";
    }

    @ApiOperation("测试PostMapping")
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

    @ApiOperation("测试GetMapping")
    @GetMapping(value = "test2")
    public List<MInfo> testQuery() {
        List<MInfo> mInfos = mInfoService.testquery();
        log.info("请求成功:" + JSONObject.toJSONString(mInfos));
        return mInfos;
    }

    @ApiOperation("测试DeleteMapping")
    @DeleteMapping(value = "test2")
    public String testDelete() {
        Integer e = mInfoService.testDelete();
        if (e <= 0) {
            return "删除失败";
        }
        return "删除成功";
    }

    @ApiOperation("测试PutMapping")
    @PutMapping(value = "test2")
    public String testPut(Long id) {
        Integer in = mInfoService.testPut(id);
        return "更新成功";
    }

    @ApiOperation("测试Mybatisplus的XML形式")
    @GetMapping(value = "test3")
    public List<MInfo> testXML() {
        return mInfoService.tesXML();
    }

    @ApiOperation("测试Mybatisplus的XML联表查询")
    @GetMapping(value = "test4")
    public List<MInfoUnionQuery> testUnionQuery() {
        return mInfoService.findUnionQuery();
    }

    @ApiOperation("测试Mybatisplus的XML联表查询collection集合")
    @GetMapping(value = "test5")
    public List<MInfoDto> queryUnionAll() {
        return mInfoService.queryUnionAll();
    }

    @ApiOperation("测试Mybatisplus的XML联表查询多个collection集合")
    @GetMapping(value = "test6")
    public List<MInfo> testLamdba(Date beginTime, Date endTime,  String moid) {
        log.info("参数："+moid);
        LambdaQueryWrapper<MInfo> wrapper = new LambdaQueryWrapper<>();
        if (beginTime != null && endTime != null) {
            wrapper.between(MInfo::getCreatime, beginTime, endTime);
        }
        if (moid != null) {
            wrapper.like(MInfo::getMOid, moid);
        }
        return  mInfoMapper.selectList(wrapper);
    }

    @ApiOperation("测试Wrappers方式查询")
    @GetMapping(value = "test7")
    public MInfo testGetOne() {
        return mInfoServices.getOne(Wrappers.query(), false);
    }

    @ApiOperation("测试查询登录用户")
    @GetMapping(value = "test8")
    public TUser testUser(String username){
        return tUserServiceImpl.findUserByUserName(username);
    }

    @ApiOperation("测试@Value注解")
    @GetMapping(value = "test9")
    public Integer testValue(){
        log.info("getTokenHeader--FEAF--:"+JwtTokenUtils.TOKEN_PREFIX.length());
        return JwtTokenUtils.TOKEN_PREFIX.length();
    }

}
