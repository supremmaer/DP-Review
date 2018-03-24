
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
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void testFindAll() {
		Collection<Sponsor> sponsors;

		sponsors = this.sponsorService.findAll();
		Assert.isTrue(!sponsors.isEmpty());
	}

	@Test
	public void testSave() {
		Sponsor sponsor, saved;
		Collection<Sponsor> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;
		Collection<Sponsorship> sponsorships;

		sponsorships = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("admin2");
		sponsor = new Sponsor();
		sponsor.setAddress("direccion");
		sponsor.setEmail("email@email.com");
		sponsor.setName("Admin test name");
		sponsor.setPhone("666666666");
		sponsor.setSurname("sponsor test surname");
		sponsor.setSocialIdentities(si);
		sponsor.setMessagesReceived(messagesReceived);
		sponsor.setMessagesSent(messagesSent);
		sponsor.setUserAccount(LoginService.getPrincipal());
		sponsor.setSponsorships(sponsorships);
		saved = this.sponsorService.save(sponsor);
		updated = this.sponsorService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {

		Sponsor sponsor, saved;
		Collection<Sponsor> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;
		Collection<Sponsorship> sponsorships;

		sponsorships = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("admin2");
		sponsor = new Sponsor();
		sponsor.setAddress("direccion");
		sponsor.setEmail("email@email.com");
		sponsor.setName("Admin test name");
		sponsor.setPhone("666666666");
		sponsor.setSurname("sponsor test surname");
		sponsor.setSocialIdentities(si);
		sponsor.setMessagesReceived(messagesReceived);
		sponsor.setMessagesSent(messagesSent);
		sponsor.setUserAccount(LoginService.getPrincipal());
		sponsor.setSponsorships(sponsorships);
		saved = this.sponsorService.save(sponsor);
		updated = this.sponsorService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.sponsorService.delete(saved);
		updated = this.sponsorService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
