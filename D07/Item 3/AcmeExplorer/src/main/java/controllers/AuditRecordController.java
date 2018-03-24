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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditRecordService;
import domain.AuditRecord;

@Controller
@RequestMapping("/auditRecord")
public class AuditRecordController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public AuditRecordController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer auditRecordId) {
		final ModelAndView result;
		AuditRecord auditRecord;
		int id;
		auditRecord = this.auditRecordService.findOne(auditRecordId);
		id = 0;

		if (this.actorService.isLogged())
			id = this.actorService.findByPrincipal().getId();
		result = new ModelAndView("auditRecord/display");
		result.addObject("auditRecord", auditRecord);
		result.addObject("principalID", id);
		result.addObject("requestURI", "auditRecord/display.do");
		return result;
	}
}
