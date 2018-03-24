
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StoryService;
import controllers.AbstractController;
import domain.Story;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StoryService	storyService;

	


	//Constructor -------------------------------------------------------------

	public StoryExplorerController() {
		super();
	}

	//Listing ----------------------------------------------------------------7

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer tripId) {
		ModelAndView result;
		Story story;

		story = this.storyService.create(tripId);
		result = this.createEditModelAndView(story);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Story story, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:/story/list.do?tripId=" + story.getTrip().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int tripId) {
	//		ModelAndView result;
	//		Story story;
	//
	//		story = this.storyService.findOne(tripId);
	//		Assert.notNull(story);
	//		result = this.createEditModelAndView(story);
	//
	//		return result;
	//	}

	// Ancillary Methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Story story) {
		ModelAndView result;

		result = this.createEditModelAndView(story, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("story/edit");
		result.addObject("story", story);

		result.addObject("message", messageCode);

		return result;
	}
}
