package com.aison.controller;

import com.aison.common.Result;
import com.aison.service.TMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="系统菜单接口",tags={"系统菜单接口"})
@RestController
@RequestMapping("/api/manage/menu")
@AllArgsConstructor
public class MenuController {

    private TMenuService tMenuService;

    @ApiOperation("根据用户角色ID获取菜单")
    @GetMapping("/getMenuListByRole")
    public Result getMenuListByRole(){
        return tMenuService.getListMenuByRole();
    }

    @ApiOperation("根据path获取菜单")
    @RequestMapping("/findAllRoleNameByPath")
    public Result findAllRoleNameByPath(String path){
        return Result.buildOk(tMenuService.findAllRoleNameByPath(path));
    }

}
