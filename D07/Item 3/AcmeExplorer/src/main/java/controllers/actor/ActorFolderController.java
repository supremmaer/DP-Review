
package controllers.actor;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import controllers.AbstractController;
import domain.Folder;

import javax.validation.Valid;

@Controller
@RequestMapping("/actor/folder")
public class ActorFolderController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private FolderService	folderService;


	// Constructors -----------------------------------------------------------

	public ActorFolderController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer folderId) {
		ModelAndView result;
		Folder father;
		Folder folder;

		folder = new Folder();
		father = this.folderService.findOne(folderId);
		folder.setFather(father);
		folder.setSystemFolders(false);

		result = this.createEditModelAndView(folder);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		result = this.createEditModelAndView(folder);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.savePrincipal(folder);
				result = new ModelAndView("redirect:/actor/message/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Folder folder) {
		ModelAndView result;

		try {
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:/actor/message/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, "folder.commit.error");
			oops.printStackTrace();
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Folder folder, final String message) {
		ModelAndView result;
		Collection<Folder> temp;
		Collection<Folder> folders;

		final String requestURI = "actor/folder/edit.do";
		temp = this.folderService.findFoldersByPrincipal();
		folders = new HashSet<Folder>(temp);
		folders.remove(folder.getFather());
		folders.remove(folder);

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("folders", folders);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
