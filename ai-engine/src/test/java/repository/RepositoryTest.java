/**
 * 
 */
package repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pojo.AuthUser;
import repositories.AuthUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/spring-data-hibernate-repositories.xml")
public class RepositoryTest {

    //private static Logger LOG = Logger.getLogger(SchemaGen.class);

    @Autowired
    AuthUserRepository authUserRepository;
    
    @Ignore
	@Test
    public void testAuthUserRep() {
    	AuthUser user = new AuthUser();
    	user.setEmail("test@test.com");
    	user.setPassword("test");
        user.setFullName("akshit shah");
        authUserRepository.saveAndFlush(user);
        AuthUser dbUser = authUserRepository.findById((Integer) 1);
        System.out.println(dbUser.getFullName());
    }
}
