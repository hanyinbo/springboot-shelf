package com.aison.controller;

import cn.hutool.core.collection.CollUtil;
import com.aison.common.Result;
import com.aison.dto.WxCompanyDto;
import com.aison.dto.WxCompanyOfPageDto;
import com.aison.dto.WxRecruitDto;
import com.aison.dto.WxRecruitQueryDto;
import com.aison.entity.*;
import com.aison.service.*;
import com.aison.utils.MinioService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private WxCustomService wxCustomService;
    @Autowired
    private WxBrokerageService wxBrokerageService;
    @Autowired
    private MinioService minioService;
    @Autowired
    private NzUploadFileService nzUploadFileService;
    @Autowired
    private WxCompanyFileService wxCompanyFileService;

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
        List<NzUploadFile> companyImgList = nzUploadFileService.getCompanyImgList(id);
        if(CollUtil.isNotEmpty(companyImgList)){
            List<Long> imgIgs = companyImgList.stream().map(NzUploadFile::getId).collect(Collectors.toList());
            company.setCompanyImgIdList(imgIgs);
        }
        return Result.buildOk(company);
    }

    /**
     * 获取公司图片
     * @param id
     * @return
     */
    @GetMapping(value = "/getCompanyImgList/{id}")
    public Result getCompanyImgList(@PathVariable("id") Long id){
        List<NzUploadFile> companyImgList = nzUploadFileService.getCompanyImgList(id);
        return Result.buildOk(companyImgList);
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
        if(wxCompany == null || StringUtils.isEmpty(wxCompany.getCompanyName()) || StringUtils.isEmpty(wxCompany.getIntroduce())){
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
        boolean saveCompany = wxCompanyService.save(wxCompany);
        if(!saveCompany){
            nzUploadFileService.removeByIds(wxCompany.getCompanyImgIdList());
            return Result.build(340,"新增公司失败");
        }
        List<NzUploadFile> uploadFileList = nzUploadFileService.listByIds(wxCompany.getCompanyImgIdList());

        List<WxCompanyFile> wxCompanyFileList = new ArrayList<>();
        for(Long imgId: wxCompany.getCompanyImgIdList()){
            WxCompanyFile companyFile = new WxCompanyFile();
            companyFile.setCompanyId(wxCompany.getId());
            uploadFileList.stream().filter(img->img.getId().longValue() == imgId).forEach(companyImg->{
                companyFile.setFileId(imgId);
            });
            wxCompanyFileList.add(companyFile);
        }
        wxCompanyFileService.saveOrUpdateBatch(wxCompanyFileList);
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
     * 新增推荐
     * @return
     */
    @PostMapping(value = "/addRecomment")
    public Result<Boolean> addRecomment(@RequestBody WxRecomment wxRecomment){
        log.info("新增报备信息："+JSONObject.toJSONString(wxRecomment));
        if(wxRecomment == null || wxRecomment.getCompanyId()==null || wxRecomment.getCustomName()==null || wxRecomment.getRecommentId()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        WxCustom custom = new WxCustom();
        custom.setCusName(wxRecomment.getCustomName());
        custom.setDelFlag(0);
        custom.setGender(wxRecomment.getGender());
        custom.setTelPhone(wxRecomment.getTelephone());
        wxCustomService.save(custom);
        wxRecomment.setCustomId(custom.getId());
        wxRecomment.setStatus(0);
        return Result.buildOk(wxRecommentService.save(wxRecomment));
    }

    /**
     * 分页获取全部报备信息
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
     * 分页获取待面试报备信息
     * @param page
     * @param wxRecomment
     * @return
     */
    @GetMapping(value = "/getRecommentNotInterview")
    public Result<List<WxRecomment>> getRecommentNotInterview(Page page, WxRecomment wxRecomment){
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
        queryWrapper.eq("status", 0);
        if(StringUtils.isNotEmpty(wxRecomment.getRecommentName())){
            queryWrapper.like("recomment_name", wxRecomment.getRecommentName());
        }
        Page page1 = wxRecommentService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }

    /**
     * 分页获取已面试报备信息
     * @param page
     * @param wxRecomment
     * @return
     */
    @GetMapping(value = "/getRecommentOkInterview")
    public Result<List<WxRecomment>> getRecommentOkInterview(Page page,WxRecomment wxRecomment){
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
        queryWrapper.eq("status", 1);
        if(StringUtils.isNotEmpty(wxRecomment.getRecommentName())){
            queryWrapper.like("recomment_name", wxRecomment.getRecommentName());
        }
        Page page1 = wxRecommentService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }

    /**
     * 分页获取已入职报备信息
     * @param page
     * @param wxRecomment
     * @return
     */
    @GetMapping(value = "/getRecommentInduction")
    public Result<List<WxRecomment>> getRecommentInduction(Page page,WxRecomment wxRecomment){
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
        queryWrapper.eq("status", 2);
        if(StringUtils.isNotEmpty(wxRecomment.getRecommentName())){
            queryWrapper.like("recomment_name", wxRecomment.getRecommentName());
        }
        Page page1 = wxRecommentService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }
    /**
     * 分页获取已离职报备信息
     * @param page
     * @param wxRecomment
     * @return
     */
    @GetMapping(value = "/getRecommentLeaveOffice")
    public Result<List<WxRecomment>> getRecommentLeaveOffice(Page page,WxRecomment wxRecomment){
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
        queryWrapper.eq("status", 3);
        if(StringUtils.isNotEmpty(wxRecomment.getRecommentName())){
            queryWrapper.like("recomment_name", wxRecomment.getRecommentName());
        }
        Page page1 = wxRecommentService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }

    /**
     * 变更面试状态
     * @param ids
     * @return
     */
    @PutMapping(value = "/changeInterviewStatus")
    public Result<Boolean> changeInterviewStatus(@RequestBody List<Long> ids){
        List<WxBrokerage> wxBrokerageList = new ArrayList<>();
        List<WxRecomment> recommentList = wxRecommentService.listByIds(ids);
        List<WxRecruitInfo> recruitInfoList = wxRecruitInfoService.list();
        if(recommentList ==null || recommentList.size()==0){
            return Result.build(310,"报备信息不存在");
        }
        recommentList.forEach(recome ->{
            recome.setStatus(1);
            recome.setInterviewTime(LocalDateTime.now());
            WxBrokerage brokerage = new WxBrokerage();
            brokerage.setCusName(recome.getCustomName());
            brokerage.setCusId(recome.getCustomId());
            brokerage.setCompanyId(recome.getCompanyId());
            brokerage.setCompanyName(recome.getIntentionCompany());
            brokerage.setInterviewTime(LocalDateTime.now());

            recruitInfoList.forEach(recruit->{
               log.info("recruit："+recruit.getCompanyId());
               log.info("recome:"+recome.getCompanyId());
                if(recruit.getCompanyId().longValue()==recome.getCompanyId().longValue() && recruit.getId().longValue()==recome.getRecruitId().longValue()){
                    log.info("佣金："+recruit.getMoney());
                    brokerage.setBrokerage(recruit.getMoney());
                }
            });
            brokerage.setIsSettle(false);
            brokerage.setRecommentId(recome.getRecommentId());
            brokerage.setRecommentName(recome.getRecommentName());
            brokerage.setStatus(recome.getStatus());
            wxBrokerageList.add(brokerage);
        });
         wxBrokerageService.saveBatch(wxBrokerageList);
        return Result.buildOk(wxRecommentService.updateBatchById(recommentList));
    }

    /**
     * 变更入职状态
     * @param ids
     * @retur
     */
    @PutMapping(value = "/changeInductionStatus")
    public Result<Boolean> changeInductionStatus(@RequestBody List<Long> ids){
        List<WxRecomment> recommentList = wxRecommentService.listByIds(ids);
        if(recommentList ==null || recommentList.size()==0){
            return Result.build(310,"报备信息不存在");
        }
        recommentList.forEach(recome ->{
            recome.setStatus(2);
            recome.setInductionTime(LocalDateTime.now());
        });
        return Result.buildOk(wxRecommentService.updateBatchById(recommentList));
    }

    /**
     * 变更离职状态
     * @param ids
     * @return
     */
    @PutMapping(value = "/changeLeaveOfficeStatus")
    public Result<Boolean> changeLeaveOfficeStatus(@RequestBody List<Long> ids){
        List<WxRecomment> recommentList = wxRecommentService.listByIds(ids);
        if(recommentList ==null || recommentList.size()==0){
            return Result.build(310,"报备信息不存在");
        }
        recommentList.forEach(recome ->{
            recome.setLeaveOfficeTime(LocalDateTime.now());
            recome.setStatus(3);
        });
        return Result.buildOk(wxRecommentService.updateBatchById(recommentList));
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
            if(StringUtils.isEmpty(positionName)){
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
                recruitPosition.setCompanyId(wxRecruitInfo.getCompanyId());
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
     * 获取推荐人列表
     * @return
     */
    @GetMapping(value = "/getRecommentUserList")
    public Result<List<WxUser>> getRecommentUserList(){
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        queryWrapper.eq("identity",1);
        List<WxUser> list = wxUserService.list(queryWrapper);
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
     * 根据公司ID获取招聘岗位
     * @return
     */
    @GetMapping(value = "/getRecruitPositionByCompanyId/{companyId}")
    public Result<List<WxRecruitPosition>> getRecruitPositionByCompanyId(@PathVariable("companyId") Long companyId){
       QueryWrapper<WxRecruitPosition> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("company_id",companyId);
       List<WxRecruitPosition> recruitPositionList = wxRecruitPositionService.list(queryWrapper);
       return Result.buildOk(recruitPositionList);
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
//    佣金

    /**
     * 获取佣金详情
     * @param id
     * @return
     */
    @GetMapping(value = "/getWxBrokerageInfo/{id}")
    public Result<WxBrokerage> getWxBrokerageInfo(@PathVariable("id") Long id){
        WxBrokerage one = wxBrokerageService.getById(id);
        if(one ==null || one.getId()==null){
            return Result.build(310,"未生成佣金");
        }
        return Result.buildOk(one);
    }

    /**
     * 根据推荐人获取所有佣金
     * @return
     */
    @GetMapping(value = "/getWxBrokerageListByRecId/{recId}")
    public Result<List<WxBrokerage>> getWxBrokerageListByRecId(@PathVariable("recId") Long recId){
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recomment_id",recId);
        List<WxBrokerage> wxBrokerageList = wxBrokerageService.list(queryWrapper);
        return Result.buildOk(wxBrokerageList);
    }

    /**
     * 根据推荐人获取未结佣佣金
     * @return
     */
    @GetMapping(value = "/getNotBrokerageListByRecId/{recId}")
    public Result<List<WxBrokerage>> getNotBrokerageListByRecId(@PathVariable("recId") Long recId){
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recomment_id",recId);
        queryWrapper.eq("is_settle",false);
        List<WxBrokerage> wxBrokerageList = wxBrokerageService.list(queryWrapper);
        return Result.buildOk(wxBrokerageList);
    }

    /**
     * 根据推荐人获取已结佣佣金
     * @return
     */
    @GetMapping(value = "/getOkBrokerageListByRecId/{recId}")
    public Result<List<WxBrokerage>> getOkBrokerageListByRecId(@PathVariable("recId") Long recId){
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recomment_id",recId);
        queryWrapper.eq("is_settle",true );
        List<WxBrokerage> wxBrokerageList = wxBrokerageService.list(queryWrapper);
        return Result.buildOk(wxBrokerageList);
    }
    /**
     * 获取佣金列表
     * @return
     */
    @GetMapping(value = "/getWxBrokerageList")
    public Result<List<WxBrokerage>> getWxBrokerageList(){
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("recomment_id");
        List<WxBrokerage> wxBrokerages = wxBrokerageService.list(queryWrapper);
        return Result.buildOk(wxBrokerages);
    }

    /**
     * 分页获取全部佣金
     * @param page
     * @param wxBrokerage
     * @return
     */
    @GetMapping(value = "/getAllBrokeragePage")
    public Result<List<WxBrokerage>> getAllBrokeragePage(Page page,WxBrokerage wxBrokerage){
        log.info("分页参数："+page.getSize()+"**"+JSONObject.toJSONString(wxBrokerage));
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        if(wxBrokerage.getCusName()!=null){
            queryWrapper.like("cus_name",wxBrokerage.getCusName());
        }
        if(wxBrokerage.getCusId() !=null){
            queryWrapper.eq("cus_id",wxBrokerage.getCusId());
        }
        if(wxBrokerage.getCompanyName() != null){
            queryWrapper.like("company_name",wxBrokerage.getCompanyName());
        }
        if(wxBrokerage.getCompanyId()!=null){
            queryWrapper.eq("company_id",wxBrokerage.getCompanyId());
        }
        if(wxBrokerage.getRecommentId()!=null){
            queryWrapper.eq("recomment_id",wxBrokerage.getRecommentId());
        }
        if(wxBrokerage.getRecommentName() !=null){
            queryWrapper.like("recomment_name",wxBrokerage.getRecommentName());
        }
        if(wxBrokerage.getIsSettle() !=null){
            queryWrapper.eq("is_settle",wxBrokerage.getIsSettle());
        }
        if(wxBrokerage.getStatus() != null){
            queryWrapper.eq("status",wxBrokerage.getStatus());
        }
        Page page1 = wxBrokerageService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }

    /**
     * 分页获取未结佣
     * @param page
     * @param wxBrokerage
     * @return
     */
    @GetMapping(value = "/getNotBrokeragePage")
    public Result<List<WxBrokerage>> getNotBrokeragePage(Page page,WxBrokerage wxBrokerage){
        log.info("分页参数："+page.getSize()+"**"+JSONObject.toJSONString(wxBrokerage));
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        if(wxBrokerage.getCusName()!=null){
            queryWrapper.like("cus_name",wxBrokerage.getCusName());
        }
        if(wxBrokerage.getCusId() !=null){
            queryWrapper.eq("cus_id",wxBrokerage.getCusId());
        }
        if(wxBrokerage.getCompanyName() != null){
            queryWrapper.like("company_name",wxBrokerage.getCompanyName());
        }
        if(wxBrokerage.getCompanyId()!=null){
            queryWrapper.eq("company_id",wxBrokerage.getCompanyName());
        }
        if(wxBrokerage.getRecommentId()!=null){
            queryWrapper.eq("recomment_id",wxBrokerage.getRecommentId());
        }
        if(wxBrokerage.getRecommentName() !=null){
            queryWrapper.like("recomment_name",wxBrokerage.getRecommentName());
        }

        queryWrapper.eq("is_settle",false);

        Page page1 = wxBrokerageService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }

    /**
     * 分页获取已结佣
     * @param page
     * @param wxBrokerage
     * @return
     */
    @GetMapping(value = "/getOkBrokeragePage")
    public Result<List<WxBrokerage>> getOkBrokeragePage(Page page,WxBrokerage wxBrokerage){
        log.info("分页参数："+page.getSize()+"**"+JSONObject.toJSONString(wxBrokerage));
        QueryWrapper<WxBrokerage> queryWrapper = new QueryWrapper<>();
        if(wxBrokerage.getCusName()!=null){
            queryWrapper.like("cus_name",wxBrokerage.getCusName());
        }
        if(wxBrokerage.getCusId() !=null){
            queryWrapper.eq("cus_id",wxBrokerage.getCusId());
        }
        if(wxBrokerage.getCompanyName() != null){
            queryWrapper.like("company_name",wxBrokerage.getCompanyName());
        }
        if(wxBrokerage.getCompanyId()!=null){
            queryWrapper.eq("company_id",wxBrokerage.getCompanyName());
        }
        if(wxBrokerage.getRecommentId()!=null){
            queryWrapper.eq("recomment_id",wxBrokerage.getRecommentId());
        }
        if(wxBrokerage.getRecommentName() !=null){
            queryWrapper.like("recomment_name",wxBrokerage.getRecommentName());
        }

        queryWrapper.eq("is_settle",true);

        Page page1 = wxBrokerageService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }
    /**
     * 删除佣金
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delWxBrokerage/{id}")
    public Result<Boolean> delWxBrokerage(@PathVariable("id") Long id){
        WxBrokerage one = wxBrokerageService.getById(id);
        if(one == null || one.getId() ==null){
            return Result.build(310,"未生成佣金");
        }
        return Result.buildOk(wxCustomService.removeById(one.getId()));
    }

    /**
     * 更新佣金
     * @param wxBrokerage
     * @return
     */
    @PutMapping(value = "/updateWxBrokerage")
    public Result<Boolean> updateWxBrokerage(@RequestBody WxBrokerage wxBrokerage){
        WxBrokerage one = wxBrokerageService.getById(wxBrokerage.getId());
        if(one == null || one.getId() ==null){
            return Result.build(310,"未生成佣金");
        }
        one.setDelFlag(0);
        return Result.buildOk(wxBrokerageService.saveOrUpdate(wxBrokerage));
    }

    /**
     * 结佣
     * @param ids
     * @return
     */
    @PutMapping(value = "/settleBrokerage")
    public Result<Boolean> settleBrokerage(@RequestBody List<Long> ids){
        List<WxBrokerage> wxBrokerageList = wxBrokerageService.listByIds(ids);
        if(wxBrokerageList ==null || wxBrokerageList.size()==0){
            return Result.build(310,"佣金信息不存在");
        }
        wxBrokerageList.forEach(brokerage->{
            brokerage.setIsSettle(true);
        });
        return Result.buildOk(wxBrokerageService.updateBatchById(wxBrokerageList));
    }

//    客户

    /**
     * 获取客户详情
     * @param id
     * @return
     */
    @GetMapping(value = "/getWxCustomInfo/{id}")
    public Result<WxCustom> getWxCustomInfo(@PathVariable("id") Long id){
        WxCustom one = wxCustomService.getById(id);
        if(one == null || one.getId() ==null){
            return Result.build(310,"客户不存在");
        }
        return Result.buildOk(one);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delWxCustom/{id}")
    public Result<Boolean> delWxCustom(@PathVariable("id") Long id){
        WxCustom one = wxCustomService.getById(id);
        if(one == null || one.getId() ==null){
            return Result.build(310,"客户不存在");
        }
        one.setDelFlag(1);
        return Result.buildOk(wxCustomService.saveOrUpdate(one));
    }

    /**
     * 修改客户信息
     * @param wxCustom
     * @return
     */
    @PutMapping(value = "/updateWxCustom")
    public Result<Boolean> updateWxCustom(@RequestBody WxCustom wxCustom){
        WxCustom one = wxCustomService.getById(wxCustom.getId());
        if(one == null || one.getId() ==null){
            return Result.build(310,"客户不存在");
        }
        return Result.buildOk(wxCustomService.updateById(wxCustom));
    }

    /**
     * 新增客户
     * @param wxCustom
     * @return
     */
    @PostMapping(value = "/addWxCustom")
    public Result<Boolean> addWxCustom(@RequestBody WxCustom wxCustom){
        if(wxCustom == null || wxCustom.getCusName()==null ){
            return Result.build(301,"业务参数不能为空");
        }
        wxCustom.setDelFlag(0);
        return Result.buildOk(wxCustomService.saveOrUpdate(wxCustom));
    }

    /**
     * 分页获取客户信息
     * @param page
     * @param wxCustom
     * @return
     */
    @GetMapping(value = "/getWxCustomPage")
    public Result<List<WxCustom>> getWxCustomPage(Page page,WxCustom wxCustom){
        log.info("分页参数："+page.getSize()+"**"+JSONObject.toJSONString(wxCustom));
        QueryWrapper<WxCustom> queryWrapper = new QueryWrapper<>();
        if(wxCustom.getCusName()!=null){
            queryWrapper.like("cus_name",wxCustom.getCusName());
        }
        if(wxCustom.getGender() !=null){
            queryWrapper.eq("gender",wxCustom.getGender());
        }
        if(wxCustom.getTelPhone() != null){
            queryWrapper.like("telphone",wxCustom.getTelPhone());
        }
        queryWrapper.eq("del_flag",0);
        Page page1 = wxCustomService.page(page, queryWrapper);
        return Result.buildOk(page1.getRecords());
    }

    /**
     * 获取客户列表
     * @return
     */
    @GetMapping(value = "/getWxCustomList")
    public Result<List<WxCustom>> getWxCustomList(){
        QueryWrapper<WxCustom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        List<WxCustom> customList = wxCustomService.list(queryWrapper);
        return Result.buildOk(customList);
    }
    /**
     * 获取首页轮播图
     * @return
     */
    @GetMapping(value = "/getCarouselImgList")
    public Result<List<WxSwiperImg>> getCarouselImgList(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("img_type","0");
        return Result.buildOk(wxSwiperImgService.list(queryWrapper));
    }
    /**
     * 获取小程序导航图
     * @return
     */
    @GetMapping(value = "/getNavImgList")
    public Result<List<WxSwiperImg>> getNavImgList(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("img_type","1");
        queryWrapper.orderByAsc("sort");
        return Result.buildOk(wxSwiperImgService.list(queryWrapper));
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
     * 上传小程序首页轮播图
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadMiniCarouselImg")
    public Result uploadMiniCarouselImg(@RequestParam(name = "file",required = false) MultipartFile file) throws Exception {
        if(file.isEmpty() ){
            return Result.build(320,"文件不能为空");
        }
        List<String> fileList = minioService.listObjectNames("swiper");
        if(fileList.size()>3){
            return Result.build(320,"轮播图不能超过3张");
        }
        boolean b = minioService.putObject("swiper", file.getOriginalFilename(), file.getInputStream());
        if(!b){
            return Result.build(320,"上传失败");
        }
        String foreverObjectUrl = minioService.getForeverObjectUrl("swiper", file.getOriginalFilename());
        WxSwiperImg wxSwiperImg = new WxSwiperImg();
        wxSwiperImg.setImgType("0");
        wxSwiperImg.setImgName(file.getOriginalFilename());
        wxSwiperImg.setImgUrl(foreverObjectUrl);
        return Result.buildOk(wxSwiperImgService.saveOrUpdate(wxSwiperImg));

    }
    /**
     * 上传小程序首页导航图
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploadMiniNavImg")
    public Result uploadMiniNavImg(@RequestParam(name = "file",required = false) MultipartFile file) throws Exception {
        if(file.isEmpty() ){
            return Result.build(320,"文件不能为空");
        }
        List<String> fileList = minioService.listObjectNames("nav");
        if(fileList.size()>4){
            return Result.build(320,"不能超过4张");
        }
        boolean b = minioService.putObject("nav", file.getOriginalFilename(), file.getInputStream());
        if(!b){
            return Result.build(320,"上传失败");
        }
        String foreverObjectUrl = minioService.getForeverObjectUrl("nav", file.getOriginalFilename());
        WxSwiperImg wxSwiperImg = new WxSwiperImg();
        wxSwiperImg.setImgType("1");
        wxSwiperImg.setImgName(file.getOriginalFilename());
        wxSwiperImg.setSort(fileList.size()+1);
        wxSwiperImg.setImgUrl(foreverObjectUrl);
        return Result.buildOk(wxSwiperImgService.saveOrUpdate(wxSwiperImg));

    }

    /**
     * 上传公司轮播图
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploadCompanyImg")
    public Result uploadCompanyImg(@RequestParam(name = "file",required = false) MultipartFile file) throws Exception {
        if(file.isEmpty() ){
            return Result.build(320,"文件不能为空");
        }
        List<String> fileList = minioService.listObjectNames("company");
        if(fileList.size()>4){
            return Result.build(320,"不能超过4张");
        }
        for(String fileName: fileList){
            if(fileName.equals(file.getOriginalFilename())){
                return Result.build(340,"文件名不能重复");
            }
        }
        boolean b = minioService.putObject("company", file.getOriginalFilename(), file.getInputStream());
        if(!b){
            return Result.build(320,"上传失败");
        }
        String foreverObjectUrl = minioService.getForeverObjectUrl("company", file.getOriginalFilename());

        NzUploadFile uploadFile = new NzUploadFile();
        uploadFile.setUid(file.getOriginalFilename());
        uploadFile.setName(file.getOriginalFilename());
        uploadFile.setUrl(foreverObjectUrl);
        uploadFile.setStatus("done");
        nzUploadFileService.saveOrUpdate(uploadFile);
        return Result.buildOk(uploadFile);
    }
    /**
     * 删除小程序首页轮播图
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteMiniCarouselImg/{id}")
    public Result<Boolean> deleteMiniCarouselImg(@PathVariable("id") Long id){
        WxSwiperImg one = wxSwiperImgService.getById(id);
        Boolean isSuccess = false;
        if(one==null){
            return Result.build(310,"轮播图不存在");
        }
        boolean b = wxSwiperImgService.removeById(id);
        if(!b){
            return Result.build(320,"删除轮播图失败");
        }else {
            isSuccess = minioService.deleteFile("swiper", one.getImgName());
        }
        return Result.buildOk(isSuccess);
    }
    /**
     * 删除小程序首页轮播图
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteMiniNavImg/{id}")
    public Result<Boolean> deleteMiniNavImg(@PathVariable("id") Long id){
        WxSwiperImg one = wxSwiperImgService.getById(id);
        Boolean isSuccess = false;
        if(one==null){
            return Result.build(310,"轮播图不存在");
        }
        boolean b = wxSwiperImgService.removeById(id);
        if(!b){
            return Result.build(320,"删除轮播图失败");
        }else {
            isSuccess = minioService.deleteFile("nav", one.getImgName());
        }
        return Result.buildOk(isSuccess);
    }
    /**
     * 修改轮播图
     * @param wxSwiperImg
     * @return
     */
    @PutMapping(value = "/updateCarouselOrNavUrl")
    public Result<Boolean> updateCarouselOrNavUrl(@RequestBody WxSwiperImg wxSwiperImg){
        if(wxSwiperImg.getId()==null){
            return Result.build(301,"参数不能为空");
        }
        WxSwiperImg one = wxSwiperImgService.getById(wxSwiperImg.getId());
        if(one == null || one.getId()==null){
            return Result.build(310,"获取轮播图不存在");
        }
        one.setNavigatorUrl(wxSwiperImg.getNavigatorUrl());
        one.setNavigatorName(wxSwiperImg.getNavigatorName());
        return Result.buildOk(wxSwiperImgService.saveOrUpdate(wxSwiperImg));
    }
    /**
     * 获取轮播图详情
     * @param id
     * @return
     */
    @GetMapping(value = "getCarouselOrNavImgInfo/{id}")
    public Result<WxSwiperImg> getCarouselOrNavImgInfo(@PathVariable("id") Long id){
        WxSwiperImg one = wxSwiperImgService.getById(id);
        if(one==null){
            return Result.build(310,"轮播图不存在");
        }
        return Result.buildOk(one);
    }
}
