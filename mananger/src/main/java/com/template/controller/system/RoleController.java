package com.template.controller.system;

import com.template.base.domain.Role;
import com.template.response.PageDataResult;
import com.template.response.ResultData;
import com.template.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Title: RoleController
 * @Description: 角色管理
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/13 10:51
 */
@Api(description = "角色管理接口")
@RestController
@RequestMapping("role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     *
     * 功能描述: 分页获取角色列表
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 14:47
     */
    @ApiOperation(value = "分页获取角色列表")
    @PostMapping("getRoleList")
    @ResponseBody
    public PageDataResult getRoleList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize){
        log.info("获取角色列表");
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pdr = roleService.getRoleList(pageNum ,pageSize);
            log.info("角色列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("角色列表查询异常！", e);
        }
        return pdr;
    }


    /**
     *
     * 功能描述: 获取角色列表
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 14:47
     */
    @GetMapping("getRoles")
    public ResultData getRoles(){
        log.info("获取角色列表");
        return roleService.getRoles();
    }


    /**
     *
     * 功能描述: 设置角色[新增或更新]
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 12:01
     */
    @PostMapping("setRole")
    @ResponseBody
    public ResultData setRole(Role role) {
        log.info("设置角色[新增或更新]！role:" + role);
        ResultData resultData = new ResultData();
        if(role.getId() == null){
            //新增角色
            resultData = roleService.addRole(role);
        }else{
            //修改角色
            resultData = roleService.updateRole(role);
        }
        return resultData;
    }

    /**
     *
     * 功能描述: 删除角色
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 12:10
     */
    @ApiOperation(value = "删除角色")
    @PostMapping("delRole")
    public ResultData delRole(@RequestParam("id") Integer id) {
        log.info("删除权限！id:" + id);
        ResultData resultData = new ResultData();
        //删除权限
        resultData = roleService.delRole(id);
        return resultData;
    }


}
