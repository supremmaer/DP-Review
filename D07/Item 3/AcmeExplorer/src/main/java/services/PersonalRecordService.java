
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Actor;
import domain.Curricula;
import domain.PersonalRecord;
import domain.Ranger;

@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private RangerService				rangerService;


	//Constructor
	public PersonalRecordService() {
		super();
	}

	//CRUD methods

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		if (personalRecord.getId() != 0)
			this.checkPrincipal(personalRecord);

		PersonalRecord result;

		result = this.personalRecordRepository.save(personalRecord);

		this.actorService.isSpam(personalRecord.getEmail(), this.actorService.findByPrincipal());
		this.actorService.isSpam(personalRecord.getFullName(), this.actorService.findByPrincipal());
		this.actorService.isSpam(personalRecord.getPhone(), this.actorService.findByPrincipal());
		this.actorService.isSpam(personalRecord.getPhoto(), this.actorService.findByPrincipal());
		this.actorService.isSpam(personalRecord.getUrl(), this.actorService.findByPrincipal());

		return result;
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getId() != 0);
		Assert.isTrue(this.personalRecordRepository.exists(personalRecord.getId()));

		this.personalRecordRepository.delete(personalRecord);
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> result;

		result = this.personalRecordRepository.findAll();

		return result;
	}

	//Checker
	public void checkPrincipal(final PersonalRecord personalRecord) {
		Actor actor;
		final Ranger owner;
		Ranger ranger;
		Curricula curricula;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Ranger);

		ranger = (Ranger) actor;
		curricula = this.curriculaService.findByPersonalRecord(personalRecord);
		owner = this.rangerService.findByCurricula(curricula);
		Assert.isTrue(ranger.getId() == owner.getId());

	}
}
