
package controllers.explorer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.ExplorerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Actor;
import domain.Explorer;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private ApplicationService		applicationService;


	// Constructors -----------------------------------------------------------

	public SurvivalClassExplorerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/enroll", method = RequestMethod.GET)
	public ModelAndView enroll(@RequestParam final int survivalClassId) throws Exception {
		ModelAndView result;
		SurvivalClass survivalClass;
		Explorer explorer;
		String message;
		Actor actor;
		Trip trip;
		survivalClass = this.survivalClassService.findOne(survivalClassId);
		trip = this.tripService.findBySurvivalClass(survivalClass);
		result = new ModelAndView("redirect:/survivalClass/list.do?tripId=" + trip.getId());
		message = "";

		if (survivalClass.getMoment().after(new Date()))
			try {
				if (this.actorService.isLogged()) {
					actor = this.actorService.findByPrincipal();
					if (actor instanceof Explorer) {
						explorer = this.explorerService.findByPrincipal();
						if (this.applicationService.findByTrip(trip).retainAll(this.applicationService.findAcceptedByExplorer(explorer))) {
							if (!explorer.getSurvivalClasses().contains(survivalClass))
								explorer.addSurvivalClasses(survivalClass);
							else
								explorer.removeSurvivalClasses(survivalClass);
							this.explorerService.save(explorer);
						} else
							message = "survivalClass.error.status";

					}
				}

			} catch (final Throwable oops) {
				message = "survivalClass.error";

			}
		else
			message = "survivalClass.error.date";
		result.addObject("message", message);

		return result;
	}
}
