
package controllers.sponsor;

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

import security.LoginService;
import security.UserAccount;
import services.SponsorService;
import services.SponsorshipService;
import services.TripService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	//Services

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private TripService			tripService;


	//Constructor

	public SponsorshipSponsorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Sponsor sponsor;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		sponsor = this.sponsorService.findByUserAccount(userAccount);
		sponsorships = sponsor.getSponsorships();

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");
		result.addObject("sponsorId", sponsor.getId());
		return result;
	}

	//Edit

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer tripId) {
		ModelAndView result;
		Sponsorship sponsorship;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		sponsorship = this.sponsorshipService.create(trip);
		result = this.createEditModelAndView(sponsorship);
		result.addObject("tripId", tripId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId, @RequestParam final int tripId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		Assert.notNull(sponsorship);

		result = this.createEditModelAndView(sponsorship);
		result.addObject("tripId", tripId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Sponsorship sponsorship, final BindingResult binding, @RequestParam(required = true) final Integer tripId) {
		ModelAndView result;
		Sponsor sponsor;
		UserAccount userAccount;
		Trip trip;

		userAccount = LoginService.getPrincipal();
		sponsor = this.sponsorService.findByUserAccount(userAccount);
		trip = this.tripService.findOne(tripId);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsorship);
			result.addObject("tripId", tripId);
		} else
			try {
				this.sponsorshipService.save(sponsorship, sponsor.getId());

				result = new ModelAndView("redirect:/trip/list.do");
				result.addObject("tripId", tripId);
				result.addObject("trip", trip);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
				result.addObject("tripId", tripId);
			}

		return result;
	}

	// Ancillary Methods
	private ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");

		result.addObject("sponsorship", sponsorship);
		result.addObject("message", messageCode);

		return result;
	}

}
