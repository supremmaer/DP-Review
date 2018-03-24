
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class AuditRecord extends DomainEntity {

	private Date				moment;
	private String				title;
	private String				description;
	private Collection<String>	attachments;
	private boolean				draftMode;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotNull
	@EachURL
	@ElementCollection(targetClass = String.class)
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	public boolean getDraftMode() {
		return this.draftMode;
	}

	public boolean isDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final boolean draftMode) {
		this.draftMode = draftMode;
	}


	//Relationships

	private Auditor	auditor;
	private Trip	trip;


	@NotNull
	@ManyToOne(optional = false)
	@Valid
	public Auditor getAuditor() {
		return this.auditor;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
