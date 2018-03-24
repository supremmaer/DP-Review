
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	address;
	private boolean	suspicious;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	public boolean isSuspicious() {
		return this.suspicious;
	}

	public void setSuspicious(final boolean suspicious) {
		this.suspicious = suspicious;
	}


	//Relationships

	private UserAccount					userAccount;
	private Collection<SocialIdentity>	socialIdentities;
	private Collection<Message>			MessagesSent;
	private Collection<Message>			MessagesReceived;
	private Collection<Folder>			folders;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@OneToMany
	public Collection<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	public void setSocialIdentities(final Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	public void addSocialIdentity(final SocialIdentity socialIdentity) {
		this.socialIdentities.add(socialIdentity);
	}

	public void removeSocialIdentity(final SocialIdentity socialIdentity) {
		this.socialIdentities.remove(socialIdentity);
	}

	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<Message> getMessagesSent() {
		return this.MessagesSent;
	}

	@NotNull
	@ManyToMany(mappedBy = "actors")
	public Collection<Message> getMessagesReceived() {
		return this.MessagesReceived;
	}

	public void setMessagesSent(final Collection<Message> messagesSent) {
		this.MessagesSent = messagesSent;
	}

	public void setMessagesReceived(final Collection<Message> messagesReceived) {
		this.MessagesReceived = messagesReceived;
	}

	@OneToMany
	@NotNull
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

}
