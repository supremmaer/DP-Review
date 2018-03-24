
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculaService {

	//Managed Repository
	@Autowired
	private CurriculaRepository			curriculaRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------
	public CurriculaService() {
		super();
	}

	//CRUDS methods
	@SuppressWarnings("unchecked")
	public Curricula create() {
		Curricula result;
		String ticker;
		Collection<EducationRecord> educationRecords;
		Collection<EndorserRecord> endorserRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Collection<ProfessionalRecord> professionalRecords;

		ticker = this.systemConfigurationService.generateTicker();
		result = new Curricula();
		educationRecords = Collections.EMPTY_SET;
		endorserRecords = Collections.EMPTY_SET;
		miscellaneousRecords = Collections.EMPTY_SET;
		professionalRecords = Collections.EMPTY_SET;
		result.setTicker(ticker);
		result.setEducationRecords(educationRecords);
		result.setEndorserRecords(endorserRecords);
		result.setMiscellaneousRecords(miscellaneousRecords);
		result.setProfessionalRecords(professionalRecords);

		return result;
	}

	//31
	public Curricula save(final Curricula curricula) {
		Assert.notNull(curricula);
		if (curricula.getId() != 0)
			this.checkPrincipal(curricula);
		this.checkSpamWord(curricula);

		Curricula result;

		result = this.curriculaRepository.save(curricula);

		return result;
	}

	//31
	public void delete(final Curricula curricula) {
		Assert.notNull(curricula);
		Assert.isTrue(curricula.getId() != 0);
		Assert.isTrue(this.curriculaRepository.exists(curricula.getId()));
		this.checkPrincipal(curricula);

		Ranger owner;

		owner = this.rangerService.findByPrincipal();
		owner.setCurricula(null);
		this.rangerService.save(owner);

		this.curriculaRepository.delete(curricula);
	}

	public Collection<Curricula> findAll() {
		Collection<Curricula> result;

		result = this.curriculaRepository.findAll();

		return result;
	}
	//30.1
	public Curricula findByRanger(final Ranger ranger) {
		Assert.notNull(ranger);

		Curricula result;

		result = this.curriculaRepository.findbyRangerID(ranger.getId());

		return result;
	}

	public Curricula findByEducationRecord(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Curricula result;

		result = this.curriculaRepository.findByEducationRecordID(educationRecord.getId());

		return result;
	}

	public Curricula findByEndorserRecord(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Curricula result;

		result = this.curriculaRepository.findByEndorserRecordID(endorserRecord.getId());

		return result;
	}
	public Curricula findByProfessionalRecord(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Curricula result;

		result = this.curriculaRepository.findByProfessionalRecordID(professionalRecord.getId());

		return result;
	}
	public Curricula findByMiscellaneousRecord(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Curricula result;

		result = this.curriculaRepository.findByMiscellaneousRecordID(miscellaneousRecord.getId());

		return result;
	}
	public Curricula findByPersonalRecord(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		Curricula result;

		result = this.curriculaRepository.findByPersonalRecordID(personalRecord.getId());

		return result;
	}
	// Other business methods -------------------------------------------------

	public void checkPrincipal(final Curricula curricula) {
		Actor actor;
		Ranger owner;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Ranger);

		owner = this.rangerService.findByCurricula(curricula);

		Assert.isTrue(actor.getId() == owner.getId());

	}

	public void checkSpamWord(final Curricula curricula) {
		Assert.notNull(curricula);

		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		this.actorService.isSpam(curricula.getPersonalRecord().getEmail(), ranger);
		this.actorService.isSpam(curricula.getPersonalRecord().getFullName(), ranger);
		this.actorService.isSpam(curricula.getPersonalRecord().getPhoto(), ranger);
		this.actorService.isSpam(curricula.getPersonalRecord().getUrl(), ranger);

	}
}
