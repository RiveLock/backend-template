package com.template.controller.system;

import com.template.base.domain.Permission;
import com.template.response.PageDataResult;
import com.template.service.PermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: PermController
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 17:40
 */
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
    @GetMapping("parentPermissionList")
    public List<Permission> parentPermissionList(){
        log.info("获取根权限菜单列表");
        List<Permission> permissions = permService.parentPermissionList();
        return permissions;
    }

}
