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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CurriculaService;
import services.RangerService;
import services.SystemConfigurationService;
import domain.Actor;
import domain.Ranger;
import domain.SystemConfiguration;
import forms.ActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private RangerService				rangerService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final String actorType) throws Exception {
		ModelAndView result;
		ActorForm actorForm;

		actorForm = new ActorForm();
		actorForm.setAuthority(actorType);

		result = this.createRegisterModelAndView(actorForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Actor actor;
		SystemConfiguration systemConfig;

		if (binding.hasErrors())
			result = this.createRegisterModelAndView(actorForm);
		else
			try {
				if (actorForm.getPhone().matches("\\d{4,99}")) {
					systemConfig = this.systemConfigurationService.findSystemConfiguration();
					String newPhone = systemConfig.getCountryCode();
					newPhone += " " + actorForm.getPhone();
					actorForm.setPhone(newPhone);
				}
				actor = this.actorService.create(actorForm);
				if (binding.hasErrors())
					result = this.createRegisterModelAndView(actorForm);
				else {
					actor = this.actorService.register(actor);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createRegisterModelAndView(actorForm, "actor.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() throws Exception {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		result = this.createEditModelAndView(actor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;
		SystemConfiguration systemConfig;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				if (actor.getPhone().matches("\\d{4,99}")) {
					systemConfig = this.systemConfigurationService.findSystemConfiguration();
					String newPhone = systemConfig.getCountryCode();
					newPhone += " " + actor.getPhone();
					actor.setPhone(newPhone);
				}
				this.actorService.saveEdit(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "actor.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) throws Exception {
		ModelAndView result;
		Actor actor;
		Ranger ranger;
		Boolean existCurricula;
		Boolean isOwner;
		String rol;

		existCurricula = false;
		rol = "none";
		result = new ModelAndView("actor/display");
		isOwner = false;
		if (actorId != 0)
			actor = this.actorService.findOne(actorId);
		else if (this.actorService.isLogged())
			actor = this.actorService.findByPrincipal();
		else {
			actor = null;
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		if (this.actorService.isLogged() && actor.getId() == this.actorService.findByPrincipal().getId())
			isOwner = true;

		if (actor instanceof Ranger) {
			ranger = this.rangerService.findOne(actor.getId());
			rol = "ranger";
			if (this.curriculaService.findByRanger(ranger) != null)
				existCurricula = true;
		}

		result.addObject("actor", actor);
		result.addObject("existCurricula", existCurricula);
		result.addObject("rol", rol);
		result.addObject("isOwner", isOwner);
		return result;
	}
	// Ancillary methods

	protected ModelAndView createRegisterModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createRegisterModelAndView(actorForm, null);

		return result;
	}
	protected ModelAndView createRegisterModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result;

		final String requestURI = "actor/register.do";

		result = new ModelAndView("actor/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		final String requestURI = "actor/edit.do";

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
