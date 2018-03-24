
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	//14.6.11
	/**
	 * Returns a collection of arrays.
	 * Every array in the collection contains, in the first position, a LegalText
	 * and, in the second position, the number of times that has been referenced
	 */
	@Query("select l, l.trips.size from LegalText l group by l")
	Collection<Object[]> findLegalTextsAndNumberOfReferences();

	//Query auxiliar si quiere hacerse el requisito por servicios

	@Query("select l from LegalText l join l.trips t where t.id=?1")
	LegalText findbyTripID(int ID);

	@Query("select l from LegalText l where l.draftMode=0")
	Collection<LegalText> findFinal();

}
