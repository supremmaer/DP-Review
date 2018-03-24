
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LocationRepository;
import domain.Location;
import forms.SurvivalClassForm;

@Service
@Transactional
public class LocationService {

	//Managed Repository
	@Autowired
	private LocationRepository	locationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;


	//Constructor
	public LocationService() {
		super();
	}

	//CRUD methods
	public Location create(final SurvivalClassForm survivalClassForm) {
		Location result;

		if (survivalClassForm.getLocationId() == 0) {
			result = new Location();
			result.setLatitude(survivalClassForm.getLatitude());
			result.setLongitude(survivalClassForm.getLongitude());
			result.setName(survivalClassForm.getName());
		} else {
			result = this.locationRepository.findOne(survivalClassForm.getLocationId());
			result.setLatitude(survivalClassForm.getLatitude());
			result.setLongitude(survivalClassForm.getLongitude());
			result.setName(survivalClassForm.getName());
		}

		return result;
	}

	public Location save(final Location location) {
		Assert.notNull(location);

		Location result;

		result = this.locationRepository.save(location);
		this.actorService.isSpam(location.getName(), this.actorService.findByPrincipal());

		return result;
	}

	public void delete(final Location location) {
		Assert.notNull(location);
		Assert.isTrue(location.getId() != 0);
		Assert.isTrue(this.locationRepository.exists(location.getId()));

		this.locationRepository.delete(location);
	}

	public Collection<Location> findAll() {
		Collection<Location> result;

		result = this.locationRepository.findAll();

		return result;
	}
}
