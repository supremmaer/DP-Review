
package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AuditorService;
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/auditor")
public class NoteAuditorController extends AbstractController {

	//Services

	@Autowired
	private NoteService		noteService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private TripService		tripService;


	//Constructor

	public NoteAuditorController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Note> notes;
		Auditor auditor;

		auditor = this.auditorService.findByPrincipal();
		notes = this.noteService.findByAuditor(auditor);
		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/auditor/list.do");
		result.addObject("auditorID", auditor.getId());

		return result;

	}

	//Edit

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer tripId) {
		ModelAndView result;
		Note note;
		Auditor auditor;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		auditor = this.auditorService.findByPrincipal();
		note = this.noteService.create(auditor, trip);
		result = this.createEditModelAndView(note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;
		Auditor auditor;
		UserAccount userAccount;

		note = this.noteService.findOne(noteId);
		userAccount = LoginService.getPrincipal();
		auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.isTrue(auditor.getId() == note.getAuditor().getId());
		Assert.notNull(note);

		result = this.createEditModelAndView(note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;
		Auditor auditor;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		auditor = this.auditorService.findByUserAccount(userAccount);
		Assert.isTrue(auditor.getId() == note.getAuditor().getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:/note/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}

		return result;
	}

	// Ancillary Methods
	private ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/create");

		result.addObject("note", note);
		result.addObject("message", messageCode);

		return result;
	}

}
