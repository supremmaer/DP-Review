/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.manager;

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

import services.CategoryService;
import services.LegalTextService;
import services.ManagerService;
import services.RangerService;
import services.SystemConfigurationService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Trip;

@Controller
@RequestMapping("/trip/manager")
public class TripManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService					tripService;

	@Autowired
	private ManagerService				managerService;
	@Autowired
	private RangerService				rangerService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private LegalTextService			legalTextService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public TripManagerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Trip> trips;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		trips = this.tripService.findByManager(manager);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/manager/list.do");
		result.addObject("managerId", manager.getId());
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.create();
		result = this.createEditModelAndView(trip);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		result = this.createEditModelAndView(trip);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {
				this.tripService.save(trip);
				result = new ModelAndView("redirect:/trip/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView result;
		try {
			this.tripService.delete(trip);
			result = new ModelAndView("redirect:/trip/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trip, "trip.commit.error");
		}

		return result;
	}

	// Edit Tag

	@RequestMapping(value = "/editTag", method = RequestMethod.GET)
	public ModelAndView editTag(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<String> systags;

		systags = this.systemConfigurationService.findSystemConfiguration().getTags();
		result = new ModelAndView("trip/editTag");
		result.addObject("systags", systags);
		result.addObject("tripId", tripId);

		return result;
	}

	@RequestMapping(value = "/editTag", method = RequestMethod.POST, params = "save")
	public ModelAndView editTag(@RequestParam final String key, @RequestParam final String value, @RequestParam final String tripId) {
		ModelAndView result;
		Trip trip;
		Integer id;
		Manager manager;

		manager = this.managerService.findByPrincipal();

		id = Integer.valueOf(tripId);
		trip = this.tripService.findOne(id);
		if (trip.getManager().getId() == manager.getId()) {
			if (value == "")
				trip.removeTag(key);
			else
				trip.addTag(key, value);
			this.tripService.save(trip);
		}
		result = new ModelAndView("redirect:../display.do?tripId=" + id);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		Assert.isTrue(trip.getCancellationReason() == null);
		result = new ModelAndView("trip/cancel");
		result.addObject("trip", trip);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView cancel(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;
		Trip updatedTrip;

		if (binding.hasErrors()) {
			result = new ModelAndView("trip/cancel");
			result.addObject("trip", trip);
		} else
			try {
				updatedTrip = this.tripService.findOne(trip.getId());
				updatedTrip.setCancellationReason(trip.getCancellationReason());
				this.tripService.save(updatedTrip);
				result = new ModelAndView("redirect:/trip/display.do?tripId=" + trip.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("trip/cancel");
				result.addObject("trip", trip);
				result.addObject("message", "trip.commit.error");
			}

		return result;
	}

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String messageCode) {
		ModelAndView result;
		Collection<LegalText> legalTexts;
		Collection<Category> categories;
		Collection<Ranger> rangers;

		legalTexts = this.legalTextService.findFinal();
		categories = this.categoryService.findAll();
		rangers = this.rangerService.findAll();

		result = new ModelAndView("trip/edit");
		result.addObject("trip", trip);
		result.addObject("legalTexts", legalTexts);
		result.addObject("categories", categories);
		result.addObject("rangers", rangers);

		result.addObject("message", messageCode);

		return result;
	}

}
