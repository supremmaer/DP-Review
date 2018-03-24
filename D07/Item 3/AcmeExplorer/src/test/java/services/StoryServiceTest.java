
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	@Autowired
	private StoryService	storyService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ActorService	actorService;


	@Test
	public void testFindAll() {
		Collection<Story> story;

		story = this.storyService.findAll();
		Assert.isTrue(!story.isEmpty());
	}

	@Test
	public void testSave() {
		Story story, saved;
		Collection<Story> stories;
		Trip trip;
		Actor actor;
		Explorer explorer;
		Collection<String> attachments;

		trip = this.tripService.findAll().iterator().next();
		super.authenticate("explorer2");
		attachments = new HashSet<String>();
		attachments.add("http://www.testAttachment.com");
		story = this.storyService.create(trip.getId());
		story.setText("Test");
		story.setTitle("Title test");
		story.setAttachments(attachments);
		actor = this.actorService.findByPrincipal();
		explorer = (Explorer) actor;
		story.setExplorer(explorer);

		saved = this.storyService.save(story);
		stories = this.storyService.findAll();
		super.unauthenticate();
		Assert.isTrue(stories.contains(saved));
	}

	@Test
	public void testDelete() {
		Story story, saved;
		Collection<Story> stories;
		Trip trip;
		Actor actor;
		Explorer explorer;
		Collection<String> attachments;

		trip = this.tripService.findAll().iterator().next();
		super.authenticate("explorer2");
		attachments = new HashSet<String>();
		attachments.add("http://www.testAttachment.com");
		story = this.storyService.create(trip.getId());
		story.setText("Test");
		story.setTitle("Title test");
		story.setAttachments(attachments);
		actor = this.actorService.findByPrincipal();
		explorer = (Explorer) actor;
		story.setExplorer(explorer);

		saved = this.storyService.save(story);
		this.storyService.delete(saved);
		stories = this.storyService.findAll();
		Assert.isTrue(!stories.contains(saved));

	}
}
