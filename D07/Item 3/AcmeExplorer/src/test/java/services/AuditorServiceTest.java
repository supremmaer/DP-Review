
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.AuditRecord;
import domain.Auditor;
import domain.Message;
import domain.Note;
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	@Autowired
	private AuditorService	auditorService;


	@Test
	public void testFindAll() {
		Collection<Auditor> auditors;

		auditors = this.auditorService.findAll();
		Assert.isTrue(!auditors.isEmpty());
	}

	@Test
	public void testSave() {
		Auditor auditor, saved;
		Collection<Auditor> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;
		Collection<Note> notes;
		Collection<AuditRecord> auditRecords;

		notes = Collections.emptySet();
		auditRecords = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		auditor = new Auditor();
		auditor.setAddress("direccion");
		auditor.setEmail("email@email.com");
		auditor.setName("Auditor test name");
		auditor.setPhone("666666666");
		auditor.setSurname("Auditor test surname");
		auditor.setSocialIdentities(si);
		auditor.setMessagesReceived(messagesReceived);
		auditor.setMessagesSent(messagesSent);
		auditor.setUserAccount(LoginService.getPrincipal());
		auditor.setNotes(notes);
		auditor.setAuditRecords(auditRecords);
		saved = this.auditorService.save(auditor);
		updated = this.auditorService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {

		Auditor auditor, saved;
		Collection<Auditor> updated;
		Collection<SocialIdentity> si;
		Collection<Message> messagesSent, messagesReceived;
		Collection<Note> notes;
		Collection<AuditRecord> auditRecords;

		notes = Collections.emptySet();
		auditRecords = Collections.emptySet();
		si = Collections.emptySet();
		messagesReceived = Collections.emptySet();
		messagesSent = Collections.emptySet();
		super.authenticate("explorer8");
		auditor = new Auditor();
		auditor.setAddress("direccion");
		auditor.setEmail("email@email.com");
		auditor.setName("Admin test name");
		auditor.setPhone("666666666");
		auditor.setSurname("auditor test surname");
		auditor.setSocialIdentities(si);
		auditor.setMessagesReceived(messagesReceived);
		auditor.setMessagesSent(messagesSent);
		auditor.setUserAccount(LoginService.getPrincipal());
		auditor.setNotes(notes);
		auditor.setAuditRecords(auditRecords);
		saved = this.auditorService.save(auditor);
		updated = this.auditorService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.auditorService.delete(saved);
		updated = this.auditorService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}

	@Test
	public void testFindByPrincipal() {
		Auditor auditor;

		super.authenticate("auditor1");
		auditor = this.auditorService.findByPrincipal();
		super.authenticate(null);
		Assert.isTrue(auditor != null);
	}

	@Test
	public void testFindByUserAccount() {
		Auditor auditor;
		UserAccount userAccount;

		super.authenticate("auditor1");
		userAccount = LoginService.getPrincipal();
		auditor = this.auditorService.findByUserAccount(userAccount);
		super.authenticate(null);
		Assert.isTrue(auditor != null);
	}
}
