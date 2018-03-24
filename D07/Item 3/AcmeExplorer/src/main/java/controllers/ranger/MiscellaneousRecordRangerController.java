
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
import services.MiscellaneousRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curricula;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Controller
@RequestMapping("/miscellaneousRecord/ranger")
public class MiscellaneousRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculaService			curriculaService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordRangerController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		Ranger ranger;

		ranger = this.rangerService.findByPrincipal();
		miscellaneousRecord = new MiscellaneousRecord();
		result = this.createEditModelAndView(miscellaneousRecord);
		result.addObject("rangerId", ranger.getId());
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		Ranger ranger;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);

		ranger = this.rangerService.findByPrincipal();
		result = this.createEditModelAndView(miscellaneousRecord);
		result.addObject("rangerId", ranger.getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;
		Ranger ranger;
		Curricula curricula;
		MiscellaneousRecord aux;

		ranger = this.rangerService.findByPrincipal();
		curricula = this.curriculaService.findByRanger(ranger);
		try {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(miscellaneousRecord);
				result.addObject("rangerId", ranger.getId());
			} else {
				aux = this.miscellaneousRecordService.save(miscellaneousRecord);
				if (miscellaneousRecord.getId() == 0) {
					curricula.addMiscellaneousRecord(aux);
					this.curriculaService.save(curricula);
				}
				result = new ModelAndView("redirect:/miscellaneousRecord/list.do?rangerId=" + ranger.getId());
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			result.addObject("rangerId", ranger.getId());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;
		Curricula curricula;
		Ranger ranger;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				ranger = this.rangerService.findByPrincipal();
				curricula = this.curriculaService.findByRanger(ranger);
				curricula.removeMiscellaneousRecord(miscellaneousRecord);
				this.curriculaService.save(curricula);
				this.miscellaneousRecordService.delete(miscellaneousRecord);
				result = new ModelAndView("redirect:/miscellaneousRecord/list.do?rangerId=" + ranger.getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.error");
			}
		return result;
	}
	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
