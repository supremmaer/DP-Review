
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Stage;

@Service
@Transactional
public class StageService {

	//Managed Repository
	@Autowired
	private StageRepository	stageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService	actorService;


	//Constructor
	public StageService() {
		super();
	}

	//CRUD methods

	public Stage save(final Stage stage) {
		Assert.notNull(stage);
		Stage result;

		this.actorService.isSpam(stage.getDescription(), this.actorService.findByPrincipal());
		this.actorService.isSpam(stage.getTitle(), this.actorService.findByPrincipal());

		result = this.stageRepository.save(stage);

		return result;
	}

	public void delete(final Stage stage) {
		Assert.notNull(stage);
		Assert.isTrue(stage.getId() != 0);
		Assert.isTrue(this.stageRepository.exists(stage.getId()));

		this.stageRepository.delete(stage);
	}

	public Collection<Stage> findAll() {
		Collection<Stage> result;

		result = this.stageRepository.findAll();

		return result;
	}

	public Stage create() {
		Stage result;

		result = new Stage();

		return result;
	}

	public Stage findOne(final int stageId) {
		Stage result;

		result = this.stageRepository.findOne(stageId);

		return result;
	}
}
