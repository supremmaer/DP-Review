
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import domain.Actor;
import domain.Explorer;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;
import forms.SurvivalClassForm;

@Service
@Transactional
public class SurvivalClassService {

	//Managed Repository
	@Autowired
	private SurvivalClassRepository	survivalClassRepository;

	//Supporting Services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private TripService				tripService;


	//Constructor
	public SurvivalClassService() {
		super();
	}

	//CRUD methods
	//43
	public SurvivalClass create() {
		SurvivalClass result;

		result = new SurvivalClass();

		return result;
	}
	public SurvivalClass create(final SurvivalClassForm survivalClassForm) {
		SurvivalClass result;

		if (survivalClassForm.getSurvivalClassId() == 0) {
			result = this.create();
			result.setTitle(survivalClassForm.getTitle());
			result.setDescription(survivalClassForm.getDescription());
			result.setMoment(survivalClassForm.getMoment());
		} else {
			result = this.survivalClassRepository.findOne(survivalClassForm.getSurvivalClassId());
			result.setTitle(survivalClassForm.getTitle());
			result.setDescription(survivalClassForm.getDescription());
			result.setMoment(survivalClassForm.getMoment());
		}

		return result;
	}

	public SurvivalClass save(final SurvivalClass survivalClass, final int tripId) {
		Assert.notNull(survivalClass);
		SurvivalClass result;
		Trip trip;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		trip = this.tripService.findOne(tripId);

		Assert.isTrue(trip.getManager().getId() == actor.getId());

		this.actorService.isSpam(survivalClass.getDescription(), this.actorService.findByPrincipal());
		this.actorService.isSpam(survivalClass.getTitle(), this.actorService.findByPrincipal());
		result = this.survivalClassRepository.save(survivalClass);

		return result;
	}

	public void delete(final SurvivalClass survivalClass, final int tripId) {
		Assert.notNull(survivalClass);
		Assert.isTrue(survivalClass.getId() != 0);
		Trip trip;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		trip = this.tripService.findOne(tripId);

		Assert.isTrue(trip.getManager().getId() == actor.getId());

		this.survivalClassRepository.delete(survivalClass.getId());
	}

	public Collection<SurvivalClass> findAll() {
		Collection<SurvivalClass> result;

		result = this.survivalClassRepository.findAll();

		return result;
	}

	public Collection<SurvivalClass> findByTrip(final Trip trip) {
		Assert.notNull(trip);
		Assert.isTrue(trip.getId() != 0);

		Collection<SurvivalClass> result;

		result = this.survivalClassRepository.findbyTripID(trip.getId());

		return result;
	}
	public SurvivalClass findOne(final int survivalClassId) {
		Assert.isTrue(survivalClassId != 0);
		SurvivalClass result;

		result = this.survivalClassRepository.findOne(survivalClassId);

		return result;
	}
	// Other business methods
	public Collection<SurvivalClass> findByManager(final Manager manager) {
		Assert.notNull(manager);

		Collection<SurvivalClass> result;

		result = this.survivalClassRepository.findbyManagerID(manager.getId());

		return result;
	}
	public Collection<SurvivalClass> findByExplorer(final Explorer explorer) {
		Assert.notNull(explorer);

		Collection<SurvivalClass> result;
		//Cambiar a una sola query
		result = this.survivalClassRepository.findbyExplorerID(explorer.getId());

		return result;
	}
}
