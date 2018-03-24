
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/actor/message")
public class ActorMessageController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private FolderService	folderService;

	@Autowired
	private MessageService	messageService;


	// Constructors -----------------------------------------------------------

	public ActorMessageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer folderId) {
		ModelAndView result;
		Collection<Folder> folders;
		Collection<Folder> allFolders;
		Folder folder;
		Boolean hasParent;
		Collection<Message> messages;

		if (folderId == null) {
			folder = this.folderService.findRoot();
			hasParent = false;
		} else {
			folder = this.folderService.findOne(folderId);
			hasParent = folder.getFather() != null;
		}

		folders = folder.getFolders();
		messages = folder.getMessages();
		allFolders = this.folderService.findFoldersByPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("folder", folder);
		result.addObject("hasParent", hasParent);
		result.addObject("folders", folders);
		result.addObject("allFolders", allFolders);
		result.addObject("messages", messages);
		result.addObject("requestURI", "actor/message/list.do?" + folder.getId());
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);

		result = new ModelAndView("message/display");
		result.addObject("messageDisplay", message);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer messageId, @RequestParam final Integer currentFolderId) {
		ModelAndView result;

		this.messageService.delete(messageId);

		result = new ModelAndView("redirect:list.do?folderId=" + currentFolderId);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView result;
		MessageForm messageForm;

		messageForm = new MessageForm();
		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final MessageForm messageForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else if (!this.messageService.checkDestination(messageForm.getDestination()))
			result = this.createEditModelAndView(messageForm, "message.destination.error");
		else
			try {
				this.messageService.send(messageForm);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
				oops.printStackTrace();
			}

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public ModelAndView move(@RequestParam final Integer messageId, @RequestParam final Integer currentFolderId, @RequestParam final Integer targetFolderId) {
		ModelAndView result;
		Message message;
		Folder targetFolder;

		message = this.messageService.findOne(messageId);
		targetFolder = this.folderService.findOne(targetFolderId);
		this.messageService.move(message, targetFolder);
		result = new ModelAndView("redirect:list.do?folderId=" + currentFolderId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		final String requestURI = "actor/message/send.do";

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
