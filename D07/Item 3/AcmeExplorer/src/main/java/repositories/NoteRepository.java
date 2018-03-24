
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	//32.1,33.1
	@Query("select n from Note n where n.trip.id= ?1")
	Collection<Note> findbyTripID(int ID);

	@Query("select t.notes from Manager m join m.trips t where m.id = ?1")
	Collection<Note> findByManagerID(int ID);

	@Query("select n from Note n where n.auditor.id= ?1")
	Collection<Note> findbyAuditorID(int ID);

	/**
	 * 35.4.1
	 * Tambien se puede usar la 32.1 para mas ayudas
	 */

	@Query("select avg(t.notes.size) from Trip t")
	Double averageNotesofTrips();
	//max
	@Query("select max(t.notes.size) from Trip t")
	Integer maxNotesofTrips();
	//min
	@Query("select min(t.notes.size) from Trip t")
	Integer minNotesofTrips();
	//Standar Deviation
	@Query("select sqrt(sum(t.notes.size*t.notes.size) / count(t.notes.size) - (avg(t.notes.size) * avg(t.notes.size))) from Trip t")
	Double standardDeviationNotesofTrips();

}
