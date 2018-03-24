
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
import domain.Category;
import domain.LegalText;
import domain.Ranger;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	@Autowired
	private TripService			tripService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private RangerService		rangerService;


	@Test
	public void testFindAll() {
		Collection<Trip> trips;

		trips = this.tripService.findAll();

		Assert.isTrue(!trips.isEmpty());
	}

	@Test
	public void testSave() {
		Trip trip, saved;
		Collection<Trip> trips;
		LegalText legalText;
		Category category;
		Ranger ranger;

		super.authenticate("manager1");
		legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		category = (Category) this.categoryService.findAll().toArray()[2];
		ranger = (Ranger) this.rangerService.findAll().toArray()[0];
		trip = this.tripService.create();
		trip.setTitle("trip de prueba");
		trip.setCategory(category);
		trip.setDescription("descripcion");
		trip.setEndDate(new Date());
		trip.setPublicationDate(new Date(System.currentTimeMillis() + 10000));
		trip.setStartDate(new Date());
		trip.setLegalText(legalText);
		trip.setCategory(category);
		trip.setExplorerRequirements("requisitos");
		trip.setRanger(ranger);

		saved = this.tripService.save(trip);
		trips = this.tripService.findAll();
		super.authenticate(null);
		Assert.isTrue(trips.contains(saved));
	}

	@Test
	public void testDelete() {
		Trip trip, saved;
		Collection<Trip> trips;
		LegalText legalText;
		Category category;
		Ranger ranger;

		super.authenticate("manager1");
		legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		category = (Category) this.categoryService.findAll().toArray()[2];
		ranger = (Ranger) this.rangerService.findAll().toArray()[0];
		trip = this.tripService.create();
		trip.setTitle("trip de prueba");
		trip.setCategory(category);
		trip.setDescription("descripcion");
		trip.setEndDate(new Date(System.currentTimeMillis() + 100000));
		trip.setPublicationDate(new Date(System.currentTimeMillis() + 100000));
		trip.setStartDate(new Date(System.currentTimeMillis() + 100000));
		trip.setLegalText(legalText);
		trip.setCategory(category);
		trip.setExplorerRequirements("requisitos");
		trip.setRanger(ranger);

		saved = this.tripService.save(trip);
		trips = this.tripService.findAll();
		Assert.isTrue(trips.contains(saved));

		this.tripService.delete(saved);
		trips = this.tripService.findAll();
		Assert.isTrue(!trips.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testFindByKey() {
		Collection<Trip> result;
		result = this.tripService.findByKey("171011");
		System.out.println(result);
		Assert.isTrue(!result.isEmpty());
	}

}
