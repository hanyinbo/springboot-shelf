package com.aison.controller;

import com.aison.common.Result;
import com.aison.dto.WxCompanyDto;
import com.aison.dto.WxCompanyOfPageDto;
import com.aison.dto.WxRecruitDto;
import com.aison.dto.WxRecruitQueryDto;
import com.aison.entity.*;
import com.aison.service.*;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private WxRecruitPositionService wxRecruitPositionService;
    @Autowired
    private WxRecommentService wxRecommentService;
    @Autowired
    private WxCompanyService wxCompanyService;
    @Autowired
    private WxPositionService wxPositionService;

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
     * 获取公司详情
     * @return
     */
    @GetMapping(value = "/getCompanyInfo/{id}")
    public Result getCompanyInfo(@PathVariable("id") Long id){
        QueryWrapper<WxCompany> companyQuery = new QueryWrapper<>();
        companyQuery.eq("id",id);
        WxCompany company = wxCompanyService.getOne(companyQuery);
        if (company == null || company.getId() ==null){
            return Result.build(310,"公司不存在");
        }
        return Result.buildOk(company);
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
        companyWrapper.ne("id",wxCompany.getId());
        List<WxCompany> companyList = wxCompanyService.list(companyWrapper);
        if(companyList !=null && companyList.size()>0){
            return Result.build(311,"公司名称不允许重复");
        }
        return Result.buildOk(wxCompanyService.saveOrUpdate(wxCompany));
    }


    /**
     * 分页获取公司列表
     * @param page
     * @return
     */
    @GetMapping("/getPageOfCompany")
    public Result<List<WxCompany>> getPageOfCompany(WxCompanyOfPageDto page){
        IPage<WxCompany> pageOfCompany = wxCompanyService.getPageOfCompany(page);
        log.info("分页获取公司："+JSONObject.toJSONString(pageOfCompany));
        return Result.buildOk(pageOfCompany.getRecords());
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
     * 获取报备列表
     * @param
     * @return
     */
    @GetMapping(value = "/getRecommentList")
    public Result<List<WxRecomment>> getRecommentList(){
        return Result.buildOk(wxRecommentService.list());
    }

    /**
     * 获取推荐详情
     * @param id
     * @return
     */
    @GetMapping(value = "/getRecommentById/{id}")
    public Result<WxRecomment> getRecommentById(@PathVariable("id") Long id){
        WxRecomment one = wxRecommentService.getById(id);
        if(one ==null || one.getId() == null){
            return Result.build(310,"未有报备信息");
        }
        return Result.buildOk(one);
    }

    /**
     * 删除报备
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delRecomment/{id}")
    public Result<Boolean> delRecomment(@PathVariable("id") Long id){
        WxRecomment one = wxRecommentService.getById(id);
        if(one ==null || one.getId() == null){
            return Result.build(310,"未有报备信息");
        }
        return Result.buildOk(wxRecommentService.removeById(id));
    }

    /**
     * 修改报备
     * @param wxRecomment
     * @return
     */
    @PutMapping(value = "/updateRecomment")
    public Result<Boolean> updateRecomment(@RequestBody WxRecomment wxRecomment){
        log.info("修改招聘信息："+JSONObject.toJSONString(wxRecomment));
        if(wxRecomment == null || wxRecomment.getId()==null || wxRecomment.getCustomName()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        WxRecomment one = wxRecommentService.getById(wxRecomment.getId());
        if(one== null || one.getId()==null){
            return Result.build(310,"未设置报备信息");
        }
        return Result.buildOk(wxRecommentService.updateById(wxRecomment));
    }

    /**
     * 分页获取报备信息
     * @param page
     * @param wxRecomment
     * @return
     */
    @GetMapping(value = "/getRecommentOfPage")
    public Result<List<WxRecomment>> getRecommentOfPage(Page page, WxRecomment wxRecomment){
        QueryWrapper<WxRecomment> queryWrapper = new QueryWrapper<>();
        if( StringUtils.isNotEmpty(wxRecomment.getCustomName())){
            queryWrapper.like("custom_name",wxRecomment.getCustomName());
        }
        if(StringUtils.isNotEmpty(wxRecomment.getTelephone())){
            queryWrapper.like("telephone", wxRecomment.getTelephone());
        }
        if(wxRecomment.getGender() != null ){
            queryWrapper.eq("gender", wxRecomment.getGender());
        }
        if(StringUtils.isNotEmpty(wxRecomment.getIntentionCompany())){
            queryWrapper.like("intention_company", wxRecomment.getIntentionCompany());
        }
        if(wxRecomment.getStatus() !=null){
            queryWrapper.eq("status", wxRecomment.getStatus());
        }
        if(StringUtils.isNotEmpty(wxRecomment.getRecommentName())){
            queryWrapper.like("recomment_name", wxRecomment.getRecommentName());
        }
        Page page1 = wxRecommentService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
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
     * 获取所有公司招聘信息列表
     */
    @GetMapping(value = "/getAllRecruitList")
    public Result<List<WxRecruitInfo>> getAllRecruitList(){
        List<WxRecruitInfo> companyList = wxRecruitInfoService.list();
        return Result.buildOk(companyList);
    }

    /**
     * 根据ID获取公司招聘详情
     * @param
     * @return
     */
    @GetMapping(value = "/getRecruitInfoById")
    public Result<WxRecruitInfo> getRecruitInfoById(Long id){
        WxRecruitInfo wxRecruitInfo = wxRecruitInfoService.getById(id);
        QueryWrapper<WxCompanyDetailImg> detailImgQueryWrapper = new QueryWrapper<>();
        detailImgQueryWrapper.eq("company_id",wxRecruitInfo.getCompanyId());
        List<WxCompanyDetailImg> detailList = wxCompanyDetailImgService.list(detailImgQueryWrapper);
        QueryWrapper<WxRecruitPosition> positionQueryWrapper = new QueryWrapper<>();
        positionQueryWrapper.eq("recruit_id",wxRecruitInfo.getId());
        List<WxRecruitPosition> wxRecruitPositionList = wxRecruitPositionService.list(positionQueryWrapper);
        wxRecruitInfo.setWxRecruitPositionList(wxRecruitPositionList);
        wxRecruitInfo.setCompanyDetailImgs(detailList);
        log.info("获取公司明细；"+JSONObject.toJSONString(wxRecruitInfo));
        return Result.buildOk(wxRecruitInfo);
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
//        if(wxRecruitInfo.getRegion() != null ){
//            wrappers.eq(WxRecruitInfo::getPosition, wxRecruitInfo.getRegion());
//        }
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
     * 新增公司招聘
     * @param wxRecruitInfo
     * @return
     */
    @PostMapping("/addRecruitInfo")
    public Result<Boolean> addRecruitInfo(@RequestBody WxRecruitInfo wxRecruitInfo){
        List<WxRecruitPosition> recruitPositionList = new ArrayList<>();
        if(wxRecruitInfo == null || wxRecruitInfo.getCompanyName()==null || wxRecruitInfo.getWxPositionList()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        String positionName = null;
        for(String name: wxRecruitInfo.getWxPositionList()){
            if(StringUtils.isNotEmpty(positionName)){
                positionName=name;
            }else {
                positionName=positionName+"/"+name;
            }
        }
        wxRecruitInfo.setPosition(positionName);
        boolean b = wxRecruitInfoService.saveOrUpdate(wxRecruitInfo);
        if(b){
            log.info("招聘表："+wxRecruitInfo.getId());
            QueryWrapper<WxPosition> wrapper = new QueryWrapper();
            wrapper.in("position_name",wxRecruitInfo.getWxPositionList());
            List<WxPosition> wxPositionList = wxPositionService.list(wrapper);
            for(WxPosition wxPosition:wxPositionList){
                WxRecruitPosition recruitPosition = new WxRecruitPosition();
                recruitPosition.setPositionName(wxPosition.getPositionName());
                recruitPosition.setRecruitId(wxRecruitInfo.getId());
                recruitPositionList.add(recruitPosition);
            }
        }else {
            return Result.build(302,"保存失败");
        }
        return Result.buildOk(wxRecruitPositionService.saveBatch(recruitPositionList));
    }

    /**
     * 删除公司招聘信息
     * @param id
     * @return
     */
    @DeleteMapping("/delRecruit/{id}")
    public Result<Boolean> delRecruitInfo(@PathVariable("id") Long id){
        QueryWrapper<WxRecruitInfo> recruitInfoWrapper =  new QueryWrapper<>();
        recruitInfoWrapper.eq("id",id);
        WxRecruitInfo one = wxRecruitInfoService.getOne(recruitInfoWrapper);
        if(one==null || one.getId()==null){
            return Result.build(310,"该公司未设置招聘");
        }
        QueryWrapper<WxRecruitPosition> wxRecruitPositionWrapper = new QueryWrapper<>();
        wxRecruitPositionWrapper.eq("recruit_id",one.getId());

        List<Long> idList = wxRecruitPositionService.list(wxRecruitPositionWrapper).stream().map(WxRecruitPosition::getId).collect(Collectors.toList());
        boolean b = wxRecruitPositionService.removeByIds(idList);
        if(!b){
            return Result.build(312,"删除招聘信息失败");
        }
        return Result.buildOk( wxRecruitInfoService.removeById(id));
    }

    /**
     * 修改招聘信息
     * @param dto
     * @return
     */
    @PutMapping("/updateRecruitInfo")
    public Result<Boolean> updateRecruitInfo(@RequestBody WxRecruitDto dto){
        log.info("修改招聘信息："+JSONObject.toJSONString(dto));
        if(dto == null || dto.getId()==null || dto.getWxPositionList()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        QueryWrapper<WxRecruitInfo> recruitQueryWrapper = new QueryWrapper<>();
        recruitQueryWrapper.eq("id",dto.getId());
        WxRecruitInfo one = wxRecruitInfoService.getOne(recruitQueryWrapper);
        if(one==null || one.getId()==null){
            return Result.build(301,"招聘信息不存在");
        }
        one.setCompanyId(dto.getCompanyId());
        one.setCompanyName(dto.getCompanyName());
        one.setJobRequire(dto.getJobRequire());
        one.setWelfare(dto.getWelfare());
        one.setInterviewAddress(dto.getInterviewAddress());
        one.setMoney(dto.getMoney());
        one.setNumber(dto.getNumber());
        String positionName = null;
        for(String name: dto.getWxPositionList()){
            if(StringUtils.isNotEmpty(positionName)){
                positionName=name;
            }else {
                positionName=positionName+"/"+name;
            }
        }
        one.setPosition(positionName);
        wxRecruitInfoService.saveOrUpdate(one);
        QueryWrapper<WxRecruitPosition> recruitPositionQueryWrapper = new QueryWrapper<>();
        recruitPositionQueryWrapper.eq("recruit_id",dto.getId());
        List<WxRecruitPosition> recruitPositionList = wxRecruitPositionService.list(recruitPositionQueryWrapper);
        if(recruitPositionList !=null && recruitPositionList.size()>0){
            List<WxRecruitPosition> wxRecruitPositionList = new ArrayList<>();
            List<Long> idList = recruitPositionList.stream().map(WxRecruitPosition::getId).collect(Collectors.toList());
            wxRecruitPositionService.removeByIds(idList);
            QueryWrapper<WxPosition> queryWrapper = new QueryWrapper();
            queryWrapper.in("position_name",dto.getWxPositionList());
            List<WxPosition> wxPositionList = wxPositionService.list(queryWrapper);
            for(WxPosition wxPosition:wxPositionList){
                WxRecruitPosition recruitPosition = new WxRecruitPosition();
                recruitPosition.setPositionName(wxPosition.getPositionName());
                recruitPosition.setRecruitId(dto.getId());
                wxRecruitPositionList.add(recruitPosition);
            }
            wxRecruitPositionService.saveOrUpdateBatch(wxRecruitPositionList);
        }
        return Result.buildOk(Boolean.TRUE);
    }

    /**
     * 获取公司招聘详情
     * @param id
     * @return
     */
    @GetMapping("/getRecruitData/{id}")
    public Result<WxRecruitDto> getRecruitData(@PathVariable("id") Long id){
        WxRecruitInfo one = wxRecruitInfoService.getById(id);
        log.info("getone:"+JSONObject.toJSONString(one));
        if(one==null || one.getId()==null){
            return Result.build(310,"该公司未设置招聘");
        }
        WxRecruitDto dto = new WxRecruitDto();
        dto.setId(one.getId());
        dto.setInterviewAddress(one.getInterviewAddress());
        dto.setJobRequire(one.getJobRequire());
        dto.setMoney(one.getMoney());
        dto.setNumber(one.getNumber());
        dto.setWelfare(one.getWelfare());
        WxCompany company = wxCompanyService.getById(one.getCompanyId());
        dto.setWxCompany(company);
        dto.setCompanyId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        QueryWrapper<WxRecruitPosition> recruitWrapper = new QueryWrapper<>();
        recruitWrapper.eq("recruit_id",id);
        List<String> recruitPositionList = wxRecruitPositionService.list(recruitWrapper).stream().map(WxRecruitPosition::getPositionName).collect(Collectors.toList());
        dto.setWxPositionList(recruitPositionList);
        log.info("招聘详情:"+JSONObject.toJSONString(dto));
        return Result.buildOk(dto);
    }

    /**
     * 分页获取招聘信息
     * @return
     */
    @GetMapping(value = "/getRecruitOfPage")
    public Result<List<WxRecruitQueryDto>> getRecruitOfPage(Page page,WxRecruitQueryDto dto){
        IPage<WxRecruitQueryDto> recruitOfPage = wxRecruitInfoService.getRecruitOfPage(page, dto);
        return Result.buildOk(recruitOfPage.getRecords());
    }

    /**
     * 分页获取用户信息
     * @param page
     * @param wxUser
     * @return
     */
    @GetMapping(value = "/getWxUerPage")
    public Result<List<WxUser>> getWxUerPage(Page page,WxUser wxUser){
        log.info("分页参数："+page.getSize()+"**"+JSONObject.toJSONString(wxUser));
        IPage<WxUser> userOfPage = wxUserService.getUserOfPage(page, wxUser);
        return Result.buildOk(userOfPage.getRecords());
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
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/delUserById/{id}")
    public Result<Boolean> delUserById(@PathVariable("id") long id){
        QueryWrapper<WxUser> userWrapper = new QueryWrapper<>();
        userWrapper.eq("id",id);
        WxUser one = wxUserService.getOne(userWrapper);

        if (one == null || one.getId() ==null){
            return Result.build(310,"用户不存在");
        }
        return Result.buildOk(wxUserService.removeById(id));
    }

    /**
     * 更新用户信息
     * @param wxUser
     * @return
     */
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody WxUser wxUser){
        if(wxUser == null || wxUser.getId()==null){
            return Result.build(301,"业务参数不能为空");
        }
        WxUser user = wxUserService.getById(wxUser.getId());
        if (user == null || user.getId()== null){
            return Result.build(310,"用户不存在");
        }
        QueryWrapper<WxUser> userWrapper = new QueryWrapper<>();
        userWrapper.eq("nick_name",wxUser.getNickName());
        userWrapper.ne("id",wxUser.getId());
        log.info("编辑用户："+wxUser.getId());
        List<WxUser> userList = wxUserService.list(userWrapper);
        if(userList !=null && userList.size()>0){
            return Result.build(311,"用户昵称不允许重复");
        }
        return Result.buildOk(wxUserService.saveOrUpdate(wxUser));
    }
    /**
     * 获取用户详情
     * @return
     */
    @GetMapping(value = "/getUserInfo/{id}")
    public Result getUserInfo(@PathVariable("id") Long id){
        QueryWrapper<WxUser> userQuery = new QueryWrapper<>();
        userQuery.eq("id",id);
        WxUser user = wxUserService.getOne(userQuery);
        if (user == null || user.getId() ==null){
            return Result.build(310,"用户不存在");
        }
        return Result.buildOk(user);
    }

    /**
     * 添加用户
     * @param wxUser
     * @return
     */
    @PostMapping("/addUser")
    public Result addUser(@RequestBody WxUser wxUser){
        if(wxUser == null || wxUser.getNickName()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        QueryWrapper<WxUser> userWrapper = new QueryWrapper<>();
        userWrapper.eq("nick_name",wxUser.getNickName());
        List<WxUser> userList = wxUserService.list(userWrapper);
        if(userList !=null && userList.size()>0){
            return Result.build(311,"用户昵称不允许重复");
        }
        wxUser.setDelFlag(0);
        log.info("添加用户对象："+JSONObject.toJSONString(wxUser));
        return Result.buildOk(wxUserService.save(wxUser));
    }

    /**
     * 获取职位列表
     * @return
     */
    @GetMapping(value = "/getPositionList")
    public Result<List<WxPosition>> getPositionList(WxPosition wxPosition){
        LambdaQueryWrapper<WxPosition> wrappers = new QueryWrapper().lambda();
        wrappers.eq(WxPosition::getDelFlag, 0);
        return Result.buildOk(wxPositionService.list(wrappers));
    }

    /**
     * 获取职位详情
     * @return
     */
    @GetMapping(value = "/getPositionInfo/{id}")
    public Result getPositionInfo(@PathVariable("id") Long id){
        QueryWrapper<WxPosition> positionQuery = new QueryWrapper<>();
        positionQuery.eq("id",id);
        WxPosition wxPosition = wxPositionService.getOne(positionQuery);
        if (wxPosition == null || wxPosition.getId() ==null){
            return Result.build(310,"职位不存在");
        }
        return Result.buildOk(wxPosition);
    }

    /**
     * 删除职位信息
     * @param id
     * @return
     */
    @DeleteMapping("/delPositionById/{id}")
    public Result<Boolean> delPositionById(@PathVariable("id") long id){
        QueryWrapper<WxPosition> positionWrapper = new QueryWrapper<>();
        positionWrapper.eq("id",id);
        WxPosition one = wxPositionService.getOne(positionWrapper);
        if (one == null || one.getId() ==null){
            return Result.build(310,"职位不存在");
        }
        return Result.buildOk(wxPositionService.removeById(id));
    }

    /**
     * 添加职位信息
     * @param wxPosition
     * @return
     */
    @PostMapping("/addPosition")
    public Result addPosition(@RequestBody WxPosition wxPosition){
        if(wxPosition == null || wxPosition.getPositionName()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        QueryWrapper<WxPosition> positionWrapper = new QueryWrapper<>();
        positionWrapper.eq("position_name",wxPosition.getPositionName());
        List<WxPosition> positionList = wxPositionService.list(positionWrapper);
        if(positionList !=null && positionList.size()>0){
            return Result.build(311,"职位名称不允许重复");
        }
        wxPosition.setDelFlag(0);
        log.info("添加职位对象："+JSONObject.toJSONString(wxPosition));
        return Result.buildOk(wxPositionService.save(wxPosition));
    }

    /**
     * 更新职位信息
     * @param wxPosition
     * @return
     */
    @PutMapping("/updatePosition")
    public Result updateUser(@RequestBody WxPosition wxPosition){
        if(wxPosition == null || wxPosition.getId()==null){
            return Result.build(301,"业务参数不能为空");
        }
        WxPosition wxPosition1 = wxPositionService.getById(wxPosition.getId());
        if (wxPosition1 == null || wxPosition1.getId()== null){
            return Result.build(310,"职位不存在");
        }
        QueryWrapper<WxPosition> positionWrapper = new QueryWrapper<>();
        positionWrapper.eq("position_name",wxPosition.getPositionName());
        positionWrapper.ne("id",wxPosition.getId());
        log.info("编辑职位"+wxPosition.getId());
        List<WxPosition> positionList = wxPositionService.list(positionWrapper);
        if(positionList !=null && positionList.size()>0){
            return Result.build(311,"职位名称不允许重复");
        }
        return Result.buildOk(wxPositionService.saveOrUpdate(wxPosition));
    }

    /**
     * 分页获取职位信息
     * @param page
     * @param wxPosition
     * @return
     */
    @GetMapping(value = "/getWxPositionPage")
    public Result<List<WxPosition>> getWxPositionPage(Page page,WxPosition wxPosition){
        log.info("分页参数："+page.getSize()+"**"+JSONObject.toJSONString(wxPosition));
        IPage<WxPosition> positionPage = wxPositionService.getPageOfPosition(page, wxPosition);
        return Result.buildOk(positionPage.getRecords());
    }
}
