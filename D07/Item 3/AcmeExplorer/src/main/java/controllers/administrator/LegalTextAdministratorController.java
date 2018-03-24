
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LegalTextService;
import domain.LegalText;

@Controller
@RequestMapping("/legalText/administrator")
public class LegalTextAdministratorController {

	@Autowired
	private LegalTextService	legalTextService;


	// Constructors -----------------------------------------------------------

	public LegalTextAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextService.findAll();

		result = new ModelAndView("legalText/list");
		result.addObject("legalTexts", legalTexts);
		result.addObject("requestURI", "legalText/administrator/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.create();
		result = this.createEditModelAndView(legalText);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalTextId) {
		final ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOne(legalTextId);
		Assert.isTrue(legalText != null);
		Assert.isTrue(legalText.isDraftMode());
		result = this.createEditModelAndView(legalText);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final LegalText legalText, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.save(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(legalText, "legalText.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalText legalText, final BindingResult binding) {
		ModelAndView result;
		try {
			this.legalTextService.delete(legalText);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalText, "legalText.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/setFinal", method = RequestMethod.GET)
	public ModelAndView setFinal(@RequestParam final int legalTextId) {
		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOne(legalTextId);
		this.legalTextService.setFinal(legalText);

		result = new ModelAndView("redirect:list.do");
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int legalTextId) {
		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOne(legalTextId);
		result = this.createEditModelAndView(legalText);
		final Boolean view = true;
		result.addObject("view", view);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalText legalText) {
		ModelAndView result;

		result = this.createEditModelAndView(legalText, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final LegalText legalText, final String message) {
		ModelAndView result;

		final String requestURI = "legalText/administrator/edit.do";
		final Boolean view = false;

		result = new ModelAndView("legalText/edit");
		result.addObject("legalText", legalText);
		result.addObject("requestURI", requestURI);
		result.addObject("view", view);

		return result;
	}

}
