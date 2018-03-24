
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
import domain.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LocationServiceTest extends AbstractTest {

	@Autowired
	private LocationService	locationService;


	@Test
	public void testFindAll() {
		Collection<Location> location;

		location = this.locationService.findAll();
		Assert.isTrue(!location.isEmpty());
	}

	@Test
	public void testSave() {
		Location location, saved;
		Collection<Location> locations;

		location = new Location();
		location.setLatitude(23.6);
		location.setLongitude(56.2);
		location.setName("Test Location");

		saved = this.locationService.save(location);
		locations = this.locationService.findAll();
		Assert.isTrue(locations.contains(saved));
	}

	@Test
	public void testDelete() {
		Location location, saved;
		Collection<Location> updated;

		location = new Location();
		location.setLatitude(23.6);
		location.setLongitude(56.2);
		location.setName("Test Location");
		saved = this.locationService.save(location);

		this.locationService.delete(saved);
		updated = this.locationService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
