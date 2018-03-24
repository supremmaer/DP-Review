
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ContactRepository;
import domain.Contact;

@Service
@Transactional
public class ContactService {

	//Managed Repository
	@Autowired
	private ContactRepository	contactRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;


	//Constructor
	public ContactService() {
		super();
	}

	//CRUD methods

	public Contact save(final Contact contact) {
		Assert.notNull(contact);

		Contact result;

		result = this.contactRepository.save(contact);
		this.actorService.isSpam(contact.getEmail(), this.actorService.findByPrincipal());
		this.actorService.isSpam(contact.getName(), this.actorService.findByPrincipal());
		this.actorService.isSpam(contact.getPhone(), this.actorService.findByPrincipal());

		return result;
	}

	public void delete(final Contact contact) {
		Assert.notNull(contact);
		Assert.isTrue(contact.getId() != 0);
		Assert.isTrue(this.contactRepository.exists(contact.getId()));

		this.contactRepository.delete(contact);
	}

	public Collection<Contact> findAll() {
		Collection<Contact> result;

		result = this.contactRepository.findAll();

		return result;
	}
}
