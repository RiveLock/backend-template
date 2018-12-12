package com.template.service.impl;

import com.template.base.dao.PermissionDao;
import com.template.base.domain.Permission;
import com.template.base.domain.criteria.PermissionCriteria;
import com.template.base.dto.PermDto;
import com.template.response.PageDataResult;
import com.template.service.PermService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: PermServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 18:53
 */
@Service
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
}
