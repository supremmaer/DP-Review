//
// package services;
//
// import java.util.Date;
// import java.util.HashSet;
// import java.util.concurrent.ThreadLocalRandom;
//
// import javax.transaction.Transactional;
//
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
// import utilities.AbstractTest;
// import domain.Application;
// import domain.Note;
// import domain.Sponsorship;
// import domain.SurvivalClass;
// import domain.Trip;
//
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {
// "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
// })
// @Transactional
// public class PageableTripServiceTest extends AbstractTest {
//
// @Autowired
// private TripService tripService;
//
// @Autowired
// private RangerService rangerService;
//
// @Autowired
// private CategoryService categoryService;
//
// @Autowired
// private ManagerService managerService;
//
// @Autowired
// private LegalTextService legalTextService;
//
//
// @Test
// public void testFindPage() {
// this.createDummyTrips(); // Generate dummy trips to test pagination
//
// Pageable pageable = new PageRequest(0, 5); // Create Pageable obejct for page no. 0, 5 trips per page
// Page<Trip> page;
// boolean loop = true;
//
// page = this.tripService.findPage(pageable);
// while (loop) { // Show every page
// System.out.println(page.toString());
// for (final Trip t : page.getContent())
// System.out.println(t.getTitle());
// loop = page.hasNextPage(); // Check if we are in the last page
// if (loop) {
// pageable = page.nextPageable(); // Get the Pageable for the next page
// page = this.tripService.findPage(pageable);
// }
// }
// }
//
// public void createDummyTrips() {
// super.authenticate("customer3");
//
// final Date date = new Date();
// for (int i = 0; i < 18; i++) {
// final Trip trip = this.tripService.create();
// trip.setTitle("Dummy Trip " + i);
// trip.setDescription("Dummy description " + i);
// trip.setPublicationDate(date);
// trip.setTicker(ThreadLocalRandom.current().nextInt(100000, 999999) + "-AAAA");
// trip.setStartDate(new Date(1511973 + 1000 * i));
// trip.setSponsorships(new HashSet<Sponsorship>());
// trip.setNotes(new HashSet<Note>());
// trip.setRanger(this.rangerService.findByPrincipal());
// trip.setCategory(this.categoryService.findAll().iterator().next());
// trip.setEndDate(new Date(1512973 + 1000 * i));
// trip.setSurvivalClasses(new HashSet<SurvivalClass>());
// trip.setManager(this.managerService.findAll().iterator().next());
// trip.setExplorerRequirements("Requirement " + i);
// trip.setApplications(new HashSet<Application>());
// trip.setLegalText(this.legalTextService.findAll().iterator().next());
// trip.setTag("Tag " + i);
// this.tripService.save(trip);
// }
// }
//}
