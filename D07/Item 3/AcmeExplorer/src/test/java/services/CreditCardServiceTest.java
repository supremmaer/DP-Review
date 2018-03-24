
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
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService	creditCardService;


	@Test
	public void testFindAll() {
		Collection<CreditCard> creditCard;

		creditCard = this.creditCardService.findAll();
		Assert.isTrue(!creditCard.isEmpty());
	}

	@Test
	public void testSave() {
		CreditCard creditCard, saved;
		Collection<CreditCard> creCards;

		creditCard = new CreditCard();
		creditCard.setBrandName("Test");
		creditCard.setCvvCode(789);
		creditCard.setExpirationMonth(8);
		creditCard.setExpirationYear(2030);
		creditCard.setHolderName("Test Holder Name");
		creditCard.setNumber("1111222233334444");
		saved = this.creditCardService.save(creditCard);
		creCards = this.creditCardService.findAll();
		Assert.isTrue(creCards.contains(saved));
	}

	@Test
	public void testDelete() {
		CreditCard creditCard, saved;
		Collection<CreditCard> updated;

		creditCard = new CreditCard();
		creditCard.setBrandName("Test");
		creditCard.setCvvCode(789);
		creditCard.setExpirationMonth(8);
		creditCard.setExpirationYear(2030);
		creditCard.setHolderName("Test Holder Name");
		creditCard.setNumber("1111222233334444");
		saved = this.creditCardService.save(creditCard);

		this.creditCardService.delete(saved);
		updated = this.creditCardService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
