
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Manager;
import domain.Ranger;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository ----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService			folderService;
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private RangerService			rangerService;


	//Constructors
	public AdministratorService() {
		super();
	}
	//CRUD methods ----------------------------------------------------

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		Administrator result;
		Collection<Folder> folders;

		if (administrator.getId() == 0) {
			folders = this.folderService.initSystemFolders();
			administrator.setFolders(folders);
		}
		result = this.administratorRepository.save(administrator);
		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		Assert.isTrue(this.administratorRepository.exists(administrator.getId()));

		this.administratorRepository.delete(administrator);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	//Other business methods ----------------------------------------------------

	//DONE: Revisar delete legal text

	//35.1
	public Map<String, Actor> getSuspicious() {
		final Map<String, Actor> result = new HashMap<>();
		final Collection<Manager> sm = this.managerService.getAllSuspiciousManagers();
		final Collection<Ranger> sr = this.rangerService.getAllSuspiciousRangers();
		for (final Manager m : sm)
			result.put("Manager", m);
		for (final Ranger r : sr)
			result.put("Ranger", r);
		return result;
	}

	public Administrator create() {
		Administrator result;

		result = new Administrator();

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.administratorRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}
}
