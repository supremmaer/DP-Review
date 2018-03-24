
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AuditRecord;

@Repository
public interface AuditRecordRepository extends JpaRepository<AuditRecord, Integer> {

	//30.2 querry auxiliar
	@Query("select a from AuditRecord a where a.trip.id= ?1")
	AuditRecord findbyTripID(int ID);

	//33.2
	@Query("select a from AuditRecord a where a.auditor.id= ?1")
	Collection<AuditRecord> findbyAuditorID(int ID);

	/**
	 * 35.4.2 //DONE
	 * Tambien se puede usar la 30.1 para mas ayudas
	 */
	//Media de los viajes con auditRecord
	@Query("select count(t.auditRecord)*1.0/(select count(to) from Trip to) from Trip t")
	Double averageAuditRecordsperTrip();
}
