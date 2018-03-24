
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordTest extends AbstractTest {

	@Autowired
	private EndorserRecordService	endorserRecordService;


	@Test
	public void testFindAll() {
		Collection<EndorserRecord> endorserRecord;

		endorserRecord = this.endorserRecordService.findAll();
		Assert.isTrue(!endorserRecord.isEmpty());
	}

	@Test
	public void testSave() {
		EndorserRecord endorserRecord, saved;
		Collection<EndorserRecord> enRecords;

		super.authenticate("ranger1");
		endorserRecord = new EndorserRecord();
		endorserRecord.setComment("TestComment");
		endorserRecord.setEmail("test@email.com");
		endorserRecord.setFullNameEndorser("Test Full Name");
		endorserRecord.setLinkedInProfile("http://testlink.com");
		endorserRecord.setPhone("TestPhone");

		saved = this.endorserRecordService.save(endorserRecord);
		enRecords = this.endorserRecordService.findAll();
		Assert.isTrue(enRecords.contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		EndorserRecord endorserRecord, saved;
		Collection<EndorserRecord> enRecords;

		endorserRecord = new EndorserRecord();
		endorserRecord.setComment("TestComment");
		endorserRecord.setEmail("test@email.com");
		endorserRecord.setFullNameEndorser("Test Full Name");
		endorserRecord.setLinkedInProfile("http://testlink.com");
		endorserRecord.setPhone("TestPhone");

		saved = this.endorserRecordService.save(endorserRecord);

		this.endorserRecordService.delete(saved);
		enRecords = this.endorserRecordService.findAll();
		Assert.isTrue(!enRecords.contains(saved));
	}
}
