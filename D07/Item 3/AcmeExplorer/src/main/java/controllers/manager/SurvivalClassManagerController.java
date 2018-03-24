
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.LocationService;
import services.ManagerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Actor;
import domain.Location;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;
import forms.SurvivalClassForm;

@Controller
@RequestMapping("/survivalClass/manager")
public class SurvivalClassManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private LocationService			locationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ManagerService			managerService;


	// Constructors -----------------------------------------------------------

	public SurvivalClassManagerController() {
		super();
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) throws Exception {
		ModelAndView result;
		SurvivalClassForm survivalClassForm;
		Trip trip;

		survivalClassForm = new SurvivalClassForm();
		trip = this.tripService.findOne(tripId);
		survivalClassForm.setTrip(trip);

		result = this.createEditModelAndView(survivalClassForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClassForm survivalClassForm;
		SurvivalClass survivalClass;
		Trip trip;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		trip = this.tripService.findBySurvivalClass(survivalClass);
		survivalClassForm = new SurvivalClassForm();
		survivalClassForm.setSurvivalClassId(survivalClassId);
		survivalClassForm.setLocationId(survivalClass.getLocation().getId());
		survivalClassForm.setTrip(trip);
		survivalClassForm.setDescription(survivalClass.getDescription());
		survivalClassForm.setLatitude(survivalClass.getLocation().getLatitude());
		survivalClassForm.setLongitude(survivalClass.getLocation().getLongitude());
		survivalClassForm.setMoment(survivalClass.getMoment());
		survivalClassForm.setName(survivalClass.getLocation().getName());
		survivalClassForm.setTitle(survivalClass.getTitle());

		result = this.createEditModelAndView(survivalClassForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClassForm survivalClassForm, final BindingResult binding) {
		ModelAndView result;
		SurvivalClass survivalClass;
		SurvivalClass survivalClassAux;
		Location location;
		Location locationAux;
		Trip trip;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClassForm);
		else
			try {
				location = this.locationService.create(survivalClassForm);
				trip = survivalClassForm.getTrip();
				survivalClass = this.survivalClassService.create(survivalClassForm);
				if (trip.getManager().getId() == principal.getId()) {
					locationAux = this.locationService.save(location);
					survivalClass.setLocation(locationAux);
					survivalClassAux = this.survivalClassService.save(survivalClass, trip.getId());
					if (!trip.getSurvivalClasses().contains(survivalClass))
						trip.addSurvivalClass(survivalClassAux);
					this.tripService.saveSurvivalClass(trip);
				}
				result = new ModelAndView("redirect:/survivalClass/list.do?tripId=" + trip.getId());

			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(survivalClassForm, "survivalClass.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final SurvivalClassForm survivalClassForm, final BindingResult binding) {
		ModelAndView result;
		SurvivalClass survivalClass;
		Location location;
		Trip trip;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClassForm);
		else
			try {
				location = this.locationService.create(survivalClassForm);
				trip = survivalClassForm.getTrip();
				survivalClass = this.survivalClassService.create(survivalClassForm);
				if (principal.getId() == trip.getManager().getId()) {
					trip.removeSurvivalClass(survivalClass);
					this.tripService.saveSurvivalClass(trip);
					this.survivalClassService.delete(survivalClass, trip.getId());
					this.locationService.delete(location);
				}
				result = new ModelAndView("redirect:/survivalClass/list.do?tripId=" + trip.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(survivalClassForm, "survivalClass.error");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClases;
		Integer principalId;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		survivalClases = this.survivalClassService.findByManager(principal);
		principalId = principal.getId();
		result = new ModelAndView("survivalClass/manager/list");
		result.addObject("requestURI", "survivalClass/manager/list.do");
		result.addObject("survivalClass", survivalClases);
		result.addObject("principalId", principalId);

		return result;

	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final SurvivalClassForm survivalClassForm) {
		ModelAndView result;

		result = this.createEditModelAndView(survivalClassForm, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final SurvivalClassForm survivalClassForm, final String message) {
		ModelAndView result;

		final String requestURI = "survivalClass/manager/edit.do";

		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClassForm", survivalClassForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
