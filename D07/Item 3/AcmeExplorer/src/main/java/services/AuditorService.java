
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.LoginService;
import security.UserAccount;
import domain.AuditRecord;
import domain.Auditor;
import domain.Folder;
import domain.Note;

@Service
@Transactional
public class AuditorService {

	//Managed Repository

	@Autowired
	private AuditorRepository	auditorRepository;

	//Supporting Services
	@Autowired
	private FolderService		folderService;


	//Constructors

	public AuditorService() {
		super();
	}

	//CRUD methods

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);

		Auditor result;
		Collection<Folder> folders;

		if (auditor.getId() == 0) {
			folders = this.folderService.initSystemFolders();
			auditor.setFolders(folders);
		}

		result = this.auditorRepository.save(auditor);

		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);
		Assert.isTrue(this.auditorRepository.exists(auditor.getId()));

		this.auditorRepository.delete(auditor);
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;

		result = this.auditorRepository.findAll();

		return result;
	}

	//Other business methods

	public Auditor findByPrincipal() {
		Auditor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	//11.2
	public Auditor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Auditor result;

		result = this.auditorRepository.findbyUserAccountID(userAccount.getId());

		return result;
	}

	public Auditor create() {
		Auditor result;

		result = new Auditor();
		result.setAuditRecords(new HashSet<AuditRecord>());
		result.setNotes(new HashSet<Note>());

		return result;
	}
}
