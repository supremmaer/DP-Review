
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

import utilities.AbstractTest;
import domain.Actor;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private PersonalRecordService	personalRecordService;


	@Test
	public void testFindAll() {
		Collection<Curricula> curriculas;

		curriculas = this.curriculaService.findAll();
		Assert.isTrue(!curriculas.isEmpty());
	}

	@Test
	public void testSave() {
		Curricula curricula, savedC;
		Collection<Curricula> updated;
		PersonalRecord personalRecord, savedPR;
		Collection<ProfessionalRecord> prRs;
		Collection<EndorserRecord> enRs;
		Collection<EducationRecord> edRs;
		Collection<MiscellaneousRecord> mRs;

		super.authenticate("ranger2");
		prRs = Collections.emptySet();
		enRs = Collections.emptySet();
		edRs = Collections.emptySet();
		mRs = Collections.emptySet();
		personalRecord = new PersonalRecord();
		personalRecord.setEmail("correo@correo.es");
		personalRecord.setFullName("Test name");
		personalRecord.setPhone("666666666");
		personalRecord.setPhoto("http://foto.com/foto.jpg");
		personalRecord.setUrl("http://url.com");
		savedPR = this.personalRecordService.save(personalRecord);
		curricula = new Curricula();
		curricula.setTicker("171110-AAAA");
		curricula.setPersonalRecord(savedPR);
		curricula.setProfessionalRecords(prRs);
		curricula.setEndorserRecords(enRs);
		curricula.setEducationRecords(edRs);
		curricula.setMiscellaneousRecords(mRs);
		savedC = this.curriculaService.save(curricula);
		updated = this.curriculaService.findAll();
		Assert.isTrue(updated.contains(savedC));
		super.authenticate(null);
	}

	@Test
	public void testFindByRanger() {
		Actor actor;
		Curricula curricula;

		super.authenticate("ranger1");
		actor = this.actorService.findByPrincipal();
		curricula = this.curriculaService.findByRanger((Ranger) actor);
		Assert.isTrue(curricula != null);
	}

}
