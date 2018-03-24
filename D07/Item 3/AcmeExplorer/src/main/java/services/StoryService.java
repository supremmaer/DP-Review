
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StoryRepository;
import domain.Actor;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class StoryService {

	//Managed Repository
	@Autowired
	private StoryRepository	storyRepository;

	//Supporting Services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private TripService		tripService;


	//Constructor
	public StoryService() {
		super();
	}

	//CRUD methods
	public Story create(final int tripId) {
		Story result;
		Trip trip;
		Collection<String> attachments;
		Explorer explorer;

		attachments = new HashSet<String>();
		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		Assert.isTrue(trip.getEndDate().before(new Date()));
		result = new Story();
		result.setTrip(trip);
		result.setAttachments(attachments);
		explorer = this.explorerService.findByPrincipal();
		result.setExplorer(explorer);

		return result;
	}

	public Story save(final Story story) {
		Assert.notNull(story);
		if (story.getId() != 0)
			Assert.isTrue(this.explorerService.findByPrincipal().getStories().contains(story));

		Actor actor;
		Story result;

		actor = this.actorService.findByPrincipal();
		this.actorService.isSpam(story.getText(), actor);
		this.actorService.isSpam(story.getTitle(), actor);
		for (final String s : story.getAttachments())
			this.actorService.isSpam(s, actor);
		result = this.storyRepository.save(story);

		return result;
	}

	public void delete(final Story story) {
		Assert.notNull(story);
		Assert.isTrue(story.getId() != 0);
		Assert.isTrue(this.storyRepository.exists(story.getId()));

		this.storyRepository.delete(story);
	}

	public Collection<Story> findAll() {
		Collection<Story> result;

		result = this.storyRepository.findAll();

		return result;
	}

	public Collection<Story> findByTrip(final Integer tripId) {
		Collection<Story> result;

		result = this.storyRepository.findbyTripID(tripId);

		return result;
	}

	public Story findOne(final int storyId) {
		Story story;

		story = this.storyRepository.findOne(storyId);

		return story;
	}
}
