
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	//Relationships
	private Collection<Application>		applications;
	private Finder						finder;
	private Collection<Story>			stories;
	private Collection<Contact>			contacts;
	private Collection<SurvivalClass>	survivalClasses;


	@NotNull
	@OneToMany(mappedBy = "explorer")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return this.finder;
	}

	@NotNull
	@OneToMany(mappedBy = "explorer")
	public Collection<Story> getStories() {
		return this.stories;
	}

	@NotNull
	@ManyToMany
	public Collection<Contact> getContacts() {
		return this.contacts;
	}

	@NotNull
	@ManyToMany
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	public void setContacts(final Collection<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

	public void addSurvivalClasses(final SurvivalClass survivalClass) {
		this.survivalClasses.add(survivalClass);
	}

	public void removeSurvivalClasses(final SurvivalClass survivalClass) {
		this.survivalClasses.remove(survivalClass);
	}
}
