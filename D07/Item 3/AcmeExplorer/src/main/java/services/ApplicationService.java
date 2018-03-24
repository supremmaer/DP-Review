
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Message;
import domain.Trip;

import javax.transaction.Transactional;

@Service
@Transactional
public class ApplicationService {

	//Managed Repository
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;


	//Constructors
	public ApplicationService() {
		super();
	}

	//CRUDS methods
	public Application create(final Explorer explorer, final Trip trip) {
		Assert.notNull(explorer);
		Assert.notNull(trip);

		Application result;

		result = new Application();
		result.setExplorer(explorer);
		result.setTrip(trip);
		result.setStatus("PENDING");
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	//12.2
	public Application save(final Application application) {
		Assert.notNull(application);
		Application result;

		if (!this.applicationRepository.exists(application.getId())) {
			application.setMoment(new Date(System.currentTimeMillis() - 1));
			application.setStatus("PENDING");
		}
		result = this.applicationRepository.save(application);
		this.actorService.isSpam(application.getComment(), this.actorService.findByPrincipal());
		this.actorService.isSpam(application.getRejectingReason(), this.actorService.findByPrincipal());
		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));

		this.applicationRepository.delete(application);
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	// Other business methods

	public void changeStatus(final Application application, final String newStatus, final String rejectedReason) {
		Assert.notNull(application);
		Assert.notNull(newStatus);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));
		Assert.isTrue(newStatus.equals("ACCEPTED") || newStatus.equals("CANCELLED") || newStatus.equals("DUE") || newStatus.equals("REJECTED"), "Nuevo estado no válido");

		Message message;
		Manager manager;
		Explorer explorer;
		final Collection<Actor> actors = new HashSet<Actor>();

		application.setStatus(newStatus);
		if (newStatus.equals("REJECTED"))
			application.setRejectingReason(rejectedReason);

		manager = this.applicationRepository.findOne(application.getId()).getTrip().getManager();
		explorer = this.applicationRepository.findOne(application.getId()).getExplorer();
		actors.add(explorer);
		actors.add(manager);
		message = this.messageService.createNotification();
		message.setBody("El estado de la aplicacion " + application + " ha sido modificado a" + newStatus);
		message.setMoment(new Date());
		message.setSubject("Status Changed");
		message.setPriority("HIGH");
		message.setActor(manager);
		message.setActors(actors);

		this.messageService.sendNotification(message);
	}

	//13.3 
	public Application addCreditCard(final Application application, final CreditCard creditCard) {
		Assert.notNull(application);
		Assert.notNull(creditCard);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));
		Assert.isTrue(application.getStatus().equals("DUE"));

		Application result;
		Actor actor;
		Explorer explorer;
		Collection<Actor> actors;
		Manager manager;
		Message message;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Explorer);

		explorer = (Explorer) actor;

		Assert.isTrue(application.getExplorer().getId() == explorer.getId());

		application.setCreditCard(creditCard);
		application.setStatus("ACCEPTED");

		result = this.applicationRepository.save(application);

		actors = new HashSet<Actor>();
		manager = application.getTrip().getManager();
		actors.add(explorer);
		actors.add(manager);
		message = this.messageService.createNotification();
		message.setBody("El estado de la aplicacion " + application + " ha sido modificado a" + application.getStatus());
		message.setMoment(new Date());
		message.setSubject("Status Changed");
		message.setPriority("HIGH");
		message.setActor(manager);
		message.setActors(actors);

		this.messageService.sendNotification(message);

		return result;

	}
	public Collection<Application> findByTrip(final Trip trip) {
		Assert.notNull(trip);

		Collection<Application> result;

		result = this.applicationRepository.findbyTripID(trip.getId());

		return result;

	}

	public Collection<Application> findByManager(final Manager manager) {
		Assert.notNull(manager);

		Collection<Application> result;

		result = this.applicationRepository.findByManagerID(manager.getId());

		return result;
	}
	public Collection<Application> findAcceptedByExplorer(final Explorer explorer) {
		Assert.notNull(explorer);

		Collection<Application> result;

		result = this.applicationRepository.findAcceptedByExplorerID(explorer.getId());

		return result;
	}

	//13.2 
	public Collection<Application> findByExplorer(final Explorer explorer) {
		Assert.notNull(explorer);

		Collection<Application> result;

		result = this.applicationRepository.findbyExplorerIDGrouped(explorer.getId());

		return result;
	}

	//14.6.1
	public Map<String, Double> getApplicationPerTripData() {
		final Map<String, Double> result = new HashMap<>();
		result.put("average", this.applicationRepository.avgApplicationsPerTrip());
		result.put("minimum", Double.valueOf(this.applicationRepository.minApplicationsPerTrip()));
		result.put("maximum", Double.valueOf(this.applicationRepository.maxApplicationsPerTrip()));
		result.put("standardDeviation", this.applicationRepository.standardDeviationPerTrip());
		return result;
	}
	//14.6.5
	public Map<String, Double> getStatusRatiosApplicationData() {
		final Map<String, Double> result = new HashMap<>();
		result.put("PENDING", this.applicationRepository.applicationsByStatusRatio("PENDING"));
		result.put("REJECTED", this.applicationRepository.applicationsByStatusRatio("REJECTED"));
		result.put("DUE", this.applicationRepository.applicationsByStatusRatio("DUE"));
		result.put("ACCEPTED", this.applicationRepository.applicationsByStatusRatio("ACCEPTED"));
		result.put("CANCELLED", this.applicationRepository.applicationsByStatusRatio("CANCELLED"));
		return result;
	}

	public Boolean getApplicationNotRejectedByTripAndExplorer(final int tripId, final int explorerId) {
		Boolean result;
		Integer n;

		result = false;
		n = this.applicationRepository.countApplicationsNotRejected(tripId, explorerId);
		if (n == 0)
			result = true;

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Boolean getApplicationsAccepted(final int tripId, final int explorerId) {
		Boolean result;
		Integer n;

		result = false;
		n = this.applicationRepository.countApplicationsAccepted(tripId, explorerId);
		if (n > 0)
			result = true;

		return result;
	}
}
