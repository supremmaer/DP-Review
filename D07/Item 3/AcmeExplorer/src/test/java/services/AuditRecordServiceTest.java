
package services;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditRecordServiceTest extends AbstractTest {

	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private ActorService		actorService;


	@Test
	public void testFindAll() {
		Collection<AuditRecord> auditRecord;

		auditRecord = this.auditRecordService.findAll();
		Assert.isTrue(!auditRecord.isEmpty());
	}

	@Test
	public void testSave() {
		AuditRecord auditRecord, saved;
		Collection<AuditRecord> updated;
		Collection<String> attachments;
		Trip trip;
		Actor actor;
		Auditor auditor;

		super.authenticate("auditor1");
		attachments = new HashSet<String>();
		auditRecord = this.auditRecordService.create();
		auditRecord.setDescription("descripcion");
		auditRecord.setTitle("Titulo");
		auditRecord.setAttachments(attachments);
		auditRecord.setDraftMode(true);
		auditRecord.setMoment(new Date(System.currentTimeMillis() - 1));
		trip = (Trip) this.tripService.findAll().toArray()[1];
		auditRecord.setTrip(trip);
		actor = this.actorService.findByPrincipal();
		auditor = (Auditor) actor;
		//		auditor = this.auditorService.findAll().iterator().next();
		//		auditRecord.setAuditor(auditor);
		auditRecord.setAuditor(auditor);
		saved = this.auditRecordService.save(auditRecord);
		updated = this.auditRecordService.findAll();
		Assert.isTrue(updated.contains(saved));
	}

	@Test
	public void testDelete() {
		AuditRecord auditRecord, saved;
		Collection<AuditRecord> updated;
		Collection<String> attachments;
		Trip trip;
		Actor actor;
		Auditor auditor;

		super.authenticate("auditor1");
		attachments = new HashSet<String>();
		auditRecord = this.auditRecordService.create();
		auditRecord.setDescription("descripcion");
		auditRecord.setTitle("Titulo");
		auditRecord.setAttachments(attachments);
		auditRecord.setDraftMode(true);
		auditRecord.setMoment(new Date(System.currentTimeMillis() - 1));
		trip = (Trip) this.tripService.findAll().toArray()[1];
		auditRecord.setTrip(trip);
		actor = this.actorService.findByPrincipal();
		auditor = (Auditor) actor;
		auditRecord.setAuditor(auditor);

		saved = this.auditRecordService.save(auditRecord);
		this.auditRecordService.delete(saved);
		updated = this.auditRecordService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
