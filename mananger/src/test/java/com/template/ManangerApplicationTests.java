package com.template;

import com.template.base.domain.User;
import com.template.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManangerApplicationTests {


	@Autowired
	private RedisService redisService;

	@Test
	public void contextLoads() {
		//User u  = (User) redisService.get("91c72c29-cde5-4d09-8318-31b22880d1e9");
		System.out.println(redisService.get("91c72c29-cde5-4d09-8318-31b22880d1e9"));
		//System.out.println(u);
	}

}
