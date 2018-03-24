
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Actor;
import domain.Curricula;
import domain.EndorserRecord;
import domain.Ranger;

@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;
	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CurriculaService			curriculaService;


	//Constructor
	public EndorserRecordService() {
		super();
	}

	//CRUD methods

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		if (endorserRecord.getId() != 0)
			this.checkPrincipal(endorserRecord);

		EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);
		this.actorService.isSpam(endorserRecord.getComment(), this.actorService.findByPrincipal());
		this.actorService.isSpam(endorserRecord.getEmail(), this.actorService.findByPrincipal());
		this.actorService.isSpam(endorserRecord.getFullNameEndorser(), this.actorService.findByPrincipal());
		this.actorService.isSpam(endorserRecord.getLinkedInProfile(), this.actorService.findByPrincipal());
		this.actorService.isSpam(endorserRecord.getPhone(), this.actorService.findByPrincipal());
		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);
		Assert.isTrue(this.endorserRecordRepository.exists(endorserRecord.getId()));

		this.endorserRecordRepository.delete(endorserRecord);
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;

		result = this.endorserRecordRepository.findAll();

		return result;
	}

	public EndorserRecord findOne(final int Id) {
		Assert.isTrue(Id != 0);
		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(Id);

		return result;

	}
	//Checker
	public void checkPrincipal(final EndorserRecord endorserRecord) {
		Actor actor;
		final Ranger owner;
		Ranger ranger;
		Curricula curricula;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Ranger);

		ranger = (Ranger) actor;
		curricula = this.curriculaService.findByEndorserRecord(endorserRecord);
		owner = this.rangerService.findByCurricula(curricula);
		Assert.isTrue(ranger.getId() == owner.getId());

	}

	public void checkSpamWord(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		this.actorService.isSpam(endorserRecord.getComment(), ranger);
		this.actorService.isSpam(endorserRecord.getEmail(), ranger);
		this.actorService.isSpam(endorserRecord.getFullNameEndorser(), ranger);
		this.actorService.isSpam(endorserRecord.getLinkedInProfile(), ranger);

	}
}
