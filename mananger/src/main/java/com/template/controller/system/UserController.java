package com.template.controller.system;

import com.template.response.PageDataResult;
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
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
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

}
