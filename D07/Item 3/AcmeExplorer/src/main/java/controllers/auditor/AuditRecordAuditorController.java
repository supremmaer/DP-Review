
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditRecordService;
import services.AuditorService;
import services.TripService;
import controllers.AbstractController;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

import javax.validation.Valid;

@Controller
@RequestMapping("/auditRecord/auditor")
public class AuditRecordAuditorController extends AbstractController {

	//	 Services ---------------------------------------------------------------

	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private TripService			tripService;


	//	 Constructors -----------------------------------------------------------

	public AuditRecordAuditorController() {
		super();
	}

	//	 Listing ----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<AuditRecord> auditRecords;
		Auditor auditor;

		auditor = this.auditorService.findByPrincipal();
		auditRecords = this.auditRecordService.findByAuditor(auditor);

		result = new ModelAndView("auditRecord/list");
		result.addObject("auditRecords", auditRecords);
		result.addObject("requestURI", "auditRecord/auditor/list.do");
		result.addObject("auditorId", auditor.getId());
		return result;
	}

	//	 Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) throws Exception {
		ModelAndView result;
		AuditRecord auditRecord;
		Trip trip;
		trip = this.tripService.findOne(tripId);
		auditRecord = this.auditRecordService.create();
		auditRecord.setTrip(trip);
		result = this.createEditModelAndView(auditRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord;

		auditRecord = this.auditRecordService.findOne(auditRecordId);
		Assert.notNull(auditRecord);
		result = this.createEditModelAndView(auditRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final AuditRecord auditRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditRecord);
		else
			try {
				System.out.println(auditRecord.getDraftMode());
				this.auditRecordService.save(auditRecord);
				result = new ModelAndView("redirect:/auditRecord/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditRecord, "auditRecord.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final AuditRecord auditRecord, final BindingResult binding) {
		ModelAndView result;
		try {
			this.auditRecordService.delete(auditRecord);
			result = new ModelAndView("redirect:/auditRecord/auditor/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(auditRecord, "auditRecord.error");
			System.out.println(oops);
		}

		return result;
	}

	@RequestMapping(value = "/setFinalMode", method = RequestMethod.GET)
	public ModelAndView setFinalMode(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord;

		auditRecord = this.auditRecordService.findOne(auditRecordId);
		Assert.notNull(auditRecord);
		auditRecord.setDraftMode(false);
		this.auditRecordService.saveDraftMode(auditRecord);
		result = new ModelAndView("redirect:/auditRecord/display.do?auditRecordId=" + auditRecordId);

		return result;
	}

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final AuditRecord auditRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(auditRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditRecord auditRecord, final String messageCode) {
		ModelAndView result;

		final String requestURI = "auditRecord/auditor/edit.do";

		result = new ModelAndView("auditRecord/edit");
		result.addObject("auditRecord", auditRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
