
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	//Managed Repository
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	//Supporting Services
	@Autowired
	private ActorService				actorService;


	//Constructor
	public SocialIdentityService() {
		super();
	}

	public SocialIdentity create() {
		SocialIdentity socialIdentity;

		socialIdentity = new SocialIdentity();

		return socialIdentity;
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);

		SocialIdentity result, db;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		if (socialIdentity.getId() != 0)
			Assert.isTrue(actor.getSocialIdentities().contains(socialIdentity));

		this.actorService.isSpam(socialIdentity.getNick(), actor);
		this.actorService.isSpam(socialIdentity.getPhoto(), actor);
		this.actorService.isSpam(socialIdentity.getProfile(), actor);
		this.actorService.isSpam(socialIdentity.getSocialNetwork(), actor);
		db = this.findOne(socialIdentity.getId());
		result = this.socialIdentityRepository.save(socialIdentity);
		actor.removeSocialIdentity(db);
		actor.addSocialIdentity(result);
		this.actorService.save(actor);

		return result;
	}

	public void delete(final SocialIdentity socialIdentity) {
		Actor actor;

		Assert.notNull(socialIdentity);
		Assert.isTrue(socialIdentity.getId() != 0);
		Assert.isTrue(this.socialIdentityRepository.exists(socialIdentity.getId()));

		actor = this.actorService.findByPrincipal();

		actor.removeSocialIdentity(socialIdentity);
		this.actorService.save(actor);
		this.socialIdentityRepository.delete(socialIdentity);
	}

	public Collection<SocialIdentity> findAll() {
		Collection<SocialIdentity> result;

		result = this.socialIdentityRepository.findAll();

		return result;
	}

	public Collection<SocialIdentity> findByPincipal() {
		Collection<SocialIdentity> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		result = actor.getSocialIdentities();

		return result;
	}

	public SocialIdentity findOne(final int socialIdentityId) {
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityRepository.findOne(socialIdentityId);
		return socialIdentity;
	}

}
