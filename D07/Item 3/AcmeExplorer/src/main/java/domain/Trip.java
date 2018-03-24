
package domain;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	private String				ticker;
	private String				title;
	private String				description;
	@SuppressWarnings("unused")
	private Double				price;
	private Date				publicationDate;
	private Date				startDate;
	private Date				endDate;
	private Map<String, String>	tag;
	private String				explorerRequirements;
	private String				cancellationReason;


	@NotBlank
	@Pattern(regexp = "[0-9]{6}-[A-Z]{4}")
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
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

	@Transient
	public Double getPrice() {
		Double res = 0.0;
		for (final Stage e : this.getStages())
			res = res + e.getPrice();
		return res;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@ElementCollection
	public Map<String, String> getTag() {
		return this.tag;
	}

	public void setTag(final Map<String, String> tag) {
		this.tag = tag;
	}

	public void addTag(final String tag, final String value) {
		this.tag.put(tag, value);
	}

	public void removeTag(final String tag) {
		this.tag.remove(tag);
	}
	@NotBlank
	public String getExplorerRequirements() {
		return this.explorerRequirements;
	}

	public void setExplorerRequirements(final String explorerRequirements) {
		this.explorerRequirements = explorerRequirements;
	}

	public String getCancellationReason() {
		return this.cancellationReason;
	}

	public void setCancellationReason(final String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}


	//Relationships
	private Collection<Application>		applications;
	private Collection<Sponsorship>		sponsorships;
	private Manager						manager;
	private Ranger						ranger;
	private LegalText					legalText;
	private Collection<Stage>			stages;
	private Category					category;
	private AuditRecord					auditRecord;
	private Collection<Note>			notes;
	private Collection<SurvivalClass>	survivalClasses;


	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Ranger getRanger() {
		return this.ranger;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public LegalText getLegalText() {
		return this.legalText;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Stage> getStages() {
		return this.stages;
	}

	@NotNull
	@OneToMany(mappedBy = "trip")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	@NotNull
	@OneToMany
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	@Valid
	@OneToOne(mappedBy = "trip")
	public AuditRecord getAuditRecord() {
		return this.auditRecord;
	}

	public void setAuditRecord(final AuditRecord auditRecord) {
		this.auditRecord = auditRecord;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	public void addStage(final Stage stage) {
		this.stages.add(stage);
	}

	public void removeStage(final Stage stage) {
		this.stages.remove(stage);
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

	public void addSurvivalClass(final SurvivalClass survivalClass) {
		this.survivalClasses.add(survivalClass);
	}

	public void removeSurvivalClass(final SurvivalClass survivalClass) {
		this.survivalClasses.remove(survivalClass);
	}

	@NotNull
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

}
