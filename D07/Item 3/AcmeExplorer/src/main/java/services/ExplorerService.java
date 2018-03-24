
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Contact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.Story;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class ExplorerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ExplorerRepository	explorerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private TripService			tripService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public ExplorerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Explorer create() {
		Explorer result;

		result = new Explorer();
		result.setApplications(new HashSet<Application>());
		result.setContacts(new HashSet<Contact>());
		result.setStories(new HashSet<Story>());
		result.setSurvivalClasses(new HashSet<SurvivalClass>());

		return result;
	}

	//10.1
	public Explorer save(final Explorer explorer) {
		Assert.notNull(explorer);

		Explorer result;
		Collection<Folder> folders;

		if (explorer.getId() == 0) {
			folders = this.folderService.initSystemFolders();
			explorer.setFolders(folders);
		}

		result = this.explorerRepository.save(explorer);

		return result;
	}

	public void delete(final Explorer explorer) {
		Assert.notNull(explorer);
		Assert.isTrue(explorer.getId() != 0);
		Assert.isTrue(this.explorerRepository.exists(explorer.getId()));

		this.explorerRepository.delete(explorer);
	}

	public Collection<Explorer> findAll() {
		Collection<Explorer> result;

		result = this.explorerRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

	//11.2
	public Explorer findByPrincipal() {
		Explorer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	//11.2
	public Explorer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Explorer result;

		result = this.explorerRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

	//13.1
	public Application ApplyForTrip(final Application application) {
		Application result;
		Assert.notNull(application);
		Assert.isTrue(application.getExplorer().equals(this.findByPrincipal()));
		result = this.applicationService.save(application);
		return result;
	}

	//13.4
	public void cancelApplication(final Application application) {
		Date fechaCancelacion;
		Trip trip;

		Assert.notNull(application);

		trip = application.getTrip();
		fechaCancelacion = new Date();

		Assert.isTrue(fechaCancelacion.before(trip.getStartDate()));

		application.setStatus("CANCELLED");

		this.applicationService.save(application);

	}

	//34
	public Collection<Trip> findByOwnFinder() {
		Explorer explorer;
		Collection<Trip> result;
		Finder finder;

		explorer = this.findByPrincipal();
		finder = this.finderService.findByExplorer(explorer);
		result = this.tripService.findByFinder(finder);

		return result;

	}

	//44.1
	public Explorer enrolSurvivalClass(final SurvivalClass survivalClass) {
		Assert.notNull(survivalClass);
		Explorer explorer, saved;

		explorer = this.findByPrincipal();
		explorer.addSurvivalClasses(survivalClass);
		saved = this.explorerRepository.save(explorer);
		return saved;
	}

}
