package com.template.controller.system;

import com.template.base.domain.User;
import com.template.response.PageDataResult;
import com.template.response.ResultData;
import com.template.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @Title: UserController
 * @Description: 用户管理
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 16:53
 */
@Api(description = "用户管理接口")
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * 功能描述: 获取用户列表
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 9:45
     */
    @ApiOperation(value = "获取用户列表")
    @PostMapping("getUserList")
    public PageDataResult getUserList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize ) {
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pdr = userService.getUserList(pageNum ,pageSize);
            log.info("用户列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户列表查询异常！", e);
        }
        return pdr;
    }

    /**
     *
     * 功能描述: 设置用户[新增或更新]
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/12/13 13:34
     */
    @ApiOperation(value = "设置用户[新增或更新]")
    @PostMapping("setUser")
    public ResultData setUser(User user) {
        log.info("设置用户[新增或更新]！user:" + user);
        ResultData resultData = new ResultData();
        if(user.getId() == null){
            //新增
            resultData = userService.addUser(user);
        }else{
            //修改
            resultData = userService.updateUser(user);
        }
        return resultData;
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("delUser")
    public ResultData delUser(@RequestParam("id") Long id) {
        log.info("删除用户！id:" + id);
        ResultData resultData = new ResultData();
        //删除权限
        resultData = userService.delUser(id);
        return resultData;
    }

}
