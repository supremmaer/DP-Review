
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService	categoryService;


	@Test
	public void testFindAll() {
		Collection<Category> categories;

		categories = this.categoryService.findAll();
		Assert.isTrue(!categories.isEmpty());
	}

	@Test
	public void testSave() {
		Category category, saved, father;
		Collection<Category> categories, childs;

		super.authenticate("admin1");
		childs = Collections.emptySet();
		categories = this.categoryService.findAll();
		category = this.categoryService.create();
		category.setName("Cat TEST");
		category.setCategories(childs);
		father = (Category) categories.toArray()[0];
		category.setFather(father);
		saved = this.categoryService.save(category);
		categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));
		super.authenticate(null);
	}

	//DONE: delete

	@Test
	public void testDelete() {
		Category category, saved, father;
		Collection<Category> categories, childs;

		super.authenticate("admin1");
		childs = Collections.emptySet();
		categories = this.categoryService.findAll();
		category = this.categoryService.create();
		category.setName("Cat TEST");
		category.setCategories(childs);
		father = (Category) categories.toArray()[0];
		category.setFather(father);
		saved = this.categoryService.save(category);
		categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));

		this.categoryService.delete(saved);
		categories = this.categoryService.findAll();
		Assert.isTrue(!categories.contains(saved));
		super.authenticate(null);
	}
}
