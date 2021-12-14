package com.aison.controller;

import com.aison.service.TMenuService;
import com.aison.vo.TMenuRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="系统菜单接口",tags={"系统菜单接口"})
@RestController
@RequestMapping("/api/manage/menu")
@AllArgsConstructor
public class MenuController {

    private TMenuService tMenuService;

    @ApiOperation("根据角色ID获取菜单")
    @RequestMapping("/getMenuListByRole")
    private List<TMenuRoleVO> getMenuListByRole(Long roleId){
        return tMenuService.getListMenuByRoleId(roleId);
    }

    @ApiOperation("根据path获取菜单")
    @RequestMapping("/findAllRoleNameByPath")
    private List<String> findAllRoleNameByPath(String path){
        return tMenuService.findAllRoleNameByPath(path);
    }
}
