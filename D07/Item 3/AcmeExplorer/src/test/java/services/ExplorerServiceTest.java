
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Application;
import domain.Contact;
import domain.CreditCard;
import domain.Explorer;
import domain.Message;
import domain.SocialIdentity;
import domain.Story;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ContactService			contactService;

	@Autowired
	private UserAccountService		userAccountService;


	@Test
	public void testFindAll() {
		Collection<Explorer> explorers;

		explorers = this.explorerService.findAll();
		Assert.isTrue(!explorers.isEmpty());
	}

	@Test
	public void testSave() {
		Explorer explorer, saved;
		Collection<Explorer> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;
		Collection<Application> applications;
		Collection<SurvivalClass> survivalClasses;
		Collection<Story> stories;
		Collection<Contact> contacts;
		Contact contact, savedC;

		contact = new Contact();
		contact.setName("John Doe");
		contact.setPhone("611522433");
		contact.setEmail("johndoe@server.com");
		savedC = this.contactService.save(contact);

		contacts = new HashSet<Contact>();
		contacts.add(savedC);
		applications = Collections.emptySet();
		survivalClasses = Collections.emptySet();
		stories = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		explorer = new Explorer();
		explorer.setAddress("direccion");
		explorer.setEmail("email@email.com");
		explorer.setName("Admin test name");
		explorer.setPhone("666666666");
		explorer.setSurname("explorer test surname");
		explorer.setSocialIdentities(si);
		explorer.setMessagesReceived(messagesReceived);
		explorer.setMessagesSent(messagesSent);
		explorer.setUserAccount(LoginService.getPrincipal());
		explorer.setApplications(applications);
		explorer.setStories(stories);
		explorer.setSurvivalClasses(survivalClasses);
		explorer.setContacts(contacts);
		saved = this.explorerService.save(explorer);
		updated = this.explorerService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		Explorer explorer, saved;
		Collection<Explorer> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;
		Collection<Application> applications;
		Collection<SurvivalClass> survivalClasses;
		Collection<Story> stories;
		Collection<Contact> contacts;
		Contact contact, savedC;

		contact = new Contact();
		contact.setName("John Doe");
		contact.setPhone("611522433");
		contact.setEmail("johndoe@server.com");
		savedC = this.contactService.save(contact);

		contacts = new HashSet<Contact>();
		contacts.add(savedC);
		applications = Collections.emptySet();
		survivalClasses = Collections.emptySet();
		stories = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		explorer = new Explorer();
		explorer.setAddress("direccion");
		explorer.setEmail("email@email.com");
		explorer.setName("Admin test name");
		explorer.setPhone("666666666");
		explorer.setSurname("explorer test surname");
		explorer.setSocialIdentities(si);
		explorer.setMessagesReceived(messagesReceived);
		explorer.setMessagesSent(messagesSent);
		explorer.setUserAccount(LoginService.getPrincipal());
		explorer.setApplications(applications);
		explorer.setStories(stories);
		explorer.setSurvivalClasses(survivalClasses);
		explorer.setContacts(contacts);
		saved = this.explorerService.save(explorer);
		updated = this.explorerService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.explorerService.delete(saved);
		updated = this.explorerService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}

	//DONE moar methods

	@Test
	public void testFindByPrincipal() {
		Actor actor;

		super.authenticate("explorer1");
		actor = this.explorerService.findByPrincipal();
		Assert.isTrue(actor.getName().equals("Explorer1 name"));
		super.authenticate(null);
	}

	@Test
	public void testFindByUserAccount() {
		Actor actor;
		final Collection<Explorer> explorers = this.explorerService.findAll();
		Explorer explorer = null;
		for (final Explorer e : explorers)
			if (e.getName().equals("Explorer1 name"))
				explorer = e;
		final UserAccount userAccount = this.userAccountService.findByActor(explorer);
		actor = this.explorerService.findByUserAccount(userAccount);
		Assert.isTrue(actor.getName().equals("Explorer1 name"));
	}

	@Test
	public void testApplyForTrip() {
		Application application, saved;
		Explorer explorer;
		Collection<Trip> trips;
		Trip trip = null;

		Collection<CreditCard> creditCards;
		CreditCard creditCard = null;

		super.authenticate("explorer3");
		explorer = this.explorerService.findByPrincipal();

		trips = this.tripService.findAll();
		for (final Trip t : trips)
			if (t.getTitle().equals("Viaje2"))
				trip = t;

		creditCards = this.creditCardService.findAll();
		for (final CreditCard c : creditCards)
			if (c.getCvvCode() == 456)
				creditCard = c;

		application = new Application();
		application.setMoment(new Date());
		application.setCreditCard(creditCard);
		application.setExplorer(explorer);
		application.setTrip(trip);
		application.setComment("Test Application");

		saved = this.explorerService.ApplyForTrip(application);
		Assert.isTrue(this.applicationService.findByExplorer(explorer).contains(saved));
		Assert.isTrue(explorer.getApplications().contains(saved));

	}

	@Test
	public void testFindByOwnFinder() {
		Collection<Trip> trips;

		super.authenticate("explorer3");
		trips = this.explorerService.findByOwnFinder();
		Assert.isTrue(trips.isEmpty());
		super.authenticate(null);
	}

	@Test
	public void testEnrolSurvivalClass() {
		SurvivalClass survivalClass = null;

		for (final SurvivalClass s : this.survivalClassService.findAll())
			if (s.getTitle().equals("Viaje a R'lyeh"))
				survivalClass = s;

		super.authenticate("explorer3");
		this.explorerService.enrolSurvivalClass(survivalClass);
		Assert.isTrue(this.explorerService.findByPrincipal().getSurvivalClasses().contains(survivalClass));
	}

}
