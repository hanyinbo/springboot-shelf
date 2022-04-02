package com.aison.controller;

import com.aison.common.Result;
import com.aison.dto.WxCompanyDto;
import com.aison.entity.*;
import com.aison.service.*;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/mini")
@Slf4j
public class WxController {
    @Autowired
    private WxSwiperImgService wxSwiperImgService;
    @Autowired
    private WxNavigationImgService wxNavigationImgService;
    @Autowired
    private WxCompanyService wxCompanyService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxCompanyDetailImgService wxCompanyDetailImgService;
    @Autowired
    private WxPositionService wxPositionService;

    @GetMapping(value = "/getSwiperImgList")
    public Result<List<WxSwiperImg>> getSwiperImgList(){
        return Result.buildOk(wxSwiperImgService.list());
    }

    @GetMapping(value = "/getNavigationImgList")
    public Result<List<WxNavigationImg>> getNavigationImgList(){
        return Result.buildOk(wxNavigationImgService.list());
    }

    @GetMapping(value = "/getCompanyList")
    public Result<List<WxCompanyDto>> getCompanyList(){
        List<WxCompanyDto> dtoList = new ArrayList<>();
        WxCompanyDto dto = new WxCompanyDto();
        dto.setType("推荐企业");
        List<WxCompany> companyList = wxCompanyService.list();
        log.info("企业列表："+ JSONObject.toJSONString(companyList));
        dto.setCompanyList(companyList);
        dtoList.add(dto);
        return Result.buildOk(dtoList);
    }
    /*
    *获取所有公司列表
     */
    @GetMapping(value = "/getAllCompanyList")
    public Result<List<WxCompany>> getAllCompanyList(){

        List<WxCompany> companyList = wxCompanyService.list();

        return Result.buildOk(companyList);
    }

    /**
     * 根据ID获取公司详情
     * @param companyId
     * @return
     */
    @GetMapping(value = "/getCompanyDetailById")
    public Result<WxCompany> getCompanyDetailById(Long companyId){
        WxCompany company = wxCompanyService.getById(companyId);
        QueryWrapper<WxCompanyDetailImg> detailImgQueryWrapper = new QueryWrapper<>();
        detailImgQueryWrapper.eq("company_id",company.getId());
        List<WxCompanyDetailImg> detailList = wxCompanyDetailImgService.list(detailImgQueryWrapper);
        QueryWrapper<WxPosition> positionQueryWrapper = new QueryWrapper<>();
        positionQueryWrapper.eq("company_id",company.getId());
        List<WxPosition> wxPositionList = wxPositionService.list(positionQueryWrapper);
        company.setWxPositionList(wxPositionList);
        company.setCompanyDetailImgs(detailList);
        log.info("获取公司明细；"+JSONObject.toJSONString(company));
        return Result.buildOk(company);
    }

    /**
     * 根据公司名称查询公司详情
     * @param companyName
     * @return
     */
    @GetMapping(value = "/getCompanyDetailByName")
    public Result<List<WxCompany>> getCompanyDetailByName(String companyName){
        QueryWrapper<WxCompany> wxCompanyQueryWrapper = new QueryWrapper<>();
        wxCompanyQueryWrapper.like("company_name",companyName);
        List<WxCompany> companyList = wxCompanyService.list(wxCompanyQueryWrapper);
        log.info("获取公司；"+JSONObject.toJSONString(companyList));
        return Result.buildOk(companyList);
    }

    /**
     * 分页获取公司
     * @param page
     * @param wxCompany
     * @return
     */
    @GetMapping(value = "/page/getCompanyPages")
    public Result<Page<WxCompany>> getCompanyPages(Page page, WxCompany wxCompany){
        LambdaQueryWrapper<WxCompany> wrappers = new QueryWrapper(wxCompany).lambda();
        if(wxCompany.getCompanyName() != null){
            wrappers.eq(WxCompany::getCompanyName, wxCompany.getCompanyName());
        }
        if(wxCompany.getRegion() != null ){
            wrappers.eq(WxCompany::getPosition, wxCompany.getRegion());
        }

        Page page1 = wxCompanyService.page(page,wrappers);
        log.info("企业列表："+ JSONObject.toJSONString(page1));
        return Result.buildOk(page1);
    }

    /**
     * 分页获取用户信息
     * @param page
     * @param wxUser
     * @return
     */
    @GetMapping(value = "/getWxUerPage")
    public Result<IPage<WxUser>> getWxUerPage(Page page,WxUser wxUser){
        LambdaQueryWrapper<WxUser> wrappers = new QueryWrapper(wxUser).lambda();
        wrappers.eq(WxUser::getDelFlag, 0);
        Page data = wxUserService.pageMaps(page, wrappers);
        log.info("分页获取的对象："+JSONObject.toJSONString(data));
        return Result.buildOk(data);
    }

    /**
     * 获取用户列表
     * @param wxUser
     * @return
     */
    @GetMapping(value = "/getWxUerList")
    public Result<List<WxUser>> getWxUerList(WxUser wxUser){
        LambdaQueryWrapper<WxUser> wrappers = new QueryWrapper(wxUser).lambda();
        wrappers.eq(WxUser::getDelFlag, 0);
        List<WxUser> list = wxUserService.list(wrappers);
        return Result.buildOk(list);
    }
}
