
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

	//44.2
	@Query("select e.stories from Explorer e where e.id= ?1")
	Collection<Story> findbyExplorerID(int ID);

	@Query("select s from Story s where s.trip.id= ?1")
	Collection<Story> findbyTripID(int ID);

}
