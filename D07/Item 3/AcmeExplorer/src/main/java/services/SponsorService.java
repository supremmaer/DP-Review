
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.UserAccount;
import domain.Folder;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	//Managed Repository
	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private FolderService		folderService;


	//Constructor
	public SponsorService() {
		super();
	}

	//CRUD methods

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		Sponsor result;
		Collection<Folder> folders;

		if (sponsor.getId() == 0) {
			folders = this.folderService.initSystemFolders();
			sponsor.setFolders(folders);
		}

		result = this.sponsorRepository.save(sponsor);

		return result;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		Assert.isTrue(this.sponsorRepository.exists(sponsor.getId()));

		this.sponsorRepository.delete(sponsor);
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();

		return result;
	}

	public Sponsor create() {
		Sponsor result;

		result = new Sponsor();
		result.setSponsorships(new HashSet<Sponsorship>());

		return result;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor sponsor;

		sponsor = this.sponsorRepository.findOne(sponsorId);

		return sponsor;
	}

}
