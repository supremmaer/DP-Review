
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
import domain.Location;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest {

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private LocationService			locationService;

	@Autowired
	private TripService				tripService;


	@Test
	public void testFindAll() {
		Collection<SurvivalClass> survivalClass;

		survivalClass = this.survivalClassService.findAll();
		Assert.isTrue(!survivalClass.isEmpty());
	}

	@Test
	public void testSave() {
		SurvivalClass survivalClass, saved;
		Collection<SurvivalClass> survivalClasses;
		Location location, savedLocation;
		Date date;
		Trip trip;

		super.authenticate("manager1");
		date = new Date(System.currentTimeMillis());
		location = new Location();
		location.setLatitude(23.6);
		location.setLongitude(56.2);
		location.setName("Test Location");
		trip = this.tripService.findAll().iterator().next();
		savedLocation = this.locationService.save(location);
		survivalClass = new SurvivalClass();
		survivalClass.setDescription("Test description");
		survivalClass.setLocation(savedLocation);
		survivalClass.setMoment(date);
		survivalClass.setTitle("Test title");

		saved = this.survivalClassService.save(survivalClass, trip.getId());
		survivalClasses = this.survivalClassService.findAll();
		super.unauthenticate();
		Assert.isTrue(survivalClasses.contains(saved));
	}
	@Test
	public void testDelete() {
		SurvivalClass survivalClass, saved;
		Collection<SurvivalClass> survivalClasses;
		Location location, savedLocation;
		Date date;
		Trip trip;

		super.authenticate("manager1");
		date = new Date(System.currentTimeMillis());
		location = new Location();
		location.setLatitude(23.6);
		location.setLongitude(56.2);
		location.setName("Test Location");
		savedLocation = this.locationService.save(location);
		survivalClass = new SurvivalClass();
		survivalClass.setDescription("Test description");
		survivalClass.setLocation(savedLocation);
		survivalClass.setMoment(date);
		survivalClass.setTitle("Test title");
		trip = this.tripService.findAll().iterator().next();

		saved = this.survivalClassService.save(survivalClass, trip.getId());
		this.survivalClassService.delete(saved, trip.getId());
		survivalClasses = this.survivalClassService.findAll();
		super.unauthenticate();
		Assert.isTrue(!survivalClasses.contains(saved));
	}
}
