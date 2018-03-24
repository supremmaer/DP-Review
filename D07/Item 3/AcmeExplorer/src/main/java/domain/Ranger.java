
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Ranger extends Actor {

	//Relationships
	private Curricula			curricula;
	private Collection<Trip>	trips;


	@Valid
	@OneToOne(optional = true)
	public Curricula getCurricula() {
		return this.curricula;
	}

	@NotNull
	@OneToMany(mappedBy = "ranger")
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setCurricula(final Curricula curricula) {
		this.curricula = curricula;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

}
