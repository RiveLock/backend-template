package com.template.service.impl;

import com.template.base.dao.PermissionDao;
import com.template.base.dao.RoleDao;
import com.template.base.domain.Permission;
import com.template.base.domain.Role;
import com.template.base.domain.criteria.RoleCriteria;
import com.template.base.dto.RoleDto;
import com.template.response.Fail;
import com.template.response.PageDataResult;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.service.RoleService;
import com.template.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: RoleServiceImpl
 * @Description:
 *@author: youqing
 * @version: 1.0
 * @date: 2018/12/13 10:53
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageDataResult getRoleList(Integer pageNum, Integer pageSize) {
        PageDataResult pdr = new PageDataResult();
        // 获取角色列表
        RoleCriteria rc = new RoleCriteria();
        rc.setLimit((long) pageSize);
        rc.setOffset((long) ((pageNum-1)*pageSize));
        List<Role> list = roleDao.selectList(rc.andIdIsNotNull());

        List<RoleDto> roleList = new ArrayList<>();
        for(Role r:list){
            RoleDto roleDto =  new RoleDto();

            String permissions = r.getPermissions();
            BeanUtils.copyProperties(r,roleDto);
            roleDto.setPermissionIds(permissions);

            if(!StringUtils.isEmpty(permissions)){
                String[] ids = permissions.split(",");
                List<String> p = new ArrayList <>();
                for(String id: ids){
                    Permission permission = permissionDao.selectOne(Integer.parseInt(id));
                    String name = permission.getName();
                    p.add(name);
                }
                roleDto.setPermissions(p.toString());
            }
            roleList.add(roleDto);
        }

        pdr.setList(roleList);
        pdr.setTotals(roleList.size());
        return pdr;
    }

    @Override
    public ResultData updateRole(Role role) {
        try {
            role.setUpdateTime(DateUtils.getCurrentDate());
            role.setRoleStatus(1);
            int result = roleDao.update(role);
            if(result == 0){
                log.error("角色[修改]，结果=修改失败！");
                return ResultData.fail(Fail.ROLE_UPDATE_FAIL.code,Fail.ROLE_UPDATE_FAIL.description);
            }
            log.info("角色[修改]，结果=修改成功！");
            return ResultData.success(Success.ROLE_UPDATE_SUCCESS.code,Success.ROLE_UPDATE_SUCCESS.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("角色[修改]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData delRole(Integer id) {
        try{
            int result = roleDao.delete(id);
            if(result == 0){
                log.error("角色[删除]，结果=删除失败！");
                return ResultData.fail(Fail.ROLE_DELETE_FAIL.code,Fail.ROLE_DELETE_FAIL.description);
            }
            log.info("角色[删除]，结果=删除成功！");
            return ResultData.success(Success.ROLE_DELETE_SUCCESS.code,Success.ROLE_DELETE_SUCCESS.description);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("角色[删除]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData addRole(Role role) {
        try {
            role.setCreateTime(DateUtils.getCurrentDate());
            role.setUpdateTime(DateUtils.getCurrentDate());
            role.setRoleStatus(1);
            int result = roleDao.insert(role);
            if(result == 0){
                log.error("角色[新增]，结果=新增失败！");
                return ResultData.fail(Fail.ROLE_ADD_FAIL.code,Fail.ROLE_ADD_FAIL.description);
            }
            log.info("角色[新增]，结果=新增成功！");
            return ResultData.success(Success.ROLE_ADD_SUCCESS.code,Success.ROLE_ADD_SUCCESS.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("角色[新增]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData getRoles() {
        Map<String,Object> data = new HashMap();
        List <Role> roles = roleDao.selectList(RoleCriteria.idIsNotNull());
        data.put("roles",roles);
        return ResultData.success("","").with(data);
    }
}
