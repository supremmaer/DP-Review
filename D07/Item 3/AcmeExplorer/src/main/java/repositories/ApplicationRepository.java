
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select t.applications from Trip t where t.manager.id=?1")
	Collection<Application> findByManagerID(int id);

	@Query("select a from Application a where a.explorer.id = ?1 order by a.status")
	Collection<Application> findbyExplorerIDGrouped(int id);

	//13.2

	//por si no funciona la query anterior y tenemos que hacerlo por servicio
	@Query("select a from Application a where a.explorer.id= ?1")
	Collection<Application> findbyExplorerID(int ID);

	@Query("select a from Application a where a.explorer.id= ?1 and a.status='ACCEPTED'")
	Collection<Application> findAcceptedByExplorerID(int ID);

	//14.6.1
	//util si hacemos las cuentas en servicio
	@Query("select t.applications from Trip t where t.id= ?1")
	Collection<Application> findbyTripID(int ID);

	//Media de aplicacions en los viajes
	@Query("select avg(t.applications.size) from Trip t")
	Double avgApplicationsPerTrip();

	//max
	@Query("select max(t.applications.size) from Trip t")
	Integer maxApplicationsPerTrip();
	//min
	@Query("select min(t.applications.size) from Trip t")
	Integer minApplicationsPerTrip();
	//Standard Desviacion
	@Query("select sqrt(sum(t.applications.size*t.applications.size) / count(t.applications.size) - (avg(t.applications.size) * avg(t.applications.size))) from Trip t")
	Double standardDeviationPerTrip();

	//14.6.5, 14.6.6, 14.6.7, 14.6.8
	@Query("select count(a)*100.0/(select count(ap) from Application ap) from Application a where a.status= ?1")
	Double applicationsByStatusRatio(String status);

	@Query("select count(a) from Application a where a.trip.id=?1 and a.explorer.id=?2 and a.status!='REJECTED' and a.status!='CANCELLED' ")
	Integer countApplicationsNotRejected(int tripId, int explorerId);

	@Query("select count(a) from Application a where a.trip.id=?1 and a.explorer.id=?2 and a.status='ACCEPTED'")
	Integer countApplicationsAccepted(int tripId, int explorerId);

}
