
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.StoryService;
import services.TripService;
import domain.Actor;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story")
public class StoryController extends AbstractController {

	//Services

	@Autowired
	private StoryService		storyService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TripService			tripService;


	//Constructor

	public StoryController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer tripId) {
		ModelAndView result;
		Collection<Story> stories;
		Boolean doable;
		Actor actor;
		Trip trip;

		doable = false;
		trip = this.tripService.findOne(tripId);
		stories = this.storyService.findByTrip(tripId);
		result = new ModelAndView("story/list");
		result.addObject("stories", stories);
		if (this.actorService.isLogged() && trip.getEndDate().before(new Date())) {
			actor = this.actorService.findByPrincipal();
			if (actor instanceof Explorer)
				doable = this.applicationService.getApplicationsAccepted(tripId, actor.getId());

		}
		result.addObject("doable", doable);
		result.addObject("tripId", tripId);
		result.addObject("requestURI", "story/list.do");

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int storyId) {
		ModelAndView result;
		Story story;

		story = this.storyService.findOne(storyId);
		result = new ModelAndView("story/display");
		result.addObject("story", story);

		return result;
	}

	//	//Edit
	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int applicationId) {
	//		ModelAndView result;
	//		Application application;
	//
	//		application = this.applicationService.findOne(applicationId);
	//		Assert.notNull(application);
	//		result = this.createEditModelAndView(application);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView edit(@Valid final Application application, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		if (binding.hasErrors())
	//			result = this.createEditModelAndView(application);
	//		else
	//			try {
	//				this.applicationService.save(application);
	//				result = new ModelAndView("redirect:/application/manager/list.do");
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(application, "application.commit.error");
	//			}
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Application application, final BindingResult binding) {
	//		ModelAndView result;
	//		try {
	//			this.applicationService.delete(application);
	//			result = new ModelAndView("redirect:/application/manager/list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(application, "application.commit.error");
	//			System.out.println(oops);
	//		}
	//		return result;
	//	}
	//
	//	// Ancillary Methods
	//	private ModelAndView createEditModelAndView(final Application application) {
	//		ModelAndView result;
	//
	//		result = this.createEditModelAndView(application, null);
	//
	//		return result;
	//	}
	//
	//	private ModelAndView createEditModelAndView(final Application application, final String messageCode) {
	//		ModelAndView result;
	//
	//		result = new ModelAndView("trip/edit");
	//		result.addObject("message", messageCode);
	//
	//		return result;
	//	}

}
