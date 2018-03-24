
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.ProfessionalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.ProfessionalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/professionalRecord/ranger")
public class ProfessionalRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculaService			curriculaService;


	// Constructors -----------------------------------------------------------

	public ProfessionalRecordRangerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProfessionalRecord professionalRecord;
		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		professionalRecord = new ProfessionalRecord();
		result = this.createEditModelAndView(professionalRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;
		Ranger ranger;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);

		ranger = this.rangerService.findByPrincipal();
		result = this.createEditModelAndView(professionalRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;
		Ranger ranger;
		Curricula curricula;
		ProfessionalRecord aux;

		ranger = this.rangerService.findByPrincipal();
		curricula = this.curriculaService.findByRanger(ranger);
		try {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(professionalRecord);
				result.addObject("rangerId", ranger.getId());
			} else {
				aux = this.professionalRecordService.save(professionalRecord);
				if (professionalRecord.getId() == 0) {
					curricula.addProfessionalRecord(aux);
					this.curriculaService.save(curricula);
				}
				result = new ModelAndView("redirect:/professionalRecord/list.do?rangerId=" + ranger.getId());
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			result.addObject("rangerId", ranger.getId());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;
		Curricula curricula;
		Ranger ranger;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				ranger = this.rangerService.findByPrincipal();
				curricula = this.curriculaService.findByRanger(ranger);
				curricula.removeProfessionalRecord(professionalRecord);
				this.curriculaService.save(curricula);
				this.professionalRecordService.delete(professionalRecord);
				result = new ModelAndView("redirect:/professionalRecord/list.do?rangerId=" + ranger.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(professionalRecord, "professionalRecord.error");
			}
		return result;
	}
	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
