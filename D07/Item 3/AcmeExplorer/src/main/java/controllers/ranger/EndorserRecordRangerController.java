
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
import services.EndorserRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.EndorserRecord;
import domain.Ranger;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private CurriculaService		curriculaService;


	// Constructors -----------------------------------------------------------

	public EndorserRecordRangerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EndorserRecord endorserRecord;
		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		endorserRecord = new EndorserRecord();
		result = this.createEditModelAndView(endorserRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;
		Ranger ranger;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);

		ranger = this.rangerService.findByPrincipal();
		result = this.createEditModelAndView(endorserRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;
		Ranger ranger;
		Curricula curricula;
		EndorserRecord aux;

		ranger = this.rangerService.findByPrincipal();
		curricula = this.curriculaService.findByRanger(ranger);
		try {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(endorserRecord);
				result.addObject("rangerId", ranger.getId());
			} else {
				aux = this.endorserRecordService.save(endorserRecord);
				if (endorserRecord.getId() == 0) {
					curricula.addEndorserRecord(aux);
					this.curriculaService.save(curricula);
				}
				result = new ModelAndView("redirect:/endorserRecord/list.do?rangerId=" + ranger.getId());
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			result.addObject("rangerId", ranger.getId());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;
		Curricula curricula;
		Ranger ranger;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				ranger = this.rangerService.findByPrincipal();
				curricula = this.curriculaService.findByRanger(ranger);
				curricula.removeEndorserRecord(endorserRecord);
				this.curriculaService.save(curricula);
				this.endorserRecordService.delete(endorserRecord);
				result = new ModelAndView("redirect:/endorserRecord/list.do?rangerId=" + ranger.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.error");
			}
		return result;
	}
	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
