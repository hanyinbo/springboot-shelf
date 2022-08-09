package com.aison.controller;

import com.aison.common.Result;
import com.aison.utils.MinioService;
import com.aison.utils.MinioUtil;
import io.minio.messages.Bucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "文件管理模块")
@RestController
@RequestMapping("/sys-file")
@Slf4j
public class MinioController {
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioService minioService;


    @ApiOperation(value = "查看存储bucket是否存在")
    @GetMapping("/bucketExists")
    public Result bucketExists(String bucketName) {
        return Result.buildOk(minioUtil.bucketExists(bucketName));
    }

    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getAllBuckets")
    public Result getAllBuckets() {

        return Result.buildOk(minioUtil.getAllBuckets());
    }

    @ApiOperation(value = "获取全部bucket")
    @PostMapping("/makeBucket")
    public Result makeBucket(String ei) {

        return Result.buildOk(minioUtil.makeBucket(ei));
    }

    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getObjectInfo")
    public Result getObjectInfo(String bucket, String fileName) throws Exception {
        bucket = "swiper";
        fileName = "swiper1.jpg";
        return Result.buildOk(minioUtil.getObjectInfo(bucket, fileName));
    }

    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/preview")
    public Result preview() {
        String fileName = "swiper1.jpg";
        return Result.buildOk(minioUtil.preview(fileName));
    }


    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getPathUrl")
    public Result getPathUrl() {
       return Result.buildOk(minioService.getObjectUrl("eeew","swiper2.png"));
    }

}
