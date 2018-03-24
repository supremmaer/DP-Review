
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	private String	name;
	private Boolean	systemFolders;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Boolean isSystemFolders() {
		return this.systemFolders;
	}

	@NotNull
	public Boolean getSystemFolders() {
		return this.systemFolders;
	}

	public void setSystemFolders(final Boolean valor) {
		this.systemFolders = valor;
	}


	//Relationships
	private Folder				father;
	private Collection<Folder>	folders;
	private Collection<Message>	messages;


	@Valid
	@ManyToOne(optional = true)
	public Folder getFather() {
		return this.father;
	}

	@NotNull
	@OneToMany(mappedBy = "father")
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	@NotNull
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	public void setFather(final Folder father) {
		this.father = father;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	public void addMessage(final Message message) {
		this.messages.add(message);
	}

	public void removeMessage(final Message message) {
		if (this.messages.contains(message))
			this.messages.remove(message);
	}

}
