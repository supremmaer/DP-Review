
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Message;
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testFindAll() {
		Collection<Administrator> administrators;

		administrators = this.administratorService.findAll();
		Assert.isTrue(!administrators.isEmpty());
	}

	@Test
	public void testSave() {
		Administrator administrator, saved;
		Collection<Administrator> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;

		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("admin2");
		administrator = new Administrator();
		administrator.setAddress("direccion");
		administrator.setEmail("email@email.com");
		administrator.setName("Admin test name");
		administrator.setPhone("666666666");
		administrator.setSurname("administrator test surname");
		administrator.setSocialIdentities(si);
		administrator.setMessagesReceived(messagesReceived);
		administrator.setMessagesSent(messagesSent);
		administrator.setUserAccount(LoginService.getPrincipal());
		saved = this.administratorService.save(administrator);
		updated = this.administratorService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {

		Administrator administrator, saved;
		Collection<Administrator> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;

		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("admin2");
		administrator = new Administrator();
		administrator.setAddress("direccion");
		administrator.setEmail("email@email.com");
		administrator.setName("Admin test name");
		administrator.setPhone("666666666");
		administrator.setSurname("administrator test surname");
		administrator.setSocialIdentities(si);
		administrator.setMessagesReceived(messagesReceived);
		administrator.setMessagesSent(messagesSent);
		administrator.setUserAccount(LoginService.getPrincipal());
		saved = this.administratorService.save(administrator);
		updated = this.administratorService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.administratorService.delete(saved);
		updated = this.administratorService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
