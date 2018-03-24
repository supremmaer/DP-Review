
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ranger;

@Repository
public interface RangerRepository extends JpaRepository<Ranger, Integer> {

	//14.6.2, 30.1, querries auxiliar para servicios
	@Query("select r from Ranger r join r.trips t where t.id=?1")
	Ranger findbyTripID(int ID);

	//31.1 la siguiente no se si deberia de existir o sobra por si necesitais el ranger a partir de la curricula
	@Query("select r from Ranger r where r.curricula.id=?1")
	Ranger findbyCurriculaID(int ID);

	//35.1 
	@Query("select r from Ranger r where r.suspicious=True")
	Collection<Ranger> suspiciousRangers();

	//35.4.4
	@Query("select count(r)*100.0/(select count(rp) from Ranger rp) from Ranger r where r.curricula is not null")
	Double ratioRangerwithRegisteredCurricula();

	//35.4.5
	@Query("select count(r)*100.0/(select count(rp) from Ranger rp) from Ranger r where r.curricula.endorserRecords.size>0")
	Double ratioRangerwithEndorsedCurriculum();

	//35.4.7 
	@Query("select count(r)*100.0/(select count(rp) from Ranger rp) from Ranger r where r.suspicious= true")
	Double ratioSuspiciousRanger();

	//Query pedida por Migue
	@Query("select r from Ranger r where r.userAccount.id = ?1")
	Ranger findbyUserAccountID(int ID);
}
