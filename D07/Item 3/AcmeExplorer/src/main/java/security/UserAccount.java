/*
 * UserAccount.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import domain.DomainEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.PROPERTY)
public class UserAccount extends DomainEntity implements UserDetails {

	// Constructors -----------------------------------------------------------

	private static final long	serialVersionUID	= 7254823034213841482L;


	public UserAccount() {
		super();

		this.authorities = new ArrayList<Authority>();
	}


	// Attributes -------------------------------------------------------------

	// UserDetails interface --------------------------------------------------

	private String					username;
	private String					password;
	private Collection<Authority>	authorities;
	private boolean					accountNonLocked;


	@Size(min = 5, max = 32)
	@Column(unique = true)
	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotEmpty
	@Valid
	@ElementCollection
	@Override
	public Collection<Authority> getAuthorities() {
		// WARNING: Should return an unmodifiable copy, but it's not possible with hibernate!
		return this.authorities;
	}

	public void setAuthorities(final Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(final Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(!this.authorities.contains(authority));

		this.authorities.add(authority);
	}

	public void removeAuthority(final Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(this.authorities.contains(authority));

		this.authorities.remove(authority);
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAccountNonLocked(final boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

}
