
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccount(int id);

	@Query("select a from Actor a where a.id = ?1")
	Actor findOne(int id);

	@Query("select a from Actor a where a.email = ?1")
	Collection<Actor> findByEmail(String email);

	@Query("select a from Actor a where a.suspicious=True")
	Collection<Actor> suspiciousActors();
}
