
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private CreditCardService	creditCardService;


	@Test
	public void testFindAll() {
		Collection<Application> application;

		application = this.applicationService.findAll();
		Assert.isTrue(!application.isEmpty());
	}

	@Test
	public void testSave() {
		Application application, saved;
		Explorer explorer;
		Trip trip;
		Collection<Trip> trips;

		super.authenticate("explorer1");

		trips = this.tripService.findAll();
		explorer = this.explorerService.findByPrincipal();
		trip = trips.iterator().next();

		application = this.applicationService.create(explorer, trip);
		application.setExplorer(explorer);
		application.setTrip(trip);
		application.setMoment(new Date());
		application.setComment("Test Application");
		application.setCreditCard(this.creditCardService.findAll().iterator().next());

		saved = this.applicationService.save(application);
		Assert.isTrue(this.applicationService.findAll().contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		Application application, saved;
		Explorer explorer;
		Trip trip;
		Collection<Trip> trips;

		super.authenticate("explorer1");

		trips = this.tripService.findAll();
		explorer = this.explorerService.findByPrincipal();
		trip = trips.iterator().next();

		application = this.applicationService.create(explorer, trip);
		application.setExplorer(explorer);
		application.setTrip(trip);
		application.setMoment(new Date());
		application.setComment("Test Application");
		application.setCreditCard(this.creditCardService.findAll().iterator().next());

		saved = this.applicationService.save(application);
		Assert.isTrue(this.applicationService.findAll().contains(saved));

		super.authenticate(null);

		this.applicationService.delete(saved);
		Assert.isTrue(!this.applicationService.findAll().contains(saved));
	}

	//DONE: completar, falla.
	//NOTA: Falla por la DB, creadla de nuevo y repopulad si os falla
	@Test
	public void addCreditCardTest() {
		Application application, saved;
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setHolderName("John Doe");
		creditCard.setBrandName("Brand");
		creditCard.setNumber("1111222233334444");
		creditCard.setCvvCode(433);
		creditCard.setExpirationYear(2019);
		creditCard.setExpirationMonth(11);

		Explorer explorer;
		Trip trip;
		Collection<Trip> trips;

		super.authenticate("explorer1");

		trips = this.tripService.findAll();
		explorer = this.explorerService.findByPrincipal();
		trip = trips.iterator().next();

		application = this.applicationService.create(explorer, trip);
		application.setMoment(new Date());
		application.setComment("Test Application");

		saved = this.applicationService.save(application);
		saved.setStatus("DUE");
		saved = this.applicationService.save(saved);
		this.applicationService.addCreditCard(saved, creditCard);
		Assert.isTrue(saved.getCreditCard().equals(creditCard));
		super.authenticate(null);
	}
}
