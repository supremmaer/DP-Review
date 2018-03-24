
package services;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private TripService		tripService;


	@Test
	public void testFindAll() {
		Collection<Finder> finders;

		finders = this.finderService.findAll();

		Assert.isTrue(!finders.isEmpty());
	}

	@Test
	public void testSave() {
		Finder finder, saved;
		Collection<Finder> finders;
		Collection<Trip> trips;

		super.authenticate("explorer2");
		finder = new Finder();
		finder.setEndDate(new Date(System.currentTimeMillis() + 100));
		finder.setStartDate(new Date(System.currentTimeMillis()));
		finder.setKeyWord("Test KeyWord");
		finder.setLowerPrice(50.);
		finder.setMaxPrice(100.);
		trips = new HashSet<Trip>();
		trips.add(this.tripService.findAll().iterator().next());
		finder.setTrips(trips);

		saved = this.finderService.save(finder);
		finders = this.finderService.findAll();
		super.unauthenticate();
		Assert.isTrue(finders.contains(saved));
	}

	@Test
	public void testDelete() {
		Finder finder, saved;
		Collection<Finder> finders;
		Collection<Trip> trips;

		super.authenticate("explorer2");
		finder = new Finder();
		finder.setEndDate(new Date(System.currentTimeMillis() + 100));
		finder.setStartDate(new Date(System.currentTimeMillis()));
		finder.setKeyWord("Test KeyWord");
		finder.setLowerPrice(50.);
		finder.setMaxPrice(100.);
		trips = new HashSet<Trip>();
		trips.add(this.tripService.findAll().iterator().next());
		finder.setTrips(trips);

		saved = this.finderService.save(finder);
		this.finderService.delete(saved);
		finders = this.finderService.findAll();
		super.unauthenticate();
		Assert.isTrue(!finders.contains(saved));
	}
}
