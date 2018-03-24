
package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CreditCardService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;

import javax.validation.Valid;

@Controller
@RequestMapping("/creditcard/explorer")
public class CreditCardExplorerController extends AbstractController {

	//Services

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ApplicationService	applicationService;


	//Constructor

	public CreditCardExplorerController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "applicationId")
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;
		CreditCard creditcard;

		creditcard = this.creditCardService.create();
		result = this.createEditModelAndView(creditcard);
		result.addObject("applicationId", applicationId);
		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int creditCardId, @RequestParam final int applicationId) {
		ModelAndView result;
		CreditCard creditcard;

		creditcard = this.creditCardService.findOne(creditCardId);
		Assert.notNull(creditcard);

		result = this.createEditModelAndView(creditcard);
		result.addObject("applicationId", applicationId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditcard, final BindingResult binding, @RequestParam final Integer applicationId) {
		ModelAndView result;
		CreditCard updatedCreditCard;
		Application application;

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditcard);
		else
			try {
				updatedCreditCard = this.creditCardService.save(creditcard);
				application = this.applicationService.findOne(applicationId);
				application.setCreditCard(updatedCreditCard);
				this.applicationService.addCreditCard(application, updatedCreditCard);
				result = new ModelAndView("redirect:/application/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditcard, "creditcard.commit.error");
			}

		return result;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final CreditCard creditcard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditcard, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final CreditCard creditcard, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditcard", creditcard);
		result.addObject("message", messageCode);

		return result;
	}

}
