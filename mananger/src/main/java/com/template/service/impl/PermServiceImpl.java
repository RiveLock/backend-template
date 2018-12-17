package com.template.service.impl;

import com.template.base.dao.PermissionDao;
import com.template.base.domain.Permission;
import com.template.base.domain.Role;
import com.template.base.domain.User;
import com.template.base.domain.criteria.PermissionCriteria;
import com.template.base.dto.PermDto;
import com.template.response.Fail;
import com.template.response.PageDataResult;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.service.PermService;
import com.template.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: PermServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 18:53
 */
@Service
@Slf4j
public class PermServiceImpl implements PermService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageDataResult getPermissionList(Integer pageNum, Integer pageSize) {
        PageDataResult pdr = new PageDataResult();
        // 获取权限菜单列表
        PermissionCriteria p = new PermissionCriteria();
        p.setLimit((long) pageSize);
        p.setOffset((long) ((pageNum-1)*pageSize));
        List<Permission> list = permissionDao.selectList(p.andIdIsNotNull());
        List<PermDto> permDtos = new ArrayList<>();
        for(Permission permission : list){
            PermDto permDto = new PermDto();
            BeanUtils.copyProperties(permission,permDto);
            if(permission.getPid() != 0){
                Permission perm = permissionDao.selectOne(permission.getPid());
                permDto.setPname(perm.getName());
            }
            permDtos.add(permDto);

        }
        pdr.setList(permDtos);
        pdr.setTotals(permDtos.size());

        return pdr;
    }

    @Override
    public List <Permission> parentPermissionList() {
        PermissionCriteria p = new PermissionCriteria();
        p.andPidEqualTo(0);
        List<Permission> permissions = permissionDao.selectList(p);
        return permissions;
    }

    @Override
    public ResultData addPermission(Permission permission) {
        try {
            permission.setCreateTime(DateUtils.getCurrentDate());
            permission.setUpdateTime(DateUtils.getCurrentDate());
            permission.setDelFlag(1);
            int result = permissionDao.insert(permission);
            if(result == 0){
                log.error("权限[新增]，结果=新增失败！");
                return ResultData.fail(Fail.PERM_ADD_FAIL.code,Fail.PERM_ADD_FAIL.description);
            }
            log.info("权限[新增]，结果=新增成功！");
            return ResultData.success(Success.PERM_ADD_SUCCESS.code,Success.PERM_ADD_SUCCESS.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("权限[新增]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData updatePermission(Permission permission) {
        try{
            permission.setUpdateTime(DateUtils.getCurrentDate());
            int result = permissionDao.update(permission);
            if(result == 0){
                log.error("权限[更新]，结果=更新失败！");
                return ResultData.fail(Fail.PERM_UPDATE_FAIL.code,Fail.PERM_UPDATE_FAIL.description);
            }
            log.info("权限[更新]，结果=更新成功！");
            return ResultData.success(Success.PERM_UPDATE_SUCCESS.code,Success.PERM_UPDATE_SUCCESS.description);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("权限[更新]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData del(Integer id) {
        try{
            int result = permissionDao.delete(id);
            if(result == 0){
                log.error("权限[删除]，结果=删除失败！");
                return ResultData.fail(Fail.PERM_DELETE_FAIL.code,Fail.PERM_DELETE_FAIL.description);
            }
            log.info("权限[删除]，结果=删除成功！");
            return ResultData.success(Success.PERM_UPDATE_SUCCESS.code,Success.PERM_UPDATE_SUCCESS.description);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("权限[删除]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

}
