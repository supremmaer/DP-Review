
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SponsorService			sponsorService;


	//Constructor
	public SponsorshipService() {
		super();
	}

	//CRUD methods

	public Sponsorship create(final Trip trip) {
		Assert.notNull(trip);
		Sponsorship result;

		result = new Sponsorship();
		result.setTrip(trip);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship, final int sponsorId) {
		Assert.notNull(sponsorship);
		Assert.notNull(sponsorId);
		Sponsorship result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(sponsorId);
		this.actorService.isSpam(sponsorship.getBanner(), this.actorService.findByPrincipal());
		this.actorService.isSpam(sponsorship.getInfoPage(), this.actorService.findByPrincipal());
		result = this.sponsorshipRepository.save(sponsorship);
		if (sponsorship.getId() == 0) {
			sponsor.addSponsorship(result);
			this.sponsorService.save(sponsor);
		}

		return result;
	}
	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));

		this.sponsorshipRepository.delete(sponsorship);
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);

		return sponsorship;
	}
}
