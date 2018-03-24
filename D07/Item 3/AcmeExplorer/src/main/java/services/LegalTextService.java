
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.Actor;
import domain.Administrator;
import domain.LegalText;
import domain.Trip;

@Service
@Transactional
public class LegalTextService {

	//Managed Repository
	@Autowired
	private LegalTextRepository	legalTextRepository;

	//Supporting Services
	@Autowired
	private ActorService		actorService;


	//Constructor
	public LegalTextService() {
		super();
	}

	//CRUDS methods

	public Collection<LegalText> findAll() {
		Collection<LegalText> result;

		result = this.legalTextRepository.findAll();

		return result;
	}

	public LegalText create() {
		LegalText result;

		result = new LegalText();
		result.setDraftMode(new Boolean(true));
		result.setTrips(new HashSet<Trip>());
		result.setMoment(new Date());

		return result;
	}

	//14.2
	public LegalText save(final LegalText legalText) {
		Actor actor;
		LegalText result;
		Date moment;

		Assert.notNull(legalText);
		actor = this.actorService.findByPrincipal();
		Assert.isTrue(this.isSaveable(legalText));
		Assert.isTrue(actor instanceof Administrator);

		if (!this.legalTextRepository.exists(legalText.getId())) {
			moment = new Date(System.currentTimeMillis() - 1);
			legalText.setMoment(moment);
		}

		result = this.legalTextRepository.save(legalText);

		this.actorService.isSpam(legalText.getBody(), this.actorService.findByPrincipal());
		this.actorService.isSpam(legalText.getLaws(), this.actorService.findByPrincipal());
		this.actorService.isSpam(legalText.getTitle(), this.actorService.findByPrincipal());

		return result;
	}
	public void delete(final LegalText legalText) {
		Assert.notNull(legalText);
		Assert.isTrue(legalText.getId() != 0);
		Assert.isTrue(this.legalTextRepository.exists(legalText.getId()));
		Assert.isTrue(legalText.isDraftMode());

		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Administrator);

		this.legalTextRepository.delete(legalText.getId());
	}

	//Checker
	public boolean isSaveable(final LegalText legalText) {
		Assert.notNull(legalText);

		boolean result;

		result = legalText.isDraftMode() || !this.legalTextRepository.exists(legalText.getId());

		return result;
	}

	//14.6.11
	public Collection<Object[]> timesReferenced() {
		Collection<Object[]> result;
		result = this.legalTextRepository.findLegalTextsAndNumberOfReferences();
		return result;
	}

	public LegalText findOne(final int legalTextId) {
		LegalText result;

		result = this.legalTextRepository.findOne(legalTextId);

		return result;
	}

	public void setFinal(final LegalText legalText) {
		Assert.notNull(legalText);

		LegalText savable;

		savable = this.legalTextRepository.findOne(legalText.getId());
		Assert.isTrue(savable != null);
		Assert.isTrue(savable.isDraftMode());

		savable.setDraftMode(false);
		this.legalTextRepository.save(legalText);
	}

	public Collection<LegalText> findFinal() {
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextRepository.findFinal();

		return legalTexts;
	}
}
