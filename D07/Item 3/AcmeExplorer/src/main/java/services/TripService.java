
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Category;
import domain.Finder;
import domain.Manager;
import domain.Note;
import domain.Sponsorship;
import domain.Stage;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class TripService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TripRepository				tripRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public TripService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Trip create() {
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor != null);
		Assert.isTrue(actor instanceof Manager);

		Trip result;
		String ticker;
		Collection<Stage> stages;
		Collection<Note> notes;
		Collection<Sponsorship> sponsorships;
		Collection<Application> applications;
		Collection<SurvivalClass> survivalClasses;
		Map<String, String> tag;

		notes = Collections.emptySet();
		sponsorships = Collections.emptySet();
		applications = Collections.emptySet();
		survivalClasses = Collections.emptySet();
		//TODO:borrar la linea
		//ticker = TripService.generateTicker();
		ticker = this.systemConfigurationService.generateTicker();
		tag = new HashMap<String, String>();

		result = new Trip();
		stages = Collections.emptySet();
		result.setStages(stages);
		result.setNotes(notes);
		result.setSponsorships(sponsorships);
		result.setApplications(applications);
		result.setSurvivalClasses(survivalClasses);
		result.setTicker(ticker);
		result.setManager((Manager) actor);
		result.setTag(tag);

		return result;
	}

	public Collection<Trip> findAll() {
		Collection<Trip> result;

		result = this.tripRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Trip> findPublicated() {
		Collection<Trip> trips, result;
		Date now;

		trips = this.findAll();
		result = new HashSet<Trip>();
		for (final Trip e : trips) {
			now = new Date();
			if (e.getPublicationDate().before(now))
				result.add(e);
		}

		return result;
	}

	public Page<Trip> findPage(final Pageable pageable) {
		Page<Trip> result;
		result = this.tripRepository.findAll(pageable);
		Assert.notNull(result);

		return result;
	}
	public void delete(final Trip trip) {
		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.notNull(trip);
		Assert.isTrue(trip.getId() != 0);
		Assert.isTrue(this.tripRepository.exists(trip.getId()));
		Assert.isTrue(trip.getManager().getId() == actor.getId());
		Date moment;

		moment = new Date();
		Assert.isTrue(trip.getPublicationDate().after(moment));
		Assert.isTrue(trip.getStartDate().after(moment));

		this.tripRepository.delete(trip.getId());
	}

	public Trip save(final Trip trip) {
		Trip result;
		Date moment;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		moment = new Date();
		Assert.isTrue(actor != null);
		Assert.isTrue(actor instanceof Manager);
		Assert.notNull(trip);
		Assert.isTrue(trip.getPublicationDate().before(trip.getStartDate()));
		Assert.isTrue(trip.getStartDate().before(trip.getEndDate()));
		Assert.isTrue(trip.getPublicationDate().after(moment));
		Assert.isTrue(trip.getManager().getId() == actor.getId());

		if (trip.getLegalText() != null)
			Assert.isTrue(!trip.getLegalText().isDraftMode());

		this.actorService.isSpam(trip.getDescription(), this.actorService.findByPrincipal());
		this.actorService.isSpam(trip.getTitle(), this.actorService.findByPrincipal());
		this.actorService.isSpam(trip.getExplorerRequirements(), this.actorService.findByPrincipal());
		if (trip.getCancellationReason() != null)
			this.actorService.isSpam(trip.getCancellationReason(), this.actorService.findByPrincipal());

		this.actorService.isSpam(trip.getExplorerRequirements(), this.actorService.findByPrincipal());
		result = this.tripRepository.save(trip);

		return result;
	}

	public Trip saveStage(final Trip trip) { //No comprueba publicationDate, solo para guardar stages
		Trip result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor != null);
		Assert.isTrue(actor instanceof Manager || actor instanceof Administrator);
		Assert.notNull(trip);

		result = this.tripRepository.save(trip);

		return result;
	}

	public Trip saveSurvivalClass(final Trip trip) {
		Trip result, dbTrip;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		dbTrip = this.findOne(trip.getId());
		Assert.isTrue(actor != null);
		Assert.isTrue(actor instanceof Manager);
		Assert.notNull(trip);

		dbTrip.setSurvivalClasses(trip.getSurvivalClasses());

		result = this.tripRepository.save(dbTrip);

		return result;
	}

	public Trip findBySurvivalClass(final SurvivalClass survivalClass) {
		Assert.notNull(survivalClass);
		Assert.isTrue(survivalClass.getId() != 0);

		Trip result;

		result = this.tripRepository.findbySurvivalClassID(survivalClass.getId());

		return result;
	}

	//Other business methods

	public Collection<Trip> findByKey(final String key) {
		Assert.notNull(key);

		Collection<Trip> result;

		result = this.tripRepository.findByKey(key);
		result.retainAll(this.findPublicated());

		return result;
	}

	public Collection<Trip> findByCategory(final Category category) {
		Assert.notNull(category);

		Collection<Trip> result;
		int id;

		id = category.getId();
		result = this.tripRepository.findByCategoryID(id);

		return result;
	}

	public Collection<Trip> findByManager(final Manager manager) {
		Assert.notNull(manager);

		Collection<Trip> result;

		result = this.tripRepository.findByManagerID(manager.getId());

		return result;

	}

	public Collection<Trip> findByFinder(final Finder finder) {
		Assert.notNull(finder);

		Collection<Trip> trips;
		Collection<Trip> result;
		String keyword;
		Date startDate;
		Date endDate;
		Double min;
		Double max;

		keyword = finder.getKeyWord();
		startDate = finder.getStartDate();
		endDate = finder.getEndDate();
		min = finder.getLowerPrice();
		max = finder.getMaxPrice();

		trips = keyword == null ? this.tripRepository.findAll() : this.tripRepository.findByKey(keyword);
		trips.retainAll(this.findPublicated());
		result = new HashSet<Trip>();
		result.addAll(trips);
		if (startDate != null) {
			for (final Trip t : trips)
				if (t.getStartDate().before(startDate))
					result.remove(t);
			trips.retainAll(result);
		}
		if (endDate != null) {
			for (final Trip t : trips)
				if (t.getStartDate().after(endDate) || t.getEndDate().after(endDate))
					result.remove(t);
			trips.retainAll(result);
		}
		if (min != null) {
			for (final Trip t : trips)
				if (t.getPrice() < min)
					result.remove(t);
			trips.retainAll(result);
		}
		if (max != null)
			for (final Trip t : trips)
				if (t.getPrice() > max)
					result.remove(t);

		return result;
	}
	//Checker
	@SuppressWarnings("deprecation")
	public boolean isSameDay(final Date date1, final Date date2) {
		boolean result;
		result = false;
		if (date1.getDate() == date2.getDate() && date1.getMonth() == date2.getMonth() && date1.getYear() == date2.getYear())
			result = true;
		return result;
	}

	//14.6.2
	public Map<String, Double> getTripPerManagerData() {
		final Map<String, Double> result = new HashMap<>();

		result.put("average", this.tripRepository.averageTripsinManager());
		result.put("minimum", Double.valueOf(this.tripRepository.minTripsofManager()));
		result.put("maximum", Double.valueOf(this.tripRepository.maxTripsofManager()));
		result.put("standardDeviation", this.tripRepository.standardDeviationTripsperManager());
		return result;
	}

	//14.6.3
	public Map<String, Double> getPriceOfTripsData() {
		final Map<String, Double> result = new HashMap<>();
		final Collection<Trip> all = this.findAll();
		Double auxavg = 0.0;
		Double avg;
		Double min = Double.MAX_VALUE;
		Double max = Double.MIN_VALUE;
		Double auxsd = 0.0;
		Double sd;
		for (final Trip t : all) {
			auxavg = auxavg + t.getPrice();
			min = Math.min(min, t.getPrice());
			max = Math.max(max, t.getPrice());
			auxsd = auxsd + (t.getPrice() * t.getPrice());
		}
		avg = auxavg / all.size();
		sd = Math.sqrt((auxsd / all.size()) - (avg * avg));

		result.put("average", avg);
		result.put("minimum", min);
		result.put("maximum", max);
		result.put("standardDeviation", sd);
		return result;
	}

	//14.6.4
	public Map<String, Double> getTripPerRangerData() {
		final Map<String, Double> result = new HashMap<>();

		result.put("average", this.tripRepository.averageTripsinManager());
		result.put("minimum", Double.valueOf(this.tripRepository.minTripsofRanger()));
		result.put("maximum", Double.valueOf(this.tripRepository.maxTripsofRanger()));
		result.put("standardDeviation", this.tripRepository.standardDeviationTripsperRanger());
		return result;
	}

	//14.6.9
	public Double ratioTripsCancelled() {
		final Double result = this.tripRepository.ratioTripsCancelled();
		return result;
	}

	//14.6.10
	public Collection<Trip> findTripsWithMoreApplicationsThanAverage() {
		Collection<Trip> result;
		result = this.tripRepository.findTripsWithMoreApplicationsThanAverage();
		return result;
	}
	//35.4.3

	public Double ratioTripsWithOneAuditRecord() {
		final Double result = this.tripRepository.ratioTripWithOneAuditRecord();
		return result;
	}

	public Trip findOne(final int tripId) {
		Trip result;

		result = this.tripRepository.findOne(tripId);

		return result;
	}

}
