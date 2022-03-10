package com.aison.controller;

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
    public Boolean bucketExists(String bucketName) {
//        Boolean aBoolean = minioUtil.bucketExists(bucketName);
        return minioUtil.bucketExists(bucketName);
    }

    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getAllBuckets")
    public List<Bucket> getAllBuckets() {

        return minioUtil.getAllBuckets();
    }

    @ApiOperation(value = "获取全部bucket")
    @PostMapping("/makeBucket")
    public Boolean makeBucket(String ei) {

        return minioUtil.makeBucket(ei);
    }

    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getObjectInfo")
    public String getObjectInfo(String bucket, String fileName) throws Exception {
        bucket = "swiper";
        fileName = "swiper1.jpg";
        return minioUtil.getObjectInfo(bucket, fileName);
    }

    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/preview")
    public String preview() {
        String fileName = "swiper1.jpg";
        return minioUtil.preview(fileName);
    }


    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getPathUrl")
    public String getPathUrl() {
       return minioService.getObjectUrl("eeew","swiper2.png");
    }

}
