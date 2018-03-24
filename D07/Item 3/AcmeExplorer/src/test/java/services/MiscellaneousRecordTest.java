
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecord;


	@Test
	public void testFindAll() {
		Collection<MiscellaneousRecord> miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecord.findAll();
		Assert.isTrue(!miscellaneousRecord.isEmpty());
	}

	@Test
	public void testSave() {
		MiscellaneousRecord miscellaneousRecord, saved;
		Collection<MiscellaneousRecord> mRecords;

		super.authenticate("ranger1");
		miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setComment("Test Comment");
		miscellaneousRecord.setLinkAttachment("http://attachment.com");
		miscellaneousRecord.setTitle("Test title");
		saved = this.miscellaneousRecord.save(miscellaneousRecord);
		mRecords = this.miscellaneousRecord.findAll();
		Assert.isTrue(mRecords.contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		MiscellaneousRecord miscellaneousRecord, saved;
		Collection<MiscellaneousRecord> mRecords;

		miscellaneousRecord = new MiscellaneousRecord();
		miscellaneousRecord.setComment("Test Comment");
		miscellaneousRecord.setLinkAttachment("http://attachment.com");
		miscellaneousRecord.setTitle("Test title");
		saved = this.miscellaneousRecord.save(miscellaneousRecord);

		this.miscellaneousRecord.delete(saved);
		mRecords = this.miscellaneousRecord.findAll();
		Assert.isTrue(!mRecords.contains(saved));
	}
}
