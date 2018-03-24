
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Explorer;

@Repository
public interface ExplorerRepository extends JpaRepository<Explorer, Integer> {

	@Query("select e from Explorer e join e.applications a where a.id= ?1")
	Explorer findbyApplicationID(int ID);
	//44.2 ¿opcional?
	@Query("select e from Explorer e join e.stories s where s.id= ?1")
	Explorer findbyStoryID(int ID);

	//query pedida por migue
	@Query("select e from Explorer e where e.userAccount.id = ?1")
	Explorer findbyUserAccountID(int ID);

}
