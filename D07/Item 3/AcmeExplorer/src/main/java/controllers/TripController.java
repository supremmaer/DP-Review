/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CategoryService;
import services.FinderService;
import services.ManagerService;
import services.SystemConfigurationService;
import services.TripService;
import domain.Actor;
import domain.Auditor;
import domain.Category;
import domain.Explorer;
import domain.Finder;
import domain.Manager;
import domain.Sponsorship;
import domain.SystemConfiguration;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService					tripService;

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private ApplicationService			applicationService;

	@Autowired
	private FinderService				finderService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public TripController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer finderId) {
		ModelAndView result;
		Collection<Trip> trips;
		Finder finder;
		SystemConfiguration systemConfig;
		Double VATTax;

		systemConfig = this.systemConfigurationService.findSystemConfiguration();
		if (finderId != null) {
			finder = this.finderService.findOne(finderId);
			trips = finder.getTrips();
		} else
			trips = this.tripService.findPublicated();
		VATTax = systemConfig.getVATTax();
		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("VATTax", VATTax);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = "managerId")
	public ModelAndView listManager(@RequestParam final Integer managerId) {
		ModelAndView result;
		Collection<Trip> trips;
		Manager manager;

		manager = this.managerService.findOne(managerId);
		trips = this.tripService.findByManager(manager);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = "categoryId")
	public ModelAndView listCategory(@RequestParam final Integer categoryId) {
		ModelAndView result;
		Collection<Trip> trips;
		Category category;

		category = this.categoryService.findOne(categoryId);
		trips = this.tripService.findByCategory(category);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}

	@RequestMapping(value = "/listKeyword", method = RequestMethod.GET)
	public ModelAndView listKeyword(@RequestParam final String keyword) {
		ModelAndView result;
		final Collection<Trip> trips = this.tripService.findByKey(keyword);

		result = new ModelAndView("trip/listKeyword");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;

		result = new ModelAndView("trip/search");
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		Actor actor;
		Integer id;
		Boolean applyable;
		Map<String, String> tag;
		String banner;
		Collection<Sponsorship> sponsorships;
		Sponsorship sponsorship;
		Random rnd;
		Date date;

		date = new Date();
		applyable = false;
		trip = this.tripService.findOne(tripId);

		if (trip.getPublicationDate().after(date))
			Assert.isTrue(trip.getManager().getId() == this.actorService.findByPrincipal().getId());

		result = new ModelAndView("trip/display");
		tag = trip.getTag();
		if (this.actorService.isLogged()) {
			actor = this.actorService.findByPrincipal();
			id = actor.getId();
			if (actor instanceof Manager)
				result.addObject("managerId", id);
			else if (actor instanceof Explorer && trip.getCancellationReason() == null && trip.getStartDate().after(date) && trip.getPublicationDate().before(date)) {
				applyable = this.applicationService.getApplicationNotRejectedByTripAndExplorer(trip.getId(), id);
				result.addObject("explorerId", id);
				result.addObject("applyable", applyable);
			} else if (actor instanceof Auditor)
				result.addObject("auditorId", id);
		}
		sponsorships = trip.getSponsorships();
		if (sponsorships.size() > 0) {
			rnd = new Random();
			sponsorship = (Sponsorship) sponsorships.toArray()[rnd.nextInt(sponsorships.size())];
			banner = sponsorship.getBanner();
			result.addObject("banner", banner);
			result.addObject("bannerLink", sponsorship.getInfoPage());
		}
		result.addObject("trip", trip);
		result.addObject("tags", tag);
		result.addObject("requestURI", "trip/display.do");

		return result;
	}
}
