
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
import domain.LegalText;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	@Autowired
	private LegalTextService	legalTextService;


	@Test
	public void testFindAll() {
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextService.findAll();
		Assert.isTrue(!legalTexts.isEmpty());
	}

	@Test
	public void testSave() {
		LegalText legalText;
		LegalText saved;
		Collection<LegalText> updated;
		Collection<Trip> trips;

		legalText = new LegalText();
		super.authenticate("admin1");
		legalText.setDraftMode(false);
		legalText.setTitle("holo");
		legalText.setBody("lel");
		legalText.setLaws("leyes");
		trips = Collections.emptySet();
		legalText.setTrips(trips);

		saved = this.legalTextService.save(legalText);
		updated = this.legalTextService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

	}

	//DONE testEdit
	@SuppressWarnings("unused")
	@Test
	public void testEdit() {
		LegalText legalText;
		LegalText saved;
		LegalText edited;
		Boolean exception;
		Collection<Trip> trips;

		exception = false;
		legalText = new LegalText();
		super.authenticate("admin1");
		legalText.setDraftMode(false);
		legalText.setTitle("holo");
		legalText.setBody("lel");
		legalText.setLaws("leyes");
		trips = Collections.emptySet();
		legalText.setTrips(trips);

		saved = this.legalTextService.save(legalText);

		try {
			saved.setTitle("edited");
			edited = this.legalTextService.save(saved);
		} catch (final Exception e) {
			exception = true;

		} finally {
			Assert.isTrue(exception);
			super.authenticate(null);
		}

	}

	@Test
	public void testDelete() {
		LegalText legalText, saved;
		Collection<LegalText> updated;
		Collection<Trip> trips;

		legalText = new LegalText();
		legalText.setDraftMode(true);
		legalText.setTitle("holo");
		legalText.setBody("lel");
		legalText.setLaws("leyes");
		trips = Collections.emptySet();
		legalText.setTrips(trips);

		super.authenticate("admin1");
		saved = this.legalTextService.save(legalText);
		updated = this.legalTextService.findAll();
		this.legalTextService.delete(saved);
		updated = this.legalTextService.findAll();
		Assert.isTrue(!updated.contains(saved));
		super.authenticate(null);
	}
}
