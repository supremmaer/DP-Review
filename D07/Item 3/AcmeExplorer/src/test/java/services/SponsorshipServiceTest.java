
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private CreditCardService	creditCardService;


	@Test
	public void testFindAll() {
		Collection<Sponsorship> sponsorship;

		sponsorship = this.sponsorshipService.findAll();
		Assert.isTrue(!sponsorship.isEmpty());
	}

	@Test
	public void testSave() {
		Sponsorship sponsorship;
		Sponsorship saved;
		Collection<Sponsorship> sponsorships;
		CreditCard creditCard;
		Trip trip;

		creditCard = this.creditCardService.findAll().iterator().next();
		sponsorship = new Sponsorship();
		sponsorship.setBanner("http://www.testBanner.com");
		sponsorship.setCc(creditCard);
		sponsorship.setInfoPage("http://www.testInfopage.com");
		trip = this.tripService.findAll().iterator().next();
		sponsorship.setTrip(trip);
		saved = this.sponsorshipService.save(sponsorship, trip.getId());
		sponsorships = this.sponsorshipService.findAll();
		Assert.isTrue(sponsorships.contains(saved));

	}

	@Test
	public void testDelete() {
		Sponsorship sponsorship;
		Sponsorship saved;
		Collection<Sponsorship> sponsorships;
		CreditCard creditCard;
		Trip trip;

		creditCard = this.creditCardService.findAll().iterator().next();
		sponsorship = new Sponsorship();
		sponsorship.setBanner("http://www.testBanner.com");
		sponsorship.setCc(creditCard);
		sponsorship.setInfoPage("http://www.testInfopage.com");
		trip = this.tripService.findAll().iterator().next();
		sponsorship.setTrip(trip);

		saved = this.sponsorshipService.save(sponsorship, trip.getId());
		this.sponsorshipService.delete(saved);
		sponsorships = this.sponsorshipService.findAll();
		Assert.isTrue(!sponsorships.contains(saved));

	}
}
