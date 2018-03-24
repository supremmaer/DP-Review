
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message save(final Message message) {
		Assert.notNull(message);

		Message result;

		result = this.messageRepository.save(message);
		this.actorService.isSpam(message.getBody(), this.actorService.findByPrincipal());
		this.actorService.isSpam(message.getSubject(), this.actorService.findByPrincipal());
		this.actorService.isSpam(message.getPriority(), this.actorService.findByPrincipal());

		return result;
	}

	//11.3
	public Message createMessage(final Collection<Actor> recipients) {
		Assert.notNull(recipients);

		Message result;
		Date moment;
		Actor sender;

		moment = new Date();
		result = new Message();
		sender = this.actorService.findByPrincipal();
		result.setActors(recipients);
		result.setActor(sender);
		result.setMoment(moment);

		return result;
	}
	public Message createNotification() {
		Message result;
		Collection<Actor> recipients;

		recipients = this.actorService.findAll();
		result = this.createMessage(recipients);

		return result;
	}
	public Collection<Message> findAllByFolder(final int folderId) {
		Collection<Message> result;
		this.folderService.checkPrincipal(folderId);
		result = this.messageRepository.findByFolderId(folderId);
		return result;
	}
	//11.2
	public void delete(final Message message) {
		this.checkPrincipal(message);
		Actor actor;
		Folder trashbox;
		Collection<Message> temp;

		actor = this.actorService.findByPrincipal();
		trashbox = this.folderService.findFolderByActorAndName(actor, "TrashBox");
		if (this.messageRepository.findByFolderId(trashbox.getId()).contains(message)) {
			temp = trashbox.getMessages();
			temp.remove(message);
			trashbox.setMessages(temp);
			this.folderService.save(trashbox);
		} else
			this.move(message, trashbox);
	}

	public Boolean checkDestination(final String email) {
		Collection<Actor> actors;
		Boolean result;

		if (email.equals("NOTIFICATION"))
			result = this.actorService.findByPrincipal() instanceof Administrator;
		else {
			actors = this.actorService.findByEmail(email);
			result = !actors.isEmpty();
		}

		return result;
	}
	public void delete(final Integer messageId) {
		final Message message = this.messageRepository.findOne(messageId);

		this.delete(message);
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public Message findOne(final Integer messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);
		this.checkPrincipal(result);

		return result;
	}

	//Other business methods

	//11.3
	public Message send(final MessageForm messageForm) {
		Message result;
		Message message;
		Collection<Actor> recipients;
		final Boolean notification = messageForm.getDestination().equals("NOTIFICATION");

		if (notification) {
			Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);
			recipients = this.actorService.findAll();
		} else
			recipients = this.actorService.findByEmail(messageForm.getDestination());
		message = this.createMessage(recipients);
		message.setPriority(messageForm.getPriority());
		message.setSubject(messageForm.getSubject());
		message.setBody(messageForm.getBody());
		if (notification)
			result = this.sendNotification(message);
		else
			result = this.send(message);

		return result;
	}

	public Message send(final Message message) {
		Assert.notNull(message);
		this.checkPrincipal(message);

		Message result;
		Collection<Actor> recipients;
		Folder recipientFolder;
		Actor sender;
		Folder senderFolder;

		sender = message.getActor();
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		recipients = message.getActors();
		this.actorService.isSpam(message.getBody(), message.getActor());
		this.actorService.isSpam(message.getSubject(), message.getActor());
		result = this.messageRepository.save(message);

		senderFolder = this.folderService.findFolderByActorAndName(sender, "Outbox");
		senderFolder.addMessage(result);
		this.folderService.save(senderFolder);
		for (final Actor recipient : recipients) {
			recipientFolder = this.folderService.findInboxByActor(recipient);
			recipientFolder.addMessage(result);
			this.folderService.save(recipientFolder);
		}

		return result;
	}

	public Message sendNotification(final Message message) {
		Assert.notNull(message);
		this.checkPrincipal(message);

		Message result;
		Collection<Actor> recipients;
		Folder recipientFolder;

		message.setMoment(new Date(System.currentTimeMillis() - 1));
		recipients = message.getActors();
		this.actorService.isSpam(message.getBody(), message.getActor());
		this.actorService.isSpam(message.getSubject(), message.getActor());
		result = this.messageRepository.save(message);

		for (final Actor recipient : recipients) {
			recipientFolder = this.folderService.findNotificationBoxByActor(recipient);
			recipientFolder.addMessage(result);
			this.folderService.save(recipientFolder);
		}

		return result;
	}
	//11.2
	public Message move(final Message message, final Folder folder) {
		this.folderService.checkPrincipal(folder);
		this.checkPrincipal(message);
		Assert.notNull(folder);

		final Collection<Folder> currentFolders;

		currentFolders = this.folderService.findByMessage(message);
		for (final Folder f : currentFolders) {
			f.removeMessage(message);
			this.folderService.save(f);
		}
		folder.addMessage(message);

		this.folderService.save(folder);

		return message;
	}

	//Checkers

	public void checkPrincipal(final Message message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.equals(message.getActor()) || message.getActors().contains(actor));
	}

	public void checkSpamWord(final Message message) {

	}

}
