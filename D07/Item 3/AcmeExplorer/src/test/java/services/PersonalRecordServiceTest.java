
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	@Autowired
	private PersonalRecordService	personalRecordService;


	@Test
	public void testFindAll() {
		Collection<PersonalRecord> personalRecord;

		personalRecord = this.personalRecordService.findAll();
		Assert.isTrue(!personalRecord.isEmpty());
	}

	@Test
	public void testSave() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> pRecords;

		personalRecord = new PersonalRecord();
		personalRecord.setEmail("correo@correo.es");
		personalRecord.setFullName("Test name");
		personalRecord.setPhone("666666666");
		personalRecord.setPhoto("http://foto.com/foto.jpg");
		personalRecord.setUrl("http://url.com");
		saved = this.personalRecordService.save(personalRecord);
		pRecords = this.personalRecordService.findAll();
		Assert.isTrue(pRecords.contains(saved));
	}

	public void testDelete() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> updated;

		personalRecord = new PersonalRecord();
		personalRecord.setEmail("correo@correo.es");
		personalRecord.setFullName("Test name");
		personalRecord.setPhone("666666666");
		personalRecord.setPhoto("http://foto.com/foto.jpg");
		personalRecord.setUrl("http://url.com");
		saved = this.personalRecordService.save(personalRecord);

		this.personalRecordService.delete(saved);
		updated = this.personalRecordService.findAll();
		Assert.isTrue(!updated.contains(saved));

	}

}
