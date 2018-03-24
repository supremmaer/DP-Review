
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import domain.Curricula;
import domain.Ranger;

@Controller
@RequestMapping("/curriculum")
public class CurriculaController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService	rangerService;


	// Constructors -----------------------------------------------------------

	public CurriculaController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rangerId) throws Exception {
		ModelAndView result;
		Ranger ranger;
		Curricula curricula;

		ranger = this.rangerService.findOne(rangerId);
		curricula = ranger.getCurricula();
		if (curricula != null) {
			result = new ModelAndView("curriculum/display");
			result.addObject("curriculum", curricula);
			result.addObject("rangerId", rangerId);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
}
