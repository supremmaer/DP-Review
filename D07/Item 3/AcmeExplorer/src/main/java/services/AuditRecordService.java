
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRecordRepository;
import domain.Actor;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditRecordService {

	//Managed Repository	
	@Autowired
	private AuditRecordRepository	auditRecordRepository;

	//Supporting Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private TripService				tripService;


	//Constructors
	public AuditRecordService() {
		super();
	}

	//CRUD methods

	public AuditRecord findOne(final Integer i) {
		return this.auditRecordRepository.findOne(i);
	}

	public AuditRecord create() {
		AuditRecord result;
		Actor actor;
		Collection<String> attachments;
		boolean draftMode;
		Date moment;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor != null);
		Assert.isTrue(actor instanceof Auditor);

		draftMode = true;
		moment = new Date();
		attachments = Collections.emptySet();
		result = new AuditRecord();
		result.setAuditor((Auditor) actor);
		result.setAttachments(attachments);
		result.setMoment(moment);
		result.setDraftMode(draftMode);
		return result;
	}

	public AuditRecord save(final AuditRecord auditRecord) {
		Assert.notNull(auditRecord);
		Assert.isTrue(this.isSaveable(auditRecord));

		final AuditRecord result;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Auditor);
		Assert.isTrue(actor.getId() == auditRecord.getAuditor().getId());

		auditRecord.setMoment(new Date(System.currentTimeMillis() - 1));
		this.actorService.isSpam(auditRecord.getDescription(), this.actorService.findByPrincipal());
		this.actorService.isSpam(auditRecord.getTitle(), this.actorService.findByPrincipal());
		for (final String s : auditRecord.getAttachments())
			this.actorService.isSpam(s, this.actorService.findByPrincipal());

		result = this.auditRecordRepository.save(auditRecord);

		return result;
	}

	public AuditRecord saveDraftMode(final AuditRecord auditRecord) {
		Assert.notNull(auditRecord);
		Assert.isTrue(this.auditRecordRepository.exists(auditRecord.getId()));

		AuditRecord result;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Auditor);
		Assert.isTrue(actor.getId() == auditRecord.getAuditor().getId());

		auditRecord.setMoment(new Date(System.currentTimeMillis() - 1));
		result = this.auditRecordRepository.save(auditRecord);

		return result;
	}

	public void delete(final AuditRecord auditRecord) {
		Assert.notNull(auditRecord);
		Assert.isTrue(auditRecord.getId() != 0);
		Assert.isTrue(auditRecord.getDraftMode());

		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Auditor);

		this.auditRecordRepository.delete(auditRecord);
	}

	public Collection<AuditRecord> findAll() {
		Collection<AuditRecord> result;

		result = this.auditRecordRepository.findAll();

		return result;
	}

	public AuditRecord findByTrip(final Trip trip) {
		Assert.notNull(trip);

		AuditRecord result;

		result = this.auditRecordRepository.findbyTripID(trip.getId());

		return result;
	}

	public Collection<AuditRecord> findByAuditor(final Auditor auditor) {
		Assert.notNull(auditor);

		Collection<AuditRecord> result;

		result = this.auditRecordRepository.findbyAuditorID(auditor.getId());

		return result;
	}

	//Checker
	private boolean isSaveable(final AuditRecord auditRecord) {
		Assert.notNull(auditRecord);

		boolean result;

		result = auditRecord.isDraftMode() || !this.auditRecordRepository.exists(auditRecord.getId());

		return result;

	}

	//Other Methods

	public Double averageAuditRecordsperTrip() {
		final Double res = this.auditRecordRepository.averageAuditRecordsperTrip();

		return res;
	}
	//35.4.2
	public Integer maxauditRecordperTrip() {
		Integer result = 0;
		final Collection<AuditRecord> aux = this.findAll();
		if (aux.size() > 0)
			result = 1;
		return result;
	}
	public Integer minauditRecordperTrip() {
		Integer result = 1;
		final Collection<AuditRecord> aux = this.findAll();
		if (aux.size() == 0)
			result = 1;
		return result;
	}

	public Double standardDeviationauditRecordperTrip() {

		// select sqrt(sum(t.auditRecord.size*t.auditRecord.size) / count(t.auditRecord.size) - (avg(t.auditRecord.size) * avg(t.auditRecord.size)))
		final Collection<AuditRecord> aux = this.findAll();
		final Collection<Trip> auxt = this.tripService.findAll();
		final int a = aux.size(), t = auxt.size();
		final Double avg = this.averageAuditRecordsperTrip();
		final Double res = Math.sqrt(((1.0 * a) / t) - (avg * avg));
		return res;
	}

	public Map<String, Double> getAuditRecordPerTripData() {
		final Map<String, Double> result = new HashMap<>();

		result.put("average", this.averageAuditRecordsperTrip());
		result.put("minimum", Double.valueOf(this.minauditRecordperTrip()));
		result.put("maximum", Double.valueOf(this.maxauditRecordperTrip()));
		result.put("standardDeviation", this.standardDeviationauditRecordperTrip());
		return result;
	}

}
