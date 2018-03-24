
package controllers.explorer;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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
import services.ApplicationService;
import services.ExplorerService;
import services.TripService;
import controllers.AbstractController;
import domain.Application;
import domain.Explorer;
import domain.Trip;

@Controller
@RequestMapping("/application/explorer")
public class ApplicationExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private TripService			tripService;


	//Constructor -------------------------------------------------------------

	public ApplicationExplorerController() {
		super();
	}

	//Create 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Application application;
		Explorer explorer;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		explorer = this.explorerService.findByPrincipal();
		application = this.applicationService.create(explorer, trip);
		result = this.createEditModelAndView(application);

		return result;
	}

	//Listing ----------------------------------------------------------------7

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;
		Explorer explorer;
		Calendar calendar;

		explorer = this.explorerService.findByPrincipal();
		applications = this.applicationService.findByExplorer(explorer);
		calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 31);
		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/explorer/list.do");
		result.addObject("explorerID", explorer.getId());
		result.addObject("date", calendar.getTime());

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Explorer explorer;
		UserAccount userAccount;

		application = this.applicationService.findOne(applicationId);
		userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.isTrue(explorer.getId() == application.getExplorer().getId());
		Assert.notNull(application);

		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		Explorer explorer;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);
		Assert.isTrue(explorer.getId() == application.getExplorer().getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:/application/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@Valid final int applicationId) {
		ModelAndView result;
		Application application;
		Explorer explorer;
		UserAccount userAccount;

		application = this.applicationService.findOne(applicationId);
		userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.isTrue(applicationId != 0);
		Assert.isTrue(explorer.getId() == application.getExplorer().getId());
		Assert.isTrue(application.getTrip().getStartDate().after(new Date()));

		try {
			application.setStatus("CANCELLED");
			this.applicationService.save(application);

		} catch (final Throwable oops) {
		}

		result = new ModelAndView("redirect:/application/explorer/list.do");

		return result;
	}

	// Ancillary Methods
	private ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/create");

		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}
}
