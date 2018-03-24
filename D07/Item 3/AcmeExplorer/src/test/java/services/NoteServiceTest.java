
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
import domain.Auditor;
import domain.Note;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	@Autowired
	private NoteService		noteService;

	@Autowired
	private AuditorService	auditorService;
	@Autowired
	private TripService		tripService;


	@Test
	public void testFindAll() {
		Collection<Note> notes;

		notes = this.noteService.findAll();
		Assert.isTrue(!notes.isEmpty());
	}

	@Test
	public void testSave() {
		Note note, saved;
		Auditor auditor;
		Trip trip;
		Collection<Note> notes;

		trip = (Trip) this.tripService.findAll().toArray()[0];
		super.authenticate("auditor1");
		auditor = this.auditorService.findByPrincipal();
		note = this.noteService.create(auditor, trip);
		note.setRemark("Remark");
		saved = this.noteService.save(note);
		notes = this.noteService.findAll();
		Assert.isTrue(notes.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		Collection<Note> notes;
		Note note;

		notes = this.noteService.findAll();
		note = (Note) notes.toArray()[0];
		this.noteService.delete(note);
		notes = this.noteService.findAll();
		Assert.isTrue(!notes.contains(note));
	}

	@Test
	public void testFindByManager() {
		Collection<Note> notes, notes2;
		Note note;

		notes = this.noteService.findAll();
		note = (Note) notes.toArray()[0];
		notes2 = this.noteService.findByManager(note.getTrip().getManager());
		Assert.isTrue(notes2.contains(note));

	}

	//DONE testReply
	@SuppressWarnings("unused")
	@Test
	public void testReply() {
		String reply;
		Note note, saved;

		note = this.noteService.findAll().iterator().next();
		reply = "esto es una respuesta";

		super.authenticate("manager1");

		saved = this.noteService.reply(note, reply);

		note = this.noteService.findAll().iterator().next();

		Assert.isTrue(note.getReply().equals(reply));
		super.authenticate(null);

	}

}
