
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	//Managed Repository
	@Autowired
	private FolderRepository	folderRepository;

	//Supporting Repository
	@Autowired
	private ActorService		actorService;


	//Constructor
	public FolderService() {
		super();
	}

	//CRUD methods
	//11.4
	public Folder create() {
		Folder result;

		result = new Folder();
		result.setMessages(new ArrayList<Message>());

		return result;
	}

	public Folder savePrincipal(final Folder folder) {
		Assert.notNull(folder);
		Folder result;
		Folder parent;
		Collection<Folder> temp;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Hibernate.initialize(actor.getFolders());
		temp = actor.getFolders();
		if (temp.contains(folder))
			temp.remove(folder);

		if (temp.contains(folder.getFather())) {
			parent = folder.getFather();
			for (final Folder f : parent.getFolders())
				Assert.isTrue(!f.getName().equals(folder.getName()));
		}

		result = this.folderRepository.save(folder);
		temp.add(result);
		actor.setFolders(temp);
		this.actorService.save(actor);

		return result;
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		Folder result;

		result = this.folderRepository.save(folder);
		this.actorService.isSpam(folder.getName(), this.actorService.findByPrincipal());

		return result;
	}

	public void delete(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(folder.getId() != 0);
		Assert.isTrue(this.folderRepository.exists(folder.getId()));
		if (folder.isSystemFolders() == false) {
			final Actor actor = this.actorService.findByPrincipal();
			final Collection<Folder> temp = actor.getFolders();
			temp.remove(folder);
			actor.setFolders(temp);
			this.actorService.save(actor);
			this.folderRepository.delete(folder);
		}
	}

	public Collection<Folder> findAll() {
		Collection<Folder> result;

		result = this.folderRepository.findAll();

		return result;
	}

	public Folder findRoot() {
		Folder result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		result = this.findFolderByActorAndName(actor, "Root");

		return result;
	}

	public Folder findInboxByActor(final Actor actor) {
		Folder result;

		result = this.findFolderByActorAndName(actor, "Inbox");

		return result;
	}

	public Folder findNotificationBoxByActor(final Actor actor) {
		Folder result;

		result = this.findFolderByActorAndName(actor, "Notification Box");

		return result;
	}

	public Folder findFolderByActorAndName(final Actor actor, final String name) {
		Assert.notNull(actor);
		Assert.notNull(name);

		Folder result;

		result = this.folderRepository.findFolderByActorIDAndName(actor.getId(), name);

		Assert.notNull(result);

		return result;
	}
	public Collection<Folder> findFolderByName(final String name) {
		Assert.notNull(name);

		Collection<Folder> result;

		result = this.folderRepository.findByName(name);

		return result;
	}
	public Collection<Folder> findByMessage(final Message message) {
		Assert.notNull(message);
		Collection<Folder> folders;
		final Collection<Folder> result = new HashSet<Folder>();

		folders = this.folderRepository.findByMessageID(message.getId());
		for (final Folder f : folders)
			if (this.actorService.findByPrincipal().getFolders().contains(f))
				result.add(f);

		Assert.notNull(result);

		return result;
	}

	public Collection<Folder> initSystemFolders() {
		Collection<Folder> result;
		Collection<Folder> folders;
		Collection<Folder> foldersAux;

		result = new ArrayList<Folder>();
		folders = new ArrayList<Folder>();
		foldersAux = new ArrayList<Folder>();

		Folder root;
		Folder rootAux;
		Folder inbox;
		Folder outbox;
		Folder notificationbox;
		Folder trashbox;
		Folder spambox;

		root = this.create();
		root.setName("Root");
		root.setSystemFolders(true);
		root.setMessages(new ArrayList<Message>());
		rootAux = this.folderRepository.save(root);

		inbox = this.create();
		inbox.setName("Inbox");
		inbox.setSystemFolders(true);
		inbox.setFather(rootAux);
		inbox.setMessages(new ArrayList<Message>());
		inbox.setFolders(new ArrayList<Folder>());
		outbox = this.create();
		outbox.setName("Outbox");
		outbox.setSystemFolders(true);
		outbox.setFather(rootAux);
		outbox.setMessages(new ArrayList<Message>());
		outbox.setFolders(new ArrayList<Folder>());
		notificationbox = this.create();
		notificationbox.setName("Notification Box");
		notificationbox.setSystemFolders(true);
		notificationbox.setFather(rootAux);
		notificationbox.setMessages(new ArrayList<Message>());
		notificationbox.setFolders(new ArrayList<Folder>());
		trashbox = this.create();
		trashbox.setName("TrashBox");
		trashbox.setSystemFolders(true);
		trashbox.setFather(rootAux);
		trashbox.setMessages(new ArrayList<Message>());
		trashbox.setFolders(new ArrayList<Folder>());
		spambox = this.create();
		spambox.setName("Spam");
		spambox.setSystemFolders(true);
		spambox.setFather(rootAux);
		spambox.setMessages(new ArrayList<Message>());
		spambox.setFolders(new ArrayList<Folder>());

		folders.add(spambox);
		folders.add(trashbox);
		folders.add(notificationbox);
		folders.add(outbox);
		folders.add(inbox);

		foldersAux = this.folderRepository.save(folders);

		rootAux.setFolders(foldersAux);
		this.folderRepository.save(rootAux);

		result.add(rootAux);
		result.addAll(foldersAux);

		return result;
	}

	public Folder findOne(final Integer folderId) {
		Folder result;

		result = this.folderRepository.findById(folderId);
		this.checkPrincipal(result);

		Assert.notNull(result);

		return result;
	}

	public Collection<Folder> findChildren(final Integer folderId) {
		Folder folder;
		Collection<Folder> result;

		folder = this.findOne(folderId);
		result = folder.getFolders();

		Assert.notNull(result);

		return result;
	}

	public Collection<Folder> findFoldersByPrincipal() {
		Collection<Folder> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		result = actor.getFolders();

		return result;
	}
	//Checkers
	public void checkPrincipal(final Folder folder) {
		Collection<Folder> folders;

		folders = this.findFoldersByPrincipal();
		Assert.isTrue(folders.contains(folder));
	}
	public void checkPrincipal(final int folderId) {
		Folder folder;
		folder = this.folderRepository.findOne(folderId);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getFolders().contains(folder));
	}

}
