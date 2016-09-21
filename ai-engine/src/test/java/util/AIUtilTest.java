package util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.api.AIServiceException;

import pojo.AuthUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/test-applicationContext.xml")
public class AIUtilTest {
	
	@Autowired
	AIUtil aiUtil;
	
	@Test
    public void testAuthUserRep() {
		try {
			aiUtil.sendMessage("i want to sleep");
		} catch (AIServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
