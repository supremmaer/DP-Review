/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.AuditRecordService;
import services.LegalTextService;
import services.ManagerService;
import services.NoteService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.Actor;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Trip;
import forms.MessageForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services --------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private TripService			tripService;
	@Autowired
	private LegalTextService	legalTextService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private RangerService		rangerService;
	@Autowired
	private NoteService			noteService;
	@Autowired
	private AuditRecordService	auditRecordService;
	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/suspiciousworkers")
	public ModelAndView suspiciousworkers() {
		ModelAndView result;
		Collection<Actor> actors;

		final Collection<Manager> suspiciousManagers = this.managerService.getAllSuspiciousManagers();
		final Collection<Ranger> suspiciousRangers = this.rangerService.getAllSuspiciousRangers();
		actors = this.actorService.getAllSuspiciousActors();

		result = new ModelAndView("administrator/suspiciousworkers");
		result.addObject("suspiciousManagers", suspiciousManagers);
		result.addObject("suspiciousRangers", suspiciousRangers);
		result.addObject("actors", actors);
		result.addObject("requestURI", "administrator/suspiciousworkers.do");

		return result;
	}
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;
		final String avg = "average", min = "minimum", max = "maximum", sd = "standardDeviation";

		Double avgApplicationsperTrip = 0.0;
		Double minApplicationsperTrip = 0.0;
		Double maxApplicationsperTrip = 0.0;
		Double sdApplicationsperTrip = 0.0;

		Double avgTripperManager = 0.0;
		Double minTripperManager = 0.0;
		Double maxTripperManager = 0.0;
		Double sdTripperManager = 0.0;

		Double avgPriceofTrip = 0.0;
		Double minPriceofTrip = 0.0;
		Double maxPriceofTrip = 0.0;
		Double sdPriceofTrip = 0.0;

		Double avgTripperRanger = 0.0;
		Double minTripperRanger = 0.0;
		Double maxTripperRanger = 0.0;
		Double sdTripperRanger = 0.0;

		Double ratioPendingApplication = 0.0;
		Double ratioDueApplication = 0.0;
		Double ratioAcceptedApplication = 0.0;
		Double ratioCancelledApplication = 0.0;

		Double ratioCancelledvstotalTrip = 0.0;

		Collection<Trip> aboveAverageTrip = this.tripService.findTripsWithMoreApplicationsThanAverage();

		Collection<LegalText> numberReferenceLegalText = this.legalTextService.findFinal();

		Double avgNotesperTrip = 0.0;
		Double minNotesperTrip = 0.0;
		Double maxNotesperTrip = 0.0;
		Double sdNotesperTrip = 0.0;

		Double avgAuditRecordsperTrip = 0.0;
		Double minAuditRecordsperTrip = 0.0;
		Double maxAuditRecordsperTrip = 0.0;
		Double sdAuditRecordsperTrip = 0.0;

		Double ratioTripOneAuditRecord = 0.0;

		Double ratioRangerCurriculaRegistered = 0.0;

		Double ratioRangerCurriculumEndorsed = 0.0;

		Double ratioSuspiciousManagers = 0.0;

		Double ratioSuspiciousRangers = 0.0;

		if (this.tripService.findAll().size() > 0) {

			avgApplicationsperTrip = this.applicationService.getApplicationPerTripData().get(avg);
			minApplicationsperTrip = this.applicationService.getApplicationPerTripData().get(min);
			maxApplicationsperTrip = this.applicationService.getApplicationPerTripData().get(max);
			sdApplicationsperTrip = this.applicationService.getApplicationPerTripData().get(sd);

			avgTripperManager = this.tripService.getTripPerManagerData().get(avg);
			minTripperManager = this.tripService.getTripPerManagerData().get(min);
			maxTripperManager = this.tripService.getTripPerManagerData().get(max);
			sdTripperManager = this.tripService.getTripPerManagerData().get(sd);

			avgPriceofTrip = this.tripService.getPriceOfTripsData().get(avg);
			minPriceofTrip = this.tripService.getPriceOfTripsData().get(min);
			maxPriceofTrip = this.tripService.getPriceOfTripsData().get(max);
			sdPriceofTrip = this.tripService.getPriceOfTripsData().get(sd);

			avgTripperRanger = this.tripService.getTripPerRangerData().get(avg);
			minTripperRanger = this.tripService.getTripPerRangerData().get(min);
			maxTripperRanger = this.tripService.getTripPerRangerData().get(max);
			sdTripperRanger = this.tripService.getTripPerRangerData().get(sd);

			ratioPendingApplication = this.applicationService.getStatusRatiosApplicationData().get("PENDING");
			ratioDueApplication = this.applicationService.getStatusRatiosApplicationData().get("DUE");
			ratioAcceptedApplication = this.applicationService.getStatusRatiosApplicationData().get("ACCEPTED");
			ratioCancelledApplication = this.applicationService.getStatusRatiosApplicationData().get("CANCELLED");

			ratioCancelledvstotalTrip = this.tripService.ratioTripsCancelled();

			aboveAverageTrip = this.tripService.findTripsWithMoreApplicationsThanAverage();

			numberReferenceLegalText = this.legalTextService.findFinal();

			avgNotesperTrip = this.noteService.getNotesPerTripData().get(avg);
			minNotesperTrip = this.noteService.getNotesPerTripData().get(min);
			maxNotesperTrip = this.noteService.getNotesPerTripData().get(max);
			sdNotesperTrip = this.noteService.getNotesPerTripData().get(sd);

			avgAuditRecordsperTrip = this.auditRecordService.getAuditRecordPerTripData().get(avg);
			minAuditRecordsperTrip = this.auditRecordService.getAuditRecordPerTripData().get(min);
			maxAuditRecordsperTrip = this.auditRecordService.getAuditRecordPerTripData().get(max);
			sdAuditRecordsperTrip = this.auditRecordService.getAuditRecordPerTripData().get(sd);

			ratioTripOneAuditRecord = this.tripService.ratioTripsWithOneAuditRecord();
		}
		if (this.rangerService.findAll().size() > 0) {
			ratioRangerCurriculaRegistered = this.rangerService.ratioRangerwithRegisteredCurricula();

			ratioRangerCurriculumEndorsed = this.rangerService.ratioRangerwithEndorsedCurriculum();

			ratioSuspiciousRangers = this.rangerService.ratioSuspiciousRangers();
		}
		if (this.managerService.findAll().size() > 0)
			ratioSuspiciousManagers = this.managerService.ratioSuspiciousManagers();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgApplicationsperTrip", avgApplicationsperTrip);
		result.addObject("minApplicationsperTrip", minApplicationsperTrip);
		result.addObject("maxApplicationsperTrip", maxApplicationsperTrip);
		result.addObject("sdApplicationsperTrip", sdApplicationsperTrip);

		result.addObject("avgTripperManager", avgTripperManager);
		result.addObject("minTripperManager", minTripperManager);
		result.addObject("maxTripperManager", maxTripperManager);
		result.addObject("sdTripperManager", sdTripperManager);

		result.addObject("avgPriceofTrip", avgPriceofTrip);
		result.addObject("minPriceofTrip", minPriceofTrip);
		result.addObject("maxPriceofTrip", maxPriceofTrip);
		result.addObject("sdPriceofTrip", sdPriceofTrip);

		result.addObject("avgTripperRanger", avgTripperRanger);
		result.addObject("minTripperRanger", minTripperRanger);
		result.addObject("maxTripperRanger", maxTripperRanger);
		result.addObject("sdTripperRanger", sdTripperRanger);

		result.addObject("ratioPendingApplication", ratioPendingApplication);
		result.addObject("ratioDueApplication", ratioDueApplication);
		result.addObject("ratioAcceptedApplication", ratioAcceptedApplication);
		result.addObject("ratioCancelledApplication", ratioCancelledApplication);

		result.addObject("ratioCancelledvstotalTrip", ratioCancelledvstotalTrip);
		result.addObject("aboveAverageTrip", aboveAverageTrip);
		result.addObject("numberReferenceLegalText", numberReferenceLegalText);

		result.addObject("avgNotesperTrip", avgNotesperTrip);
		result.addObject("minNotesperTrip", minNotesperTrip);
		result.addObject("maxNotesperTrip", maxNotesperTrip);
		result.addObject("sdNotesperTrip", sdNotesperTrip);

		result.addObject("avgAuditRecordsperTrip", avgAuditRecordsperTrip);
		result.addObject("minAuditRecordsperTrip", minAuditRecordsperTrip);
		result.addObject("maxAuditRecordsperTrip", maxAuditRecordsperTrip);
		result.addObject("sdAuditRecordsperTrip", sdAuditRecordsperTrip);

		result.addObject("ratioTripOneAuditRecord", ratioTripOneAuditRecord);

		result.addObject("ratioRangerCurriculaRegistered", ratioRangerCurriculaRegistered);

		result.addObject("atioRangerCurriculumEndorsed", ratioRangerCurriculumEndorsed);

		result.addObject("ratioSuspiciousManagers", ratioSuspiciousManagers);

		result.addObject("ratioSuspiciousRangers", ratioSuspiciousRangers);

		result.addObject("requestURI", "administrator/dashboard.do");
		return result;
	}

	@RequestMapping("/notification")
	public ModelAndView notification() {
		ModelAndView result;
		MessageForm messageForm;

		messageForm = new MessageForm();
		messageForm.setDestination("NOTIFICATION");
		result = this.createEditModelAndView(messageForm);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		final String requestURI = "actor/message/send.do";

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
