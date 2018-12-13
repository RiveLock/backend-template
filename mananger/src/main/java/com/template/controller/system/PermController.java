package com.template.controller.system;

import com.template.base.domain.Permission;
import com.template.response.PageDataResult;
import com.template.response.ResultData;
import com.template.service.PermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @Title: PermController
 * @Description: 权限管理
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 17:40
 */
@Api(description = "权限管理接口")
@RestController
@RequestMapping("perm")
@Slf4j
public class PermController {



    @Autowired
    private PermService permService;

    /**
     *
     * 功能描述: 获取权限菜单列表
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/12 17:41
     */
    @ApiOperation(value = "获取权限菜单列表")
    @PostMapping("permissionList")
    @ResponseBody
    public PageDataResult permissionList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize){
        log.info("获取权限菜单列表");
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pdr = permService.getPermissionList(pageNum ,pageSize);
            log.info("权限菜单列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("权限菜单列表查询异常！", e);
        }
        return pdr;
    }


    /**
     *
     * 功能描述: 获取根权限菜单列表
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/11/30 11:35
     */
    @ApiOperation(value = "获取根权限菜单列表")
    @GetMapping("parentPermissionList")
    public List<Permission> parentPermissionList(){
        log.info("获取根权限菜单列表");
        List<Permission> permissions = permService.parentPermissionList();
        return permissions;
    }

    /**
     *
     * 功能描述: 设置权限[新增或更新]
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 9:59
     */
    @ApiOperation(value = "设置权限[新增或更新]")
    @PostMapping("setPermission")
    public ResultData setPermission(Permission permission) {
        log.info("设置权限[新增或更新]！permission:" + permission);
        ResultData resultData = new ResultData();
        if(permission.getId() == null){
            //新增权限
            resultData = permService.addPermission(permission);
        }else{
            //修改权限
            resultData = permService.updatePermission(permission);
        }
        return resultData;
    }

    /**
     *
     * 功能描述: 删除权限
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 10:38
     */
    @ApiOperation(value = "删除权限")
    @PostMapping("del")
    public ResultData del(@RequestParam("id") Integer id) {
        log.info("删除权限！id:" + id);
        ResultData resultData = new ResultData();
        //删除权限
        resultData = permService.del(id);
        return resultData;
    }

}
