
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
import domain.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ContactServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private ContactService	contactService;


	@Test
	public void testFindAll() {
		Collection<Contact> contacts;

		contacts = this.contactService.findAll();
		Assert.isTrue(!contacts.isEmpty());
	}

	@Test
	public void testSave() {
		Contact contact, saved;

		super.authenticate("explorer4");
		contact = new Contact();
		contact.setName("John Doe");
		contact.setPhone("611522433");
		contact.setEmail("johndoe@server.com");
		//Inicializar contacto
		saved = this.contactService.save(contact);
		Assert.isTrue(this.contactService.findAll().contains(saved));
	}

	@Test
	public void testDelete() {
		Contact contact, saved;

		super.authenticate("explorer4");
		contact = new Contact();
		contact.setName("John Doe");
		contact.setPhone("611522433");
		contact.setEmail("johndoe@server.com");
		//Inicializar contacto
		saved = this.contactService.save(contact);
		Assert.isTrue(this.contactService.findAll().contains(saved));
		//Borar contacto
		this.contactService.delete(saved);
		Assert.isTrue(!this.contactService.findAll().contains(saved));
	}
}
