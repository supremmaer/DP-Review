
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.SocialIdentity;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	// Managed Repository ----------------------------------------------------

	@Autowired
	private ActorRepository				actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private SponsorService				sponsorService;

	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private RangerService				rangerService;

	@Autowired
	private FolderService				folderService;


	//Constructor ----------------------------------------------------

	public ActorService() {
		super();
	}

	//CRUD methods ----------------------------------------------------

	public Actor create(final String actorType) {
		Actor result;
		UserAccount userAccount;
		Authority authority;

		userAccount = new UserAccount();
		authority = new Authority();

		if (actorType.equals(Authority.ADMINISTRATOR)) {
			Assert.isTrue(this.findByPrincipal() instanceof Administrator);
			authority.setAuthority(Authority.ADMINISTRATOR);
			result = this.administratorService.create();
		} else if (actorType.equals(Authority.AUDITOR)) {
			Assert.isTrue(this.findByPrincipal() instanceof Administrator);
			authority.setAuthority(Authority.AUDITOR);
			result = this.auditorService.create();
		} else if (actorType.equals(Authority.SPONSOR)) {
			Assert.isTrue(this.findByPrincipal() instanceof Administrator);
			authority.setAuthority(Authority.SPONSOR);
			result = this.sponsorService.create();
		} else if (actorType.equals(Authority.EXPLORER)) {
			authority.setAuthority(Authority.EXPLORER);
			result = this.explorerService.create();
		} else if (actorType.equals(Authority.MANAGER)) {
			Assert.isTrue(this.findByPrincipal() instanceof Administrator);
			authority.setAuthority(Authority.MANAGER);
			result = this.managerService.create();
		} else if (actorType.equals(Authority.RANGER)) {
			authority.setAuthority(Authority.RANGER);
			result = this.rangerService.create();
		} else
			throw new ServiceException("Invalid actor type parameter");

		userAccount.addAuthority(authority);
		userAccount.setAccountNonLocked(true);
		result.setUserAccount(userAccount);

		result.setFolders(this.folderService.initSystemFolders());
		result.setMessagesReceived(new HashSet<Message>());
		result.setMessagesSent(new HashSet<Message>());
		result.setSocialIdentities(new HashSet<SocialIdentity>());

		return result;

	}

	public Actor create(final ActorForm actorForm) {
		Actor result;

		result = this.create(actorForm.getAuthority());
		result.setAddress(actorForm.getAddress());
		result.setEmail(actorForm.getEmail());
		result.setName(actorForm.getName());
		result.setPhone(actorForm.getPhone());
		result.setSurname(actorForm.getSurname());
		result.getUserAccount().setUsername(actorForm.getUsername());
		result.getUserAccount().setPassword(actorForm.getPassword());

		return result;
	}

	public Actor register(final Actor actor) {
		Assert.notNull(actor);
		final Actor result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass = encoder.encodePassword(actor.getUserAccount().getPassword(), null);
		actor.getUserAccount().setPassword(pass);

		result = this.save(actor);

		return result;
	}
	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;

		result = this.actorRepository.save(actor);

		this.isSpam(result.getName(), result);
		this.isSpam(result.getEmail(), result);
		this.isSpam(result.getAddress(), result);
		this.isSpam(result.getPhone(), result);
		this.isSpam(result.getSurname(), result);

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}
	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		return result;
	}

	//	public Actor findByPrincipalCast() {
	//		UserAccount userAccount;
	//
	//		userAccount = LoginService.getPrincipal();
	//		Assert.notNull(userAccount);
	//
	//		Actor result;
	//
	//		if (userAccount.getAuthorities().contains(Authority.ADMINISTRATOR))
	//			result = this.administratorService.findByUserAccount(userAccount);
	//		else if (userAccount.getAuthorities().contains(Authority.AUDITOR))
	//			result = this.auditorService.findByUserAccount(userAccount);
	//		else if (userAccount.getAuthorities().contains(Authority.EXPLORER))
	//			result = this.explorerService.findByUserAccount(userAccount);
	//		else if (userAccount.getAuthorities().contains(Authority.MANAGER))
	//			result = this.managerService.findByUserAccount(userAccount);
	//		else if (userAccount.getAuthorities().contains(Authority.RANGER))
	//			result = this.rangerService.findByUserAccount(userAccount);
	//		else if (userAccount.getAuthorities().contains(Authority.SPONSOR))
	//			result = this.sponsorService.findByUserAccount(userAccount);
	//		else
	//			result = this.findByUserAccount(userAccount);
	//
	//		return result;
	//	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;
		result = this.actorRepository.findByUserAccount(userAccount.getId());

		return result;
	}

	public Actor findOne(final int id) {
		Actor result;
		result = this.actorRepository.findOne(id);

		return result;
	}

	//Other business methods ----------------------------------------------------

	public Boolean isSpam(final String string, final Actor actor) {
		final Boolean result = false;
		String lower;
		if (string != null) {
			lower = string.toLowerCase();

			for (final String s : this.systemConfigurationService.findSystemConfiguration().getSpamWords())
				if (lower.contains(s)) {
					actor.setSuspicious(true);
					this.actorRepository.save(actor);
				}
		}
		return result;
	}

	public boolean banActor(final Actor actor) {
		Assert.notNull(actor);

		boolean result;
		Actor principal;

		principal = this.findByPrincipal();
		result = false;

		Assert.isTrue(principal instanceof Administrator);
		Assert.isTrue(actor.getId() != 0);
		if (actor.isSuspicious()) {
			actor.getUserAccount().setAccountNonLocked(false);
			this.actorRepository.save(actor);
			result = true;
		}

		return result;
	}

	public boolean unbanActor(final Actor actor) {
		Assert.notNull(actor);

		boolean result;
		Actor principal;

		principal = this.findByPrincipal();
		result = false;

		Assert.isTrue(principal instanceof Administrator);
		Assert.isTrue(actor.getId() != 0);

		if (actor.isSuspicious()) {
			actor.getUserAccount().setAccountNonLocked(true);
			this.actorRepository.save(actor);
			result = true;
		}

		return result;
	}

	//
	//	public boolean unbanActor(final Actor actor) {
	//		Assert.notNull(actor);
	//
	//		boolean result;
	//
	//		result = false;
	//
	//		if (actor instanceof Ranger) {
	//			final Ranger ranger = (Ranger) actor;
	//			if (ranger.isBanned()) {
	//				ranger.setBanned(false);
	//				this.rangerService.save(ranger);
	//				result = true;
	//			}
	//		} else if (actor instanceof Manager) {
	//			final Manager manager = (Manager) actor;
	//			if (manager.isBanned()) {
	//				manager.setBanned(false);
	//				this.managerService.save(manager);
	//				result = true;
	//			}
	//
	//		}
	//		return result;
	//	}

	public boolean isLogged() {
		boolean result = false;
		Object principal;

		principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserAccount)
			result = true;
		return result;
	}

	public Collection<Actor> findByEmail(final String email) {
		Collection<Actor> result;

		result = this.actorRepository.findByEmail(email);

		return result;
	}

	public Actor saveEdit(final Actor actor) {
		Actor principal;
		Actor result;

		principal = this.findByPrincipal();
		principal.setAddress(actor.getAddress());
		principal.setEmail(actor.getEmail());
		principal.setFolders(actor.getFolders());
		principal.setMessagesReceived(actor.getMessagesReceived());
		principal.setMessagesSent(actor.getMessagesSent());
		principal.setName(actor.getName());
		principal.setPhone(actor.getPhone());
		principal.setSocialIdentities(actor.getSocialIdentities());
		principal.setSurname(actor.getSurname());

		result = this.save(principal);

		return result;
	}

	public Collection<Actor> getAllSuspiciousActors() {
		Collection<Actor> result;

		result = this.actorRepository.suspiciousActors();

		return result;
	}

}
