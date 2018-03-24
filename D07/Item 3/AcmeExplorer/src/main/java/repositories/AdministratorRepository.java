
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//query pedida por migue
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findbyUserAccountID(int ID);

}
