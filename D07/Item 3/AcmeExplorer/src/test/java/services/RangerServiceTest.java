
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
import domain.Message;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	@Autowired
	private RangerService	rangerService;


	@Test
	public void testFindAll() {
		Collection<Ranger> rangers;

		rangers = this.rangerService.findAll();
		Assert.isTrue(!rangers.isEmpty());
	}

	@Test
	public void testSave() {
		Ranger ranger, saved;
		Collection<Ranger> updated;
		Collection<Trip> trips;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent;
		Collection<Message> messagesReceived;

		trips = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		ranger = this.rangerService.create();
		ranger.setAddress("direccion");
		ranger.setEmail("email@email.com");
		ranger.setName("Ranger test name");
		ranger.setPhone("666666666");
		ranger.setSurname("ranger test surname");
		ranger.setSuspicious(false);
		ranger.setTrips(trips);
		ranger.setSocialIdentities(si);
		ranger.setMessagesReceived(messagesReceived);
		ranger.setMessagesSent(messagesSent);
		ranger.setUserAccount(LoginService.getPrincipal());
		saved = this.rangerService.save(ranger);
		updated = this.rangerService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		Ranger ranger, saved;
		Collection<Ranger> updated;
		Collection<Trip> trips;
		final Collection<SocialIdentity> si;
		final Collection<Message> messagesSent;
		final Collection<Message> messagesReceived;

		trips = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		ranger = this.rangerService.create();
		ranger.setAddress("direccion");
		ranger.setEmail("email@email.com");
		ranger.setName("Ranger test name");
		ranger.setPhone("666666666");
		ranger.setSurname("ranger test surname");
		ranger.setSuspicious(false);
		ranger.setTrips(trips);
		ranger.setSocialIdentities(si);
		ranger.setMessagesReceived(messagesReceived);
		ranger.setMessagesSent(messagesSent);
		ranger.setUserAccount(LoginService.getPrincipal());
		saved = this.rangerService.save(ranger);
		updated = this.rangerService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.rangerService.delete(saved);
		updated = this.rangerService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}

}
