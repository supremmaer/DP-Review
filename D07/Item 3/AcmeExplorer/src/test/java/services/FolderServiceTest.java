
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void testFindAll() {
		Collection<Folder> folders;

		folders = this.folderService.findAll();
		Assert.isTrue(!folders.isEmpty());
	}

	@Test
	public void testSave() {
		Folder folder, saved;
		Collection<Folder> folders, updated;
		Collection<Message> messages;

		super.authenticate("ranger1");
		folders = Collections.emptySet();
		messages = Collections.emptySet();
		folder = this.folderService.create();
		folder.setName("TEST folder");
		folder.setFolders(folders);
		folder.setMessages(messages);
		folder.setSystemFolders(false);
		saved = this.folderService.save(folder);
		updated = this.folderService.findAll();
		Assert.isTrue(updated.contains(saved), "this");
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		Folder folder, saved;
		Collection<Folder> folders, updated;
		Collection<Message> messages;

		super.authenticate("manager1");
		folders = Collections.emptySet();
		messages = Collections.emptySet();
		folder = this.folderService.create();
		folder.setName("TEST folder");
		folder.setFolders(folders);
		folder.setMessages(messages);
		folder.setSystemFolders(false);
		saved = this.folderService.save(folder);
		updated = this.folderService.findAll();
		Assert.isTrue(updated.contains(saved));
		super.authenticate(null);

		this.folderService.delete(saved);
		updated = this.folderService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}

	@Test
	public void testFindFolderByActorAndName() {
		Actor actor;
		Folder folder;

		super.authenticate("manager1");
		actor = this.actorService.findByPrincipal();
		folder = this.folderService.findFolderByActorAndName(actor, "Spam");
		Assert.isTrue(folder != null);
		super.authenticate(null);
	}

	@Test
	public void testFindInboxByActor() {
		Folder folder;
		Actor actor;

		super.authenticate("ranger4");
		actor = this.actorService.findByPrincipal();
		folder = this.folderService.findInboxByActor(actor);
		Assert.isTrue(folder != null);
		super.authenticate(null);

	}

	@Test
	public void testFindFolderByName() {
		Collection<Folder> folders;

		folders = this.folderService.findFolderByName("Inbox");
		Assert.isTrue(!folders.isEmpty());
	}

}
