package com.template;

import com.template.base.dao.TemplateMemberDao;
import com.template.base.domain.TemplateMember;
import com.template.base.domain.criteria.TemplateMemberCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManangerApplicationTests {

	@Autowired
	private TemplateMemberDao templateMemberDao;

	@Test
	public void contextLoads() {
		TemplateMember member = templateMemberDao.selectOne(TemplateMemberCriteria.loginNameEqualTo("admin").setLimit(1L));
		System.out.println(member.getLoginPassword());
	}

}
