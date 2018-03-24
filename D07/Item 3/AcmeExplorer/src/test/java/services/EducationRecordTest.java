
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordTest extends AbstractTest {

	@Autowired
	private EducationRecordService	educationRecordService;


	@Test
	public void testFindAll() {
		Collection<EducationRecord> educationRecord;

		educationRecord = this.educationRecordService.findAll();
		Assert.isTrue(!educationRecord.isEmpty());
	}

	@Test
	public void testSave() {
		EducationRecord educationRecord, saved;
		Collection<EducationRecord> eRecords;

		super.authenticate("ranger1");
		educationRecord = new EducationRecord();
		educationRecord.setAttachment("http://attachment.com/");
		educationRecord.setComment("Comment");
		educationRecord.setDiploma("Test Diploma");
		educationRecord.setEndDate(new Date(System.currentTimeMillis() + 1));
		educationRecord.setStartDate(new Date(System.currentTimeMillis() - 1));
		educationRecord.setInstitution("Test Institution");
		saved = this.educationRecordService.save(educationRecord);
		eRecords = this.educationRecordService.findAll();
		Assert.isTrue(eRecords.contains(saved));
		super.unauthenticate();
	}
	@Test
	public void testDelete() {
		EducationRecord educationRecord, saved;
		Collection<EducationRecord> updated;

		educationRecord = new EducationRecord();
		educationRecord.setAttachment("http://attachment.com/");
		educationRecord.setComment("Comment");
		educationRecord.setDiploma("Test Diploma");
		educationRecord.setEndDate(new Date(System.currentTimeMillis() + 1));
		educationRecord.setStartDate(new Date(System.currentTimeMillis() - 1));
		educationRecord.setInstitution("Test Institution");
		saved = this.educationRecordService.save(educationRecord);

		this.educationRecordService.delete(saved);
		updated = this.educationRecordService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
