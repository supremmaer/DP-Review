
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreditCardService {

	//Managed Repository
	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;


	//Constructor
	public CreditCardService() {
		super();
	}

	//CRUD methods

	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);

		CreditCard result;

		result = this.creditCardRepository.save(creditCard);
		this.actorService.isSpam(creditCard.getBrandName(), this.actorService.findByPrincipal());
		return result;
	}
	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);

		return result;
	}
}
