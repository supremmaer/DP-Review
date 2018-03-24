
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;
import domain.Ranger;

@Service
@Transactional
public class EducationRecordService {

	//Managed Repository
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CurriculaService			curriculaService;


	//Constructor
	public EducationRecordService() {
		super();
	}

	//CRUD methods

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		if (educationRecord.getId() != 0)
			this.checkPrincipal(educationRecord);
		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);
		this.actorService.isSpam(educationRecord.getAttachment(), this.actorService.findByPrincipal());
		this.actorService.isSpam(educationRecord.getComment(), this.actorService.findByPrincipal());
		this.actorService.isSpam(educationRecord.getDiploma(), this.actorService.findByPrincipal());
		this.actorService.isSpam(educationRecord.getInstitution(), this.actorService.findByPrincipal());

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);
		Assert.isTrue(this.educationRecordRepository.exists(educationRecord.getId()));

		this.educationRecordRepository.delete(educationRecord);
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();

		return result;
	}

	public EducationRecord findOne(final int id) {
		Assert.isTrue(id != 0);
		EducationRecord result;

		result = this.educationRecordRepository.findOne(id);

		return result;
	}

	//Checker

	public void checkPrincipal(final EducationRecord educationRecord) {
		Actor actor;
		final Ranger owner;
		Ranger ranger;
		Curricula curricula;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Ranger);

		ranger = (Ranger) actor;
		curricula = this.curriculaService.findByEducationRecord(educationRecord);
		owner = this.rangerService.findByCurricula(curricula);
		Assert.isTrue(ranger.getId() == owner.getId());

	}
	public void checkSpamWord(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);

		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		this.actorService.isSpam(educationRecord.getAttachment(), ranger);
		this.actorService.isSpam(educationRecord.getComment(), ranger);
		this.actorService.isSpam(educationRecord.getDiploma(), ranger);
		this.actorService.isSpam(educationRecord.getInstitution(), ranger);

	}
}
