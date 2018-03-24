
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CurriculaService;
import domain.Actor;
import domain.Curricula;
import domain.EndorserRecord;
import domain.Ranger;

@Controller
@RequestMapping("/endorserRecord")
public class EndorserRecordController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CurriculaService		curriculaService;


	// Constructors -----------------------------------------------------------

	public EndorserRecordController() {
		super();
	}

	// Listing ----------------------------------------------------------------	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rangerId) {
		ModelAndView result;
		Collection<EndorserRecord> endorserRecord;
		Curricula curricula;
		Actor actor;
		Actor principal;
		Integer principalId;

		actor = this.actorService.findOne(rangerId);
		if (this.actorService.isLogged()) {
			principal = this.actorService.findByPrincipal();
			principalId = principal.getId();
		} else
			principalId = 0;

		curricula = this.curriculaService.findByRanger((Ranger) actor);
		endorserRecord = curricula.getEndorserRecords();
		result = new ModelAndView("endorserRecord/list");
		result.addObject("requestURI", "endorserRecord/list.do");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("rangerId", rangerId);
		result.addObject("principalId", principalId);

		return result;

	}
}
