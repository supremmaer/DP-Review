
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Actor;
import domain.Curricula;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService					rangerService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private CurriculaService				curriculaService;


	//Constructor
	public ProfessionalRecordService() {
		super();
	}

	//CRUD methods

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		this.checkSpamWord(professionalRecord);
		if (professionalRecord.getId() != 0)
			this.checkPrincipal(professionalRecord);

		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(professionalRecord);

		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);
		Assert.isTrue(this.professionalRecordRepository.exists(professionalRecord.getId()));

		this.professionalRecordRepository.delete(professionalRecord);
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;

		result = this.professionalRecordRepository.findAll();

		return result;

	}
	public ProfessionalRecord findOne(final int id) {
		Assert.isTrue(id != 0);
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(id);

		return result;
	}

	//Checker
	private void checkPrincipal(final ProfessionalRecord professionalRecord) {
		Actor actor;
		final Ranger owner;
		Ranger ranger;
		Curricula curricula;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Ranger);

		ranger = (Ranger) actor;
		curricula = this.curriculaService.findByProfessionalRecord(professionalRecord);
		owner = this.rangerService.findByCurricula(curricula);
		Assert.isTrue(ranger.getId() == owner.getId());

	}

	private void checkSpamWord(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();

		this.actorService.isSpam(professionalRecord.getAttachment(), ranger);
		this.actorService.isSpam(professionalRecord.getComment(), ranger);
		this.actorService.isSpam(professionalRecord.getNameCompany(), ranger);
		this.actorService.isSpam(professionalRecord.getRole(), ranger);
	}
}
