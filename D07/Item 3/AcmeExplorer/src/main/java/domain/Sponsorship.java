
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String	banner;
	private String	infoPage;


	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	@NotBlank
	public String getInfoPage() {
		return this.infoPage;
	}

	public void setInfoPage(final String infoPage) {
		this.infoPage = infoPage;
	}


	//Relatioships
	private Trip		trip;
	private CreditCard	cc;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	@ManyToOne(optional = true)
	public CreditCard getCc() {
		return this.cc;
	}

	public void setCc(final CreditCard cc) {
		this.cc = cc;
	}
	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
