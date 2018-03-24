
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Actor;
import domain.Curricula;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RangerService					rangerService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private CurriculaService				curriculaService;


	//Constructor
	public MiscellaneousRecordService() {
		super();
	}

	//CRUD methods

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		if (miscellaneousRecord.getId() != 0)
			this.checkPrincipal(miscellaneousRecord);
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		this.actorService.isSpam(miscellaneousRecord.getComment(), this.actorService.findByPrincipal());
		this.actorService.isSpam(miscellaneousRecord.getLinkAttachment(), this.actorService.findByPrincipal());
		this.actorService.isSpam(miscellaneousRecord.getTitle(), this.actorService.findByPrincipal());

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);
		Assert.isTrue(this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()));

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);

	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();

		return result;

	}

	public MiscellaneousRecord findOne(final int Id) {
		Assert.isTrue(Id != 0);
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(Id);

		return result;
	}
	//Checker
	public void checkPrincipal(final MiscellaneousRecord miscellaneousRecord) {
		Actor actor;
		final Ranger owner;
		Ranger ranger;
		Curricula curricula;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Ranger);

		ranger = (Ranger) actor;
		curricula = this.curriculaService.findByMiscellaneousRecord(miscellaneousRecord);
		owner = this.rangerService.findByCurricula(curricula);
		Assert.isTrue(ranger.getId() == owner.getId());

	}
	public void checkSpamWord(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		this.actorService.isSpam(miscellaneousRecord.getComment(), ranger);
		this.actorService.isSpam(miscellaneousRecord.getLinkAttachment(), ranger);
		this.actorService.isSpam(miscellaneousRecord.getTitle(), ranger);

	}
}
