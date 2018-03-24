
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	//30.1 y 31.1 querys auxiliares para servicios
	@Query("select r.curricula from Ranger r where r.id=?1")
	Curricula findbyRangerID(int ID);

	@Query("select c from Curricula c join c.educationRecords r where r.id=?1")
	Curricula findByEducationRecordID(int ID);

	@Query("select c from Curricula c join c.miscellaneousRecords r where r.id=?1")
	Curricula findByMiscellaneousRecordID(int ID);

	@Query("select c from Curricula c join c.endorserRecords r where r.id=?1")
	Curricula findByEndorserRecordID(int ID);

	@Query("select c from Curricula c join c.professionalRecords r where r.id=?1")
	Curricula findByProfessionalRecordID(int ID);

	@Query("select c from Curricula c where c.personalRecord.id=?1")
	Curricula findByPersonalRecordID(int ID);

}
