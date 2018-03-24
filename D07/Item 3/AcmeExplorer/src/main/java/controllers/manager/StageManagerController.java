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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StageService;
import services.TripService;
import controllers.AbstractController;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/stage/manager")
public class StageManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private StageService	stageService;


	// Constructors -----------------------------------------------------------

	public StageManagerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer tripId) {
		ModelAndView result;
		Stage stage;

		stage = this.stageService.create();
		result = this.createEditModelAndView(stage);
		result.addObject("tripId", tripId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer stageId, @RequestParam final Integer tripId) {
		ModelAndView result;
		Stage stage;

		stage = this.stageService.findOne(stageId);
		Assert.notNull(stage);

		result = this.createEditModelAndView(stage);
		result.addObject("tripId", tripId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Stage stage, final BindingResult binding, @RequestParam(required = false) final Integer tripId) {
		ModelAndView result;
		Trip trip;
		Stage updatedStage;

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage);
		else
			try {
				trip = this.tripService.findOne(tripId);
				if (stage.getId() != 0) {
					updatedStage = this.stageService.findOne(stage.getId());
					updatedStage.setTitle(stage.getTitle());
					updatedStage.setDescription(stage.getDescription());
					updatedStage.setPrice(stage.getPrice());
					this.stageService.save(updatedStage);
				} else {
					trip.addStage(stage);
					this.tripService.save(trip);
				}
				result = new ModelAndView("redirect:/stage/list.do?tripId=" + tripId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(stage, "stage.commit.error");
				System.out.println(stage);
				System.out.println(oops);
			}
		result.addObject("tripId", tripId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Stage stage, @RequestParam final Integer tripId, final BindingResult binding) {
		ModelAndView result;
		Trip trip;

		try {
			trip = this.tripService.findOne(tripId);
			trip.removeStage(stage);
			this.tripService.save(trip);
			this.stageService.delete(stage);
			result = new ModelAndView("redirect:/stage/list.do?tripId=" + tripId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(stage, "stage.commit.error");
		}
		result.addObject("tripId", tripId);

		return result;
	}

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
