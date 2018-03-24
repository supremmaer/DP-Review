
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private ActorService	actorService;


	@Test
	public void testFindAll() {
		Collection<Actor> actors;

		actors = this.actorService.findAll();
		Assert.isTrue(!actors.isEmpty());
	}

	@Test
	public void testFindByUserAccount() {
		Actor actor;
		UserAccount userAccount;

		super.authenticate("explorer6");
		userAccount = LoginService.getPrincipal();
		actor = this.actorService.findByUserAccount(userAccount);
		Assert.isTrue(actor.getName().equals("Explorer6 name"));
		super.authenticate(null);
	}

	@Test
	public void testFindByPrincipal() {
		Actor actor;

		super.authenticate("explorer6");
		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getName().equals("Explorer6 name"));
		super.authenticate(null);
	}

}
