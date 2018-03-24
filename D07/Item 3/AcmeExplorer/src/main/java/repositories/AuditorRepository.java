
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Integer> {

	//32.1
	@Query("select a from Auditor a join a.notes n where n.id=?1")
	Auditor findbyNoteID(int ID);

	//33.2
	@Query("select a from Auditor a join a.auditRecords ar where ar.id=?1")
	Auditor findbyAuditRecordID(int ID);

	//query pedida por migue
	@Query("select a from Auditor a where a.userAccount.id = ?1")
	Auditor findbyUserAccountID(int ID);

}
