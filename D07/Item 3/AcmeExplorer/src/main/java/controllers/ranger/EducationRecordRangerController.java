
package controllers.ranger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EducationRecord;
import domain.Ranger;

import javax.validation.Valid;

@Controller
@RequestMapping("/educationRecord/ranger")
public class EducationRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private CurriculaService		curriculaService;


	// Constructors -----------------------------------------------------------

	public EducationRecordRangerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EducationRecord educationRecord;
		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		educationRecord = new EducationRecord();
		result = this.createEditModelAndView(educationRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;
		Ranger ranger;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);

		ranger = this.rangerService.findByPrincipal();
		result = this.createEditModelAndView(educationRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;
		Ranger ranger;
		Curricula curricula;
		EducationRecord aux;

		ranger = this.rangerService.findByPrincipal();
		curricula = this.curriculaService.findByRanger(ranger);
		try {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(educationRecord);
				result.addObject("rangerId", ranger.getId());
			} else {
				aux = this.educationRecordService.save(educationRecord);
				if (educationRecord.getId() == 0) {
					curricula.addEducationRecord(aux);
					this.curriculaService.save(curricula);
				}
				result = new ModelAndView("redirect:/educationRecord/list.do?rangerId=" + ranger.getId());
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			result.addObject("rangerId", ranger.getId());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;
		Curricula curricula;
		Ranger ranger;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				ranger = this.rangerService.findByPrincipal();
				curricula = this.curriculaService.findByRanger(ranger);
				curricula.removeEducationRecord(educationRecord);
				this.curriculaService.save(curricula);
				this.educationRecordService.delete(educationRecord);
				result = new ModelAndView("redirect:/educationRecord/list.do?rangerId=" + ranger.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(educationRecord, "educationRecord.error");
			}
		return result;
	}
	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
