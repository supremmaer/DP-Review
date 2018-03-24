
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;
import domain.SystemConfiguration;
import forms.SystemConfigurationForm;

import javax.validation.Valid;

@Controller
@RequestMapping("/systemConfiguration/administrator")
public class SystemConfigurationAdministratorController {

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors -----------------------------------------------------------

	public SystemConfigurationAdministratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		SystemConfiguration systemConfiguration;
		SystemConfigurationForm systemConfigurationForm;

		systemConfiguration = this.systemConfigurationService.findSystemConfiguration();
		systemConfigurationForm = this.systemConfigurationService.toForm(systemConfiguration);
		result = this.createEditModelAndView(systemConfigurationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final SystemConfigurationForm systemConfigurationForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(systemConfigurationForm);
		else
			try {
				this.systemConfigurationService.fromForm(systemConfigurationForm);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(systemConfigurationForm, "systemConfiguration.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		SystemConfiguration systemConfiguration;
		SystemConfigurationForm systemConfigurationForm;

		systemConfiguration = this.systemConfigurationService.findSystemConfiguration();
		systemConfigurationForm = this.systemConfigurationService.toForm(systemConfiguration);
		result = this.createEditModelAndView(systemConfigurationForm);
		final Boolean view = true;
		result.addObject("view", view);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SystemConfigurationForm systemConfigurationForm) {
		ModelAndView result;

		result = this.createEditModelAndView(systemConfigurationForm, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final SystemConfigurationForm systemConfigurationForm, final String message) {
		ModelAndView result;

		final String requestURI = "systemConfiguration/administrator/edit.do";
		final Boolean view = false;

		result = new ModelAndView("systemConfiguration/edit");
		result.addObject("systemConfigurationForm", systemConfigurationForm);
		result.addObject("requestURI", requestURI);
		result.addObject("view", view);

		return result;
	}

}
