
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Stage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StageServiceTest extends AbstractTest {

	@Autowired
	private StageService	stageService;


	@Test
	public void testFindAll() {
		Collection<Stage> stages;

		stages = this.stageService.findAll();
		Assert.isTrue(!stages.isEmpty());
	}

	@Test
	public void testSave() {
		Stage stage, saved;
		Collection<Stage> stages;

		stage = new Stage();
		stage.setDescription("Test Description");
		stage.setPrice(10.);
		stage.setTitle("Test Title");
		saved = this.stageService.save(stage);
		stages = this.stageService.findAll();
		Assert.isTrue(stages.contains(saved));
	}

	@Test
	public void testDelete() {
		Stage stage, saved;
		Collection<Stage> updated;

		stage = new Stage();
		stage.setDescription("Test Description");
		stage.setPrice(10.);
		stage.setTitle("Test Title");
		saved = this.stageService.save(stage);

		this.stageService.delete(saved);
		updated = this.stageService.findAll();
		Assert.isTrue(!updated.contains(saved));
	}
}
