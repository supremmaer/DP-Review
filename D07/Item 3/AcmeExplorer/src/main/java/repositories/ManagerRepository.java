
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	//14.6.4, 32.1? Query auxiliar para los servicios
	@Query("select m from Manager m join m.trips t where t.id=?1")
	Manager findbyTripID(int ID);

	//35.1 
	@Query("select m from Manager m where m.suspicious=True")
	Collection<Manager> suspiciousManagers();

	//35.4.6
	@Query("select count(m)*100.0/(select count(mp) from Manager mp) from Manager m where m.suspicious= true")
	Double ratioSuspiciousManager();

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findbyUserAccountID(int ID);

}
