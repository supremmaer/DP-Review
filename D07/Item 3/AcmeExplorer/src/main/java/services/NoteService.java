
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Actor;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.Trip;

@Service
@Transactional
public class NoteService {

	//Managed Repository
	@Autowired
	private NoteRepository	noteRepository;

	//Supporting Services
	@Autowired
	private ActorService	actorService;


	//Constructor
	public NoteService() {
		super();
	}

	//CRUD methods

	public Note create(final Auditor auditor, final Trip trip) {
		Assert.notNull(auditor);
		Assert.notNull(trip);
		Note result;

		result = new Note();
		result.setAuditor(auditor);
		result.setTrip(trip);
		result.setMoment(new Date());
		return result;
	}

	//33.1
	public Note save(final Note note) {
		Assert.notNull(note);

		Note result;
		Date moment;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Auditor || actor instanceof Manager);
		if (actor instanceof Auditor) {
			moment = new Date(System.currentTimeMillis() - 1);
			note.setMoment(moment);

		} else if (actor instanceof Manager) {
			moment = new Date(System.currentTimeMillis() - 1);
			note.setReplyMoment(moment);
		}
		result = this.noteRepository.save(note);
		this.actorService.isSpam(result.getRemark(), actor);
		this.actorService.isSpam(result.getReply(), actor);

		return result;
	}

	public void delete(final Note note) {
		Assert.notNull(note);
		Assert.isTrue(note.getId() != 0);
		Assert.isTrue(this.noteRepository.exists(note.getId()));

		this.noteRepository.delete(note);
	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();

		return result;
	}
	// 32
	public Note reply(final Note note, final String reply) {
		Assert.notNull(reply);
		Assert.notNull(note);
		Assert.isTrue(note.getId() != 0);

		Note result;
		Date moment;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Manager);
		Assert.isTrue(note.getTrip().getManager().getId() == actor.getId());

		moment = new Date(System.currentTimeMillis() - 1);
		note.setReply(reply);
		note.setReplyMoment(moment);

		result = this.noteRepository.save(note);
		this.actorService.isSpam(reply, this.actorService.findByPrincipal());
		return result;
	}

	public Collection<Note> findByManager(final Manager manager) {
		Assert.notNull(manager);

		Collection<Note> result;

		result = this.noteRepository.findByManagerID(manager.getId());

		return result;
	}

	public Collection<Note> findByAuditor(final Auditor auditor) {
		Assert.notNull(auditor);

		Collection<Note> result;

		result = this.noteRepository.findbyAuditorID(auditor.getId());

		return result;
	}

	public Note findOne(final int noteId) {
		Note result;

		result = this.noteRepository.findOne(noteId);

		return result;
	}
	//35.4.1
	public Map<String, Double> getNotesPerTripData() {
		final Map<String, Double> result = new HashMap<>();

		result.put("average", this.noteRepository.averageNotesofTrips());
		result.put("minimum", Double.valueOf(this.noteRepository.minNotesofTrips()));
		result.put("maximum", Double.valueOf(this.noteRepository.maxNotesofTrips()));
		result.put("standardDeviation", this.noteRepository.standardDeviationNotesofTrips());
		return result;
	}

}
