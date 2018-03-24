
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t")
	Page<Trip> findPage(Pageable pageable);

	//10.3 USADO
	@Query("select t from Trip t where t.ticker like %?1% or t.title like %?1% or t.description like %?1% ")
	// DONE: Esto no funciona, no devuelve nada
	Collection<Trip> findByKey(String key);

	//10.4 USADO
	@Query("select t from Trip t where t.category.id = ?1")
	Collection<Trip> findByCategoryID(int ID);

	//14.6.1 query auxiliar para servicios
	@Query("select t from Trip t join t.applications a where a.id=?1")
	Trip findByApplicationID(int ID);

	//14.6.2
	//util si hacemos las cuentas en servicio
	@Query("select m.trips from Manager m where m.id= ?1")
	Collection<Trip> findByManagerID(int ID);

	@Query("select avg(m.trips.size) from Manager m")
	Double averageTripsinManager();

	//max
	@Query("select max(m.trips.size) from Manager m")
	Integer maxTripsofManager();
	//min
	@Query("select min(m.trips.size) from Manager m")
	Integer minTripsofManager();
	//Standar Deviation
	@Query("select sqrt(sum(m.trips.size*m.trips.size) / count(m.trips.size) - (avg(m.trips.size) * avg(m.trips.size))) from Manager m")
	Double standardDeviationTripsperManager();

	//14.6.3 Metodos auxiliares para solucionar en servicios

	@Query("select sum(s.price)*1.0 from Trip t join t.stages s group by t")
	Collection<Double> pricesofTrips();

	@Query("select sum(s.price)*1.0 from Trip t join t.stages s where t.id=?1")
	Double pricebyTripID(int ID);

	//14.6.4
	//util si hacemos las cuentas en servicio
	@Query("select r.trips from Ranger r where r.id= ?1")
	Collection<Trip> findbyRangerID(int ID);

	@Query("select avg(r.trips.size) from Ranger r")
	Double averageTripsinRanger();

	//max
	@Query("select max(r.trips.size) from Ranger r")
	Integer maxTripsofRanger();
	//min
	@Query("select min(r.trips.size) from Ranger r")
	Integer minTripsofRanger();
	//Standar Desviacion
	@Query("select sqrt(sum(r.trips.size*r.trips.size) / count(r.trips.size) - (avg(r.trips.size) * avg(r.trips.size))) from Ranger r")
	Double standardDeviationTripsperRanger();

	//14.6.9
	@Query("select count(t)*100.0/(select count(n) from Trip n) from Trip t where t.cancellationReason is not null")
	Double ratioTripsCancelled();

	//14.6.10
	@Query("select t.title from Trip t where t.applications.size>= (select avg(tp.applications.size)*1.1 from Trip tp) order by t.applications.size")
	Collection<String> findtripsAboveTenPercentApplicationsThanAveragetitle();

	@Query("select t from Trip t where t.applications.size>= (select avg(tp.applications.size)*1.1 from Trip tp) order by t.applications.size")
	Collection<Trip> findTripsWithMoreApplicationsThanAverage();

	//14.6.11
	//query auxiliar para hacerlo por servicio, la original en LegalTextRepository

	@Query("select t from Trip t where t.legalText.id= ?1")
	Collection<Trip> findbyLegalTextID(int ID);

	//30.2 
	@Query("select t from Trip t where t.auditRecord.id= ?1")
	Trip findbyAuditRecordID(int ID);

	//32.1, 33.1
	@Query("select t from Trip t join t.notes n where n.id=?1")
	Trip findbyNoteID(int ID);

	//35.4.3
	@Query("select (select count(t) from Trip t where t.auditRecord is not null)*100.0/ count(tb) from Trip tb")
	Double ratioTripWithOneAuditRecord();

	//43.1
	@Query("select t from Trip t join t.survivalClasses s where s.id=?1")
	Trip findbySurvivalClassID(int ID);

	//44.2 ¿opcional?
	@Query("select s.trip from Story s where s.trip.id= ?1")
	Trip findbyStoryID(int ID);

}
