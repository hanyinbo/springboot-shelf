package com.aison.controller;

import com.aison.common.Result;
import com.aison.entity.WxNavigationImg;
import com.aison.entity.WxSwiperImg;
import com.aison.service.WxNavigationImgService;
import com.aison.service.WxSwiperImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/mini")
public class WxSwiperImgController {
    @Autowired
    private WxSwiperImgService wxSwiperImgService;
    @Autowired
    private WxNavigationImgService wxNavigationImgService;

    @GetMapping(value = "/getSwiperImgList")
    public Result<List<WxSwiperImg>> getSwiperImgList(){
        return Result.buildOk(wxSwiperImgService.list());
    }

    @GetMapping(value = "/getNavigationImgList")
    public Result<List<WxNavigationImg>> getNavigationImgList(){
        return Result.buildOk(wxNavigationImgService.list());
    }
}
