
package controllers.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CreditCardService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Actor;
import domain.CreditCard;
import domain.Sponsorship;

import javax.validation.Valid;

@Controller
@RequestMapping("/creditcard/sponsor")
public class CreditCardSponsorController extends AbstractController {

	//Services

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ActorService		actorService;


	//Constructor

	public CreditCardSponsorController() {
		super();
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET, params = "sponsorshipId")
	public ModelAndView create(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		CreditCard creditcard;

		creditcard = this.creditCardService.create();
		result = this.createEditModelAndView(creditcard);
		result.addObject("sponsorshipId", sponsorshipId);
		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int creditCardId, @RequestParam final int sponsorshipId) {
		ModelAndView result;
		CreditCard creditcard;

		creditcard = this.creditCardService.findOne(creditCardId);
		Assert.notNull(creditcard);

		result = this.createEditModelAndView(creditcard);
		result.addObject("sponsorshipId", sponsorshipId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditcard, final BindingResult binding, @RequestParam final Integer sponsorshipId) {
		ModelAndView result;
		final CreditCard updatedCreditCard;
		Sponsorship sponsorship;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(creditcard);
			result.addObject("sponsorshipId", sponsorshipId);
		} else
			try {
				updatedCreditCard = this.creditCardService.save(creditcard);
				sponsorship = this.sponsorshipService.findOne(sponsorshipId);
				sponsorship.setCc(updatedCreditCard);
				this.sponsorshipService.save(sponsorship, actor.getId());
				result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditcard, "creditcard.commit.error");
				result.addObject("sponsorshipId", sponsorshipId);
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

		result = new ModelAndView("creditCard/add");
		result.addObject("creditcard", creditcard);
		result.addObject("message", messageCode);

		return result;
	}
}
