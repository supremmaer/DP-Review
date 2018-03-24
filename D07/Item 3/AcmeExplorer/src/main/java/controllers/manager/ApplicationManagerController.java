
package controllers.manager;

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

import services.ApplicationService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Application;
import domain.Manager;

@Controller
@RequestMapping("/application/manager")
public class ApplicationManagerController extends AbstractController {

	//Services

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ManagerService		managerService;


	//Constructor

	public ApplicationManagerController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		applications = this.applicationService.findByManager(manager);
		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/manager/list.do");

		return result;

	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:/application/manager/list.do");
				if (application.getStatus().equals("DUE"))
					application.setRejectingReason(null);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Application application, final BindingResult binding) {
		ModelAndView result;
		try {
			this.applicationService.delete(application);
			result = new ModelAndView("redirect:/application/manager/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(application, "application.commit.error");
			System.out.println(oops);
		}
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

		result = new ModelAndView("application/edit");

		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}

}
