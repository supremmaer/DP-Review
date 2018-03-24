
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
import domain.Manager;
import domain.Message;
import domain.SocialIdentity;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	@Autowired
	private ManagerService	managerService;


	@Test
	public void testFindAll() {
		Collection<Manager> managers;

		managers = this.managerService.findAll();
		Assert.isTrue(!managers.isEmpty());
	}

	@Test
	public void testSave() {
		Manager manager, saved;
		Collection<Manager> updated;
		Collection<Trip> trips;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent;
		Collection<Message> messagesReceived;

		trips = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		manager = new Manager();
		manager.setAddress("direccion");
		manager.setEmail("email@email.com");
		manager.setName("Manager test name");
		manager.setPhone("666666666");
		manager.setSurname("manager test surname");
		manager.setSuspicious(false);
		manager.setTrips(trips);
		manager.setSocialIdentities(si);
		manager.setMessagesReceived(messagesReceived);
		manager.setMessagesSent(messagesSent);
		manager.setUserAccount(LoginService.getPrincipal());
		saved = this.managerService.save(manager);
		updated = this.managerService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		Manager manager, saved;
		Collection<Manager> updated;
		Collection<Trip> trips;
		final Collection<SocialIdentity> si;
		final Collection<Message> messagesSent;
		final Collection<Message> messagesReceived;

		trips = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		manager = new Manager();
		manager.setAddress("direccion");
		manager.setEmail("email@email.com");
		manager.setName("Manager test name");
		manager.setPhone("666666666");
		manager.setSurname("manager test surname");
		manager.setSuspicious(false);
		manager.setTrips(trips);
		manager.setSocialIdentities(si);
		manager.setMessagesReceived(messagesReceived);
		manager.setMessagesSent(messagesSent);
		manager.setUserAccount(LoginService.getPrincipal());
		saved = this.managerService.save(manager);
		updated = this.managerService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.managerService.delete(saved);
		updated = this.managerService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}

}
