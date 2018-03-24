
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	private String	ticker;


	@NotBlank
	@Pattern(regexp = "[0-9]{6}-[A-Z]{4}")
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}


	//Relationships

	private PersonalRecord					personalRecord;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;
	private Collection<EducationRecord>		educationRecords;
	private Collection<EndorserRecord>		endorserRecords;
	private Collection<ProfessionalRecord>	professionalRecords;


	@Valid
	@NotNull
	@OneToOne(optional = false)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	@NotNull
	@OneToMany
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	@NotNull
	@OneToMany
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	@NotNull
	@OneToMany
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	@NotNull
	@OneToMany
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	public void addEducationRecord(final EducationRecord educationRecord) {
		this.educationRecords.add(educationRecord);
	}
	public void removeEducationRecord(final EducationRecord educationRecord) {
		this.educationRecords.remove(educationRecord);
	}

	public void addProfessionalRecord(final ProfessionalRecord professionalRecord) {
		this.professionalRecords.add(professionalRecord);
	}
	public void removeProfessionalRecord(final ProfessionalRecord professionalRecord) {
		this.professionalRecords.remove(professionalRecord);
	}

	public void addEndorserRecord(final EndorserRecord endorserRecord) {
		this.endorserRecords.add(endorserRecord);
	}
	public void removeEndorserRecord(final EndorserRecord endorserRecord) {
		this.endorserRecords.remove(endorserRecord);
	}

	public void addMiscellaneousRecord(final MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecords.add(miscellaneousRecord);
	}
	public void removeMiscellaneousRecord(final MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecords.remove(miscellaneousRecord);
	}
}
