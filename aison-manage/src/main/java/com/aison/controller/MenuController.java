package com.aison.controller;

import com.aison.service.TMenuService;
import com.aison.vo.TMenuRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="系统菜单接口",tags={"系统菜单接口"})
@RestController
@RequestMapping("/api/manage/menu")
public class MenuController {
    @Autowired
    private TMenuService tMenuService;

    @ApiOperation("根据角色ID获取菜单")
    @RequestMapping("/getMenuListByRole")
    private List<TMenuRoleVO> getMenuListByRole(Long roleId){
        return tMenuService.getListMenuByRoleId(roleId);
    }
}
