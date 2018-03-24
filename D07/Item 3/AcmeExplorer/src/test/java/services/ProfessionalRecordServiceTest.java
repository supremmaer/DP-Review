
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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	@Test
	public void testFindAll() {
		Collection<ProfessionalRecord> professionalRecord;

		professionalRecord = this.professionalRecordService.findAll();
		Assert.isTrue(!professionalRecord.isEmpty());
	}

	@Test
	public void testSave() {
		ProfessionalRecord professionalRecord, saved;
		Collection<ProfessionalRecord> pRecords;

		super.authenticate("ranger1");
		professionalRecord = new ProfessionalRecord();
		professionalRecord.setAttachment("http://attachment.com");
		professionalRecord.setComment("Test Comment");
		professionalRecord.setEndDate(new Date(System.currentTimeMillis() - 1));
		professionalRecord.setNameCompany("Test Company");
		professionalRecord.setRole("Test Role");
		professionalRecord.setStartDate(new Date(System.currentTimeMillis() - 2));

		saved = this.professionalRecordService.save(professionalRecord);
		pRecords = this.professionalRecordService.findAll();
		Assert.isTrue(pRecords.contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		ProfessionalRecord professionalRecord, saved;
		Collection<ProfessionalRecord> pRecords;

		super.authenticate("ranger1");
		professionalRecord = new ProfessionalRecord();
		professionalRecord.setAttachment("http://attachment.com");
		professionalRecord.setComment("Test Comment");
		professionalRecord.setEndDate(new Date(System.currentTimeMillis() - 1));
		professionalRecord.setNameCompany("Test Company");
		professionalRecord.setRole("Test Role");
		professionalRecord.setStartDate(new Date(System.currentTimeMillis() - 2));
		saved = this.professionalRecordService.save(professionalRecord);

		this.professionalRecordService.delete(saved);
		pRecords = this.professionalRecordService.findAll();
		Assert.isTrue(!pRecords.contains(saved));
		super.authenticate(null);
	}
}
