
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Explorer;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	//Managed Repository
	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private TripService			tripService;


	//Constructor
	public FinderService() {
		super();
	}

	//CRUD
	public Finder create() {
		Finder result;

		result = new Finder();

		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		return result;

	}

	public Finder save(final Finder finder) {
		Finder result;
		Explorer explorer;

		Assert.notNull(finder);
		explorer = this.explorerService.findByPrincipal();
		this.checkSpamWords(finder);
		finder.setTrips(this.tripService.findByFinder(finder));
		result = this.finderRepository.save(finder);
		explorer.setFinder(result);
		this.explorerService.save(explorer);

		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	public Finder findByPrincipal() {
		Finder result;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		result = this.findByExplorer(explorer);

		return result;
	}

	public Finder findByExplorer(final Explorer explorer) {
		Assert.notNull(explorer);

		Finder result;

		result = this.finderRepository.findByExplorerID(explorer.getId());

		return result;
	}

	public void checkSpamWords(final Finder finder) {
		Assert.notNull(finder);
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		this.actorService.isSpam(finder.getKeyWord(), explorer);
	}

	public Finder findOne(final Integer finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);

		return result;
	}
}
