
package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import controllers.AbstractController;
import domain.Finder;

import javax.validation.Valid;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService	finderService;

	


	//Constructor -------------------------------------------------------------

	public FinderExplorerController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() throws Exception {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findByPrincipal();
		if (finder == null)
			finder = this.finderService.create();

		result = this.createEditModelAndView(finder);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder savedFinder;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				savedFinder = this.finderService.save(finder);
				result = new ModelAndView("redirect:/trip/list.do?finderId=" + savedFinder.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		final String requestURI = "finder/explorer/edit.do";

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
