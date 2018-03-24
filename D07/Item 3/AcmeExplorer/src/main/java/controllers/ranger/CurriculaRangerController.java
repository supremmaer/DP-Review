
package controllers.ranger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PersonalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.PersonalRecord;
import domain.Ranger;

import javax.validation.Valid;

@Controller
@RequestMapping("/curriculum/ranger")
public class CurriculaRangerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CurriculaService		curriculaService;


	// Listing ----------------------------------------------------------------		

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = new PersonalRecord();
		result = this.createEditModelAndView(personalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.rangerService.findByPrincipal().getCurricula().getPersonalRecord();
		Assert.notNull(personalRecord);

		result = this.createEditModelAndView(personalRecord);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;
		Curricula curricula;
		PersonalRecord auxPersonalRecord;
		Curricula auxCurricula;
		Ranger ranger;

		try {
			if (binding.hasErrors())
				result = this.createEditModelAndView(personalRecord);
			else {
				ranger = this.rangerService.findByPrincipal();

				if (ranger.getCurricula() != null)
					auxPersonalRecord = this.personalRecordService.save(personalRecord);
				else {
					curricula = this.curriculaService.create();
					auxPersonalRecord = this.personalRecordService.save(personalRecord);
					curricula.setPersonalRecord(auxPersonalRecord);
					auxCurricula = this.curriculaService.save(curricula);
					ranger.setCurricula(auxCurricula);
					this.rangerService.save(ranger);
				}
				result = new ModelAndView("redirect:/actor/display.do?actorId=0");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(personalRecord, "educationRecord.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;
		Curricula curricula;
		Ranger ranger;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				ranger = this.rangerService.findByPrincipal();
				curricula = this.curriculaService.findByRanger(ranger);
				this.curriculaService.delete(curricula);
				result = new ModelAndView("redirect:/actor/display.do?actorId=0");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(personalRecord, "curricula.commit.error");
			}
		return result;
	}

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
