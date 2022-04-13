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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    private WxRecruitInfoService wxRecruitInfoService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxCompanyDetailImgService wxCompanyDetailImgService;
    @Autowired
    private WxPositionService wxPositionService;
    @Autowired
    private WxRecommendService wxRecommendService;
    @Autowired
    private WxCompanyService wxCompanyService;

    /**
     * 获取首页轮播图
     * @return
     */
    @GetMapping(value = "/getSwiperImgList")
    public Result<List<WxSwiperImg>> getSwiperImgList(){
        return Result.buildOk(wxSwiperImgService.list());
    }

    /**
     * 获取公司轮播图片
     * @return
     */
    @GetMapping(value = "/getCompanySwiperImgList")
    public Result<List<WxCompanyDetailImg>> getCompanySwiperImgList(){
        return Result.buildOk(wxCompanyDetailImgService.list());
    }

    /**
     * 获取导航轮播图
     * @return
     */
    @GetMapping(value = "/getNavigationImgList")
    public Result<List<WxNavigationImg>> getNavigationImgList(){
        return Result.buildOk(wxNavigationImgService.list());
    }

    /**
     * 获取公司列表
     * @return
     */
    @GetMapping(value = "/getCompanyList")
    public Result<List<WxCompany>> getCompanyList(){
        QueryWrapper<WxCompany> companyWrapper = new QueryWrapper<>();
        companyWrapper.eq("del_flag",0);
        List<WxCompany> list = wxCompanyService.list(companyWrapper);
        log.info("公司列表："+JSONObject.toJSONString(list));
        return Result.buildOk(wxCompanyService.list(companyWrapper));
    }

    /**
     * 删除公司
     * @param id
     * @return
     */
    @DeleteMapping("/delCompanyById/{id}")
    public Result<Boolean> delCompanyById(@PathVariable("id") long id){
        QueryWrapper<WxCompany> companyWrapper = new QueryWrapper<>();
        companyWrapper.eq("id",id);
        WxCompany company = wxCompanyService.getOne(companyWrapper);
        if (company == null || company.getId() ==null){
            return Result.build(310,"公司不存在");
        }
        return Result.buildOk(wxCompanyService.removeById(id));
    }

    /**
     * 新增公司
     * @param wxCompany
     * @return
     */
    @PostMapping("/addCompany")
    public Result addCompany(@RequestBody WxCompany wxCompany){
        if(wxCompany == null || wxCompany.getCompanyName()==null || wxCompany.getIntroduce()==null){
            return Result.build(301,"业务参数不能为空");
        }
        QueryWrapper<WxCompany> companyWrapper = new QueryWrapper<>();
        companyWrapper.eq("company_name",wxCompany.getCompanyName());
        List<WxCompany> companyList = wxCompanyService.list(companyWrapper);

        if(companyList !=null && companyList.size()>0){
            return Result.build(311,"公司名称不允许重复");
        }
        wxCompany.setCompanyCode("2001");
        wxCompany.setDelFlag(0);
        log.info("添加公司对象："+JSONObject.toJSONString(wxCompany));
        wxCompanyService.save(wxCompany);
        log.info("保持成功：");
       return Result.buildOk();
    }

    /**
     * 更新公司信息
     * @param wxCompany
     * @return
     */
    @PutMapping("/updateCompany")
    public Result updateCompany(@RequestBody WxCompany wxCompany){
        if(wxCompany == null || wxCompany.getId()==null ||  wxCompany.getCompanyName()==null || wxCompany.getIntroduce()==null){
            return Result.build(301,"业务参数不能为空");
        }
        WxCompany company = wxCompanyService.getById(wxCompany.getId());
        if (company == null || company.getId()== null){
            return Result.build(310,"公司不存在");
        }
        QueryWrapper<WxCompany> companyWrapper = new QueryWrapper<>();
        companyWrapper.eq("company_name",wxCompany.getCompanyName());
        List<WxCompany> companyList = wxCompanyService.list(companyWrapper);
        if(companyList !=null || companyList.size()>0){
            return Result.build(311,"公司名称不允许重复");
        }
        wxCompany.setUpdatime(LocalDateTime.now());
        wxCompany.setUpdator("hyb");
        return Result.buildOk(wxCompanyService.saveOrUpdate(wxCompany));
    }
    /**
     * 获取推荐企业招聘列表
     * @return
     */
    @GetMapping(value = "/getRecommendCompanyList")
    public Result<List<WxCompanyDto>> getRecommendCompanyList(){
        List<WxCompanyDto> dtoList = new ArrayList<>();
        WxCompanyDto dto = new WxCompanyDto();
        dto.setType("推荐企业");
        List<WxRecruitInfo> companyList = wxRecruitInfoService.list();
        log.info("企业列表："+ JSONObject.toJSONString(companyList));
        dto.setCompanyList(companyList);
        dtoList.add(dto);
        return Result.buildOk(dtoList);
    }
    /**
     * 获取所有公司招聘信息列表
     */
    @GetMapping(value = "/getAllRecruitList")
    public Result<List<WxRecruitInfo>> getAllRecruitList(){
        List<WxRecruitInfo> companyList = wxRecruitInfoService.list();
        return Result.buildOk(companyList);
    }

    /**
     * 根据ID获取公司招聘详情
     * @param companyId
     * @return
     */
    @GetMapping(value = "/getRecruitInfoById")
    public Result<WxRecruitInfo> getRecruitInfoById(Long companyId){
        WxRecruitInfo company = wxRecruitInfoService.getById(companyId);
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
     * 根据公司名称查询公司招聘详情
     * @param companyName
     * @return
     */
    @GetMapping(value = "/getRecruitInfoByName")
    public Result<List<WxRecruitInfo>> getRecruitInfoByName(String companyName){
        QueryWrapper<WxRecruitInfo> wxCompanyQueryWrapper = new QueryWrapper<>();
        wxCompanyQueryWrapper.like("company_name",companyName);
        List<WxRecruitInfo> companyList = wxRecruitInfoService.list(wxCompanyQueryWrapper);
        log.info("获取公司；"+JSONObject.toJSONString(companyList));
        return Result.buildOk(companyList);
    }

    /**
     * 分页推荐企业招聘信息
     * @param page
     * @param wxRecruitInfo
     * @return
     */
    @GetMapping(value = "/page/getRecruitPages")
    public Result<Page<WxRecruitInfo>> getRecruitPages(Page page, WxRecruitInfo wxRecruitInfo){
        LambdaQueryWrapper<WxRecruitInfo> wrappers = new QueryWrapper(wxRecruitInfo).lambda();
        if(wxRecruitInfo.getCompanyName() != null){
            wrappers.eq(WxRecruitInfo::getCompanyName, wxRecruitInfo.getCompanyName());
        }
        if(wxRecruitInfo.getRegion() != null ){
            wrappers.eq(WxRecruitInfo::getPosition, wxRecruitInfo.getRegion());
        }
        Page page1 = wxRecruitInfoService.page(page,wrappers);
        log.info("企业列表："+ JSONObject.toJSONString(page1));
        return Result.buildOk(page1);
    }

    /**
     * 删除公司招聘信息
     * @param id
     * @return
     */
    @DeleteMapping("/delRecruitById/{id}")
    public Result<Boolean> delCompanyById(@PathVariable("id") Long id){
        return Result.buildOk(wxRecruitInfoService.removeById(id));
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

    /**
     * 获取报备列表
     * @param wxRecommend
     * @return
     */
    @GetMapping(value = "/getRecommendList")
    public Result<List<WxRecommend>> getRecommendList(WxRecommend wxRecommend){
        return Result.buildOk(wxRecommendService.list());
    }

}
