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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.findSystemConfiguration();
		result = new ModelAndView("welcome/index");
		result.addObject("welcomeEN", systemConfiguration.getWelcomeMessageEnglish());
		result.addObject("welcomeES", systemConfiguration.getWelcomeMessageSpanish());

		return result;
	}
}
