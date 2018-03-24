
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SurvivalClass;

@Repository
public interface SurvivalClassRepository extends JpaRepository<SurvivalClass, Integer> {

	//43.1
	@Query("select t.survivalClasses from Trip t where t.id=?1")
	Collection<SurvivalClass> findbyTripID(int ID);

	@Query("select t.survivalClasses from Trip t where t.manager.id = ?1")
	Collection<SurvivalClass> findbyManagerID(int ID);

	@Query("select e.survivalClasses from Explorer e where e.id=?1")
	Collection<SurvivalClass> findbyExplorerID(int ID);

}
