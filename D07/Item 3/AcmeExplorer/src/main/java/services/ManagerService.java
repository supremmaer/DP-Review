
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Folder;
import domain.Manager;
import domain.Note;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	//Managed Repository
	@Autowired
	private ManagerRepository		managerRepository;

	//Supporting services
	@Autowired
	private TripService				tripService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private NoteService				noteService;

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private FolderService			folderService;


	//Constructor
	public ManagerService() {
		super();
	}

	//CRUDS methods

	public Manager save(final Manager manager) {
		Assert.notNull(manager);

		Manager result;
		Collection<Folder> folders;

		if (manager.getId() == 0) {
			folders = this.folderService.initSystemFolders();
			manager.setFolders(folders);
		}

		result = this.managerRepository.save(manager);

		return result;
	}

	public void delete(final Manager manager) {
		Assert.notNull(manager);
		Assert.isTrue(manager.getId() != 0);
		Assert.isTrue(this.managerRepository.exists(manager.getId()));

		this.managerRepository.delete(manager);
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;

		result = this.managerRepository.findAll();

		return result;

	}

	//Other business method

	//12.1
	public Collection<Trip> getTrips() {
		Collection<Trip> result;

		result = this.tripService.findAll();

		return result;
	}

	//12.3 y 12.1
	public void deleteTrip(final Trip trip) {
		Assert.notNull(trip);

		final Date moment;

		moment = new Date();

		Assert.isTrue(trip.getStartDate().after(moment));

		this.tripService.delete(trip);
	}

	//12.1
	public Trip createTrip() {
		Trip result;

		result = new Trip();

		return result;
	}

	//12.1
	public Trip saveTrip(final Trip trip) {
		Trip result;

		Assert.notNull(trip);
		Assert.isTrue(trip.getStartDate().after(trip.getPublicationDate()));
		Assert.isTrue(trip.getStartDate().before(trip.getEndDate()));

		result = this.tripService.save(trip);

		return result;
	}
	//12.2
	public Application saveApplication(final Application application) {
		Assert.notNull(application);

		Application result;
		Collection<Application> applications;
		Manager manager;

		manager = this.findByPrincipal();
		applications = this.applicationService.findByManager(manager);

		Assert.isTrue(applications.contains(application));

		result = this.applicationService.save(application);

		return result;
	}
	//32
	public Collection<Note> findNotesOwnTrips() {
		final Manager manager;
		Collection<Note> result;

		manager = this.findByPrincipal();

		Assert.notNull(manager);

		result = this.noteService.findByManager(manager);

		return result;
	}

	public void writeReplyToNote(final String reply, final Note note) {
		Assert.notNull(note);
		Assert.notNull(reply);

		note.setReply(reply);
		note.setReplyMoment(new Date());

		this.noteService.save(note);
	}

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.managerRepository.findbyUserAccountID(userAccount.getId());
		return result;
	}

	public Collection<SurvivalClass> findByManagedTrips() {
		Manager manager;
		Collection<SurvivalClass> result;
		boolean ok = false;

		manager = this.findByPrincipal();
		if (manager instanceof Manager)
			ok = true;
		Assert.isTrue(ok);
		result = this.survivalClassService.findByManager(manager);

		return result;
	}

	public Collection<Manager> getAllSuspiciousManagers() {
		final Collection<Manager> result = this.managerRepository.suspiciousManagers();
		return result;
	}

	//35.4.7
	public Double ratioSuspiciousManagers() {
		final Double result = this.managerRepository.ratioSuspiciousManager();
		return result;
	}

	public Manager create() {
		Manager result;

		result = new Manager();
		result.setTrips(new HashSet<Trip>());

		return result;
	}

	public Manager findOne(final int managerId) {
		Manager result;

		result = this.managerRepository.findOne(managerId);

		return result;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Manager result;

		result = this.managerRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

}
