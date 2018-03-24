
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date	moment;
	private String	status;
	private String	comment;
	private String	rejectingReason;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getRejectingReason() {
		return this.rejectingReason;
	}

	public void setRejectingReason(final String rejectingReason) {
		this.rejectingReason = rejectingReason;
	}


	//Relationships

	private Explorer	explorer;
	private Trip		trip;
	private CreditCard	creditCard;


	@NotNull
	@ManyToOne(optional = false)
	public Explorer getExplorer() {
		return this.explorer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	@Valid
	@ManyToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setExplorer(final Explorer explorer) {
		this.explorer = explorer;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
