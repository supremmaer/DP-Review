
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	//Relationships
	private Collection<Sponsorship>	sponsorships;


	@NotNull
	@OneToMany
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	public void addSponsorship(final Sponsorship sponsorship) {
		this.sponsorships.add(sponsorship);
	}

	public void removeSponsorship(final Sponsorship sponsorship) {
		this.sponsorships.remove(sponsorship);
	}
}
