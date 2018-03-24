
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Trip;

@Service
@Transactional
public class CategoryService {

	//Managed Repository
	@Autowired
	private CategoryRepository	categoryRepository;

	//Supporting Services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private TripService			tripService;


	//Constructors
	public CategoryService() {
		super();
	}

	//CRUD methods
	//14.4
	public Category create() {
		Category result;

		result = new Category();

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);

		Category result;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor instanceof Administrator);

		result = this.categoryRepository.save(category);
		this.actorService.isSpam(category.getName(), actor);

		return result;
	}

	public void delete(final Category category) {
		Collection<Trip> trips;
		Collection<Category> children;
		Category parent;

		Assert.notNull(category);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));

		Actor actor;
		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);

		trips = this.tripService.findByCategory(category);
		if (!trips.isEmpty())
			for (final Trip t : trips) {
				t.setCategory(category.getFather());
				this.tripService.saveStage(t);
			}

		parent = category.getFather();
		parent.getCategories().remove(category);
		this.save(parent);

		children = category.getCategories();
		if (!children.isEmpty())
			for (final Category c : children) {
				c.setFather(category.getFather());
				this.save(c);
			}

		this.categoryRepository.delete(category);
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();

		return result;
	}

	public Category findOne(final Integer id) {
		Category result;

		result = this.categoryRepository.findOne(id);

		return result;
	}

	public Collection<Category> findChildren(final Integer parentId) {
		Collection<Category> result;
		Category parent;

		parent = this.categoryRepository.findOne(parentId);
		result = parent.getCategories();

		return result;
	}

	public Category findFirst() {
		Category result = null;
		Collection<Category> categories;

		categories = this.categoryRepository.findAll();
		for (final Category c : categories)
			if (c.getFather() == null)
				result = c;

		return result;
	}
}
