
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SurvivalClassService;
import services.TripService;
import domain.Actor;
import domain.Explorer;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass")
public class SurvivalClassController extends AbstractController {

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ActorService			actorService;

	


	// Constructors -----------------------------------------------------------

	public SurvivalClassController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<SurvivalClass> survivalClases;
		Collection<SurvivalClass> registered;
		Trip trip;
		Actor actor;
		Explorer explorer;
		Integer principalId;

		trip = this.tripService.findOne(tripId);
		survivalClases = this.survivalClassService.findByTrip(trip);

		result = new ModelAndView("survivalClass/list");
		result.addObject("requestURI", "survivalClass/list.do");
		result.addObject("survivalClass", survivalClases);
		result.addObject("trip", trip);
		if (this.actorService.isLogged()) {
			actor = this.actorService.findByPrincipal();
			principalId = actor.getId();
			result.addObject("principalId", principalId);

			if (actor instanceof Explorer) {
				explorer = (Explorer) actor;
				registered = this.survivalClassService.findByExplorer(explorer);
				registered.retainAll(survivalClases);
				result.addObject("registered", registered);
			}
		}

		return result;

	}
}
