
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	//query pedida por migue
	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findbyUserAccountID(int ID);

}
