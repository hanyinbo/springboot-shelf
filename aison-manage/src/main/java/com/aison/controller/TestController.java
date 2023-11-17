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
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Api(value = "测试controller", tags = {"测试操作接口"})
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
    @GetMapping(value = "c")
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
    public List<MInfo> testLamdba(Date beginTime, Date endTime, String moid) {
        log.info("参数：" + moid);
        LambdaQueryWrapper<MInfo> wrapper = new LambdaQueryWrapper<>();
        if (beginTime != null && endTime != null) {
            wrapper.between(MInfo::getCreateTime, beginTime, endTime);
        }
        if (moid != null) {
            wrapper.like(MInfo::getMOid, moid);
        }
        return mInfoMapper.selectList(wrapper);
    }

    @ApiOperation("测试Wrappers方式查询")
    @GetMapping(value = "test7")
    public MInfo testGetOne() {
        return mInfoServices.getOne(Wrappers.query(), false);
    }

    @ApiOperation("测试查询登录用户")
    @GetMapping(value = "test8")
    public TUser testUser(String username) {
        return tUserServiceImpl.findUserByUserName(username);
    }

    //
//    @ApiOperation("测试@Value注解")
//    @GetMapping(value = "test9")
//    public Integer testValue(){
//        log.info("getTokenHeader--FEAF--:"+JwtTokenUtils.TOKEN_PREFIX.length());
//        return JwtTokenUtils.TOKEN_PREFIX.length();
//    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //单线程执行
//		ExecutorService singleThread = Executors.newSingleThreadExecutor();
//		for (int i = 0 ;i<100;i++){
//			singleThread.execute(new Runnable() {
//				@Override
//				public void run() {
//					System.out.println("线程名："+Thread.currentThread().getName()+"==");
//					System.out.println("线程状态："+singleThread.isTerminated());
//				}
//			});
//		}
        //固定线程
//		ExecutorService fixedThread = Executors.newFixedThreadPool(5);
//		for(int i =0 ;i<100;i++){
//			fixedThread.execute(new Runnable() {
//				@Override
//				public void run() {
//					System.out.println("线程名："+Thread.currentThread().getName()+"==");
//					System.out.println("线程状态："+fixedThread.isTerminated());
//				}
//			});
//		}

        //缓存线程池
//        ExecutorService cachedThread = Executors.newCachedThreadPool();
//        for (int i = 0; i < 1000; i++) {
//            cachedThread.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("线程名：" + Thread.currentThread().getName());
//                }
//            });
//        }
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            int result = 100;
//            System.out.println("第一次运算：" + result);
//            return result;
//        }).thenApply(number -> {
//            int result = number * 3;
//            System.out.println("第二次运算：" + result);
//            return result;
//        });
        //将上一段任务执行结果作为入参传递给下一个任务，产生新的结果
        System.out.println("thenApply======是同一个CompletableFuture");
        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
            List<String> codeList = new ArrayList<>();
            codeList.add("334");
            codeList.add("099400");
            return codeList;
        }).thenApply(codes -> {
            List<String> newList = new ArrayList<>();
            codes.stream().forEach(code -> {
                newList.add("1234===" + code);
                System.out.println("899==" + code);
            });
            return newList;
        });
        List<String> strings = future.get();
        System.out.println("外层输出：" + strings);

        System.out.println("thenCompose");
        CompletableFuture<Integer> composeFuture = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(30);
                        System.out.println("第一次运算：" + number);
                        return number;
                    }
                })
                .thenCompose(new Function<Integer, CompletionStage<Integer>>() {
                    @Override
                    public CompletionStage<Integer> apply(Integer param) {
                        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                            @Override
                            public Integer get() {
                                int number = param * 2;
                                System.out.println("第二次运算：" + number);
                                return number;
                            }
                        });
                    }
                });
    }

}
