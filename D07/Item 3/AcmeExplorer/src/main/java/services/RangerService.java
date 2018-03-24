
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.Folder;
import domain.Ranger;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RangerRepository	rangerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public RangerService() {
		super();
	}

	//CRUD methods ----------------------------------------------------

	public Ranger create() {
		Ranger result;

		result = new Ranger();
		result.setTrips(new HashSet<Trip>());

		return result;
	}

	//10.1 y 14.1
	public Ranger save(final Ranger ranger) {
		Assert.notNull(ranger);

		Ranger result;
		Collection<Folder> folders;

		if (ranger.getId() == 0) {
			folders = this.folderService.initSystemFolders();
			ranger.setFolders(folders);
		}

		result = this.rangerRepository.save(ranger);

		return result;
	}

	public void delete(final Ranger ranger) {
		Assert.notNull(ranger);
		Assert.isTrue(ranger.getId() != 0);
		Assert.isTrue(this.rangerRepository.exists(ranger.getId()));

		this.rangerRepository.delete(ranger);
	}

	public Collection<Ranger> findAll() {
		Collection<Ranger> result;

		result = this.rangerRepository.findAll();

		return result;
	}

	public Ranger findOne(final int id) {
		Ranger result;

		result = this.rangerRepository.findOne(id);

		return result;
	}

	// Other business methods -------------------------------------------------

	//11.2
	public Ranger findByPrincipal() {
		Ranger result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	//11.2
	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Ranger result;

		result = this.rangerRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

	//30.1
	public Ranger findByTrip(final Trip trip) {
		Assert.notNull(trip);

		Ranger result;

		result = this.rangerRepository.findbyTripID(trip.getId());

		return result;
	}
	//31
	public Ranger findByCurricula(final Curricula curricula) {
		Assert.notNull(curricula);

		Ranger result;

		result = this.rangerRepository.findbyCurriculaID(curricula.getId());

		return result;
	}
	//35.1
	public Collection<Ranger> getAllSuspiciousRangers() {
		final Collection<Ranger> result = this.rangerRepository.suspiciousRangers();
		return result;
	}

	//35.4.4
	public Double ratioRangerwithRegisteredCurricula() {
		final Double result = this.rangerRepository.ratioRangerwithRegisteredCurricula();
		return result;
	}

	//35.4.5
	public Double ratioRangerwithEndorsedCurriculum() {
		final Double result = this.rangerRepository.ratioRangerwithEndorsedCurriculum();
		return result;
	}

	//35.4.7

	public Double ratioSuspiciousRangers() {
		final Double result = this.rangerRepository.ratioSuspiciousRanger();
		return result;
	}
}
