package com.template.base.dao;


import com.template.base.domain.TemplateMember;
import com.template.base.domain.base.BaseTemplateMember;
import com.template.base.domain.criteria.TemplateMemberCriteria;

import java.util.List;

public interface TemplateMemberDao {
    int insert(BaseTemplateMember record);

    TemplateMember selectOne(Long pk);

    TemplateMember selectOne(TemplateMemberCriteria criteria);

    List<TemplateMember> selectList(TemplateMemberCriteria criteria);

    int count(TemplateMemberCriteria criteria);

    int update(BaseTemplateMember record);

    int update(BaseTemplateMember record, TemplateMemberCriteria criteria);

    int delete(Long pk);

    int delete(TemplateMemberCriteria criteria);
}