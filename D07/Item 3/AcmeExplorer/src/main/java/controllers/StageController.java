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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ManagerService;
import services.TripService;
import domain.Manager;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/stage")
public class StageController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public StageController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer tripId) {
		ModelAndView result;
		Collection<Stage> stages;
		Manager manager;
		Boolean isEditable;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		stages = trip.getStages();
		isEditable = false;
		if (this.actorService.isLogged() && trip.getPublicationDate().after(new Date())) {
			manager = this.managerService.findByPrincipal();
			isEditable = manager.getId() == trip.getManager().getId();
		}

		result = new ModelAndView("stage/list");
		result.addObject("stages", stages);
		result.addObject("requestURI", "stage/list.do");
		result.addObject("isEditable", isEditable);
		result.addObject("tripId", tripId);
		return result;
	}

	// Creation ---------------------------------------------------------------

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Stage stage) {
		ModelAndView result;

		result = this.createEditModelAndView(stage, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Stage stage, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("stage/edit");
		result.addObject("stage", stage);

		result.addObject("message", messageCode);

		return result;
	}

}
