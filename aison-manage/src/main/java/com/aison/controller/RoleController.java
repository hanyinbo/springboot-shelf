package com.aison.controller;

import com.aison.common.Result;
import com.aison.entity.TRole;
import com.aison.service.TRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="系统角色接口",tags={"系统角色接口"})
@RestController
@RequestMapping("/api/manage/role")
@AllArgsConstructor
public class RoleController {
    private TRoleService tRoleService;

    @ApiOperation("根据用户ID获取角色列表")
    @GetMapping("/getMenuListByUserId")
    public Result getMenuListByUserId(Long userId){
       return Result.buildOk(tRoleService.findRoleByUserId(userId));
    }
}
