package com.ebox3.server.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	List<Customer> findByAge(int age);

	@Query(value = "SELECT c.id AS value,	CONCAT(c.name, ' ', "
			+ "c.vorname, ' ', c.strasse, ' ', c.plz, ' ', c.ort) AS viewValue" + "	FROM customer c"
			+ "	WHERE c.status_text NOT IN ('Intressent', 'Kündigung')" + "ORDER BY c.name, c.vorname", nativeQuery = true)
	List<Map<Long, Object>> customerSelection();
	
	@Query(value = "SELECT c.id AS value,	CONCAT(c.name, ' ', "
			+ "c.vorname, ' ', c.strasse, ' ', c.plz, ' ', c.ort) AS viewValue" + "	FROM customer c"
			+ "	WHERE c.status_text NOT IN ('Intressent')" + "ORDER BY c.name, c.vorname", nativeQuery = true)
	List<Map<Long, Object>> customerSelectionWithDismissal();

	@Query("SELECT c FROM Customer c WHERE c.statusText LIKE :statusText")
	List<Customer> findByStatusText(@Param("statusText") String statusText);
	
	@Query("SELECT c FROM Customer c "
		 + "WHERE c.statusText "
		 + "LIKE :statusText AND c.createdAt BETWEEN :startDate AND :endDate")
	List<Customer> findByStatusTexAndStartDate(@Param("statusText") String statusText,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT COUNT(e) FROM Customer c, Contract con, Ebox e "
			+ "WHERE con.customer = c AND e.contract = con AND c.id = :id")
	Long countEbox(@Param("id") Long id);

	@Query("SELECT COUNT(con) FROM Customer c, Contract con "
			+ "WHERE con.customer = c AND con.statusText = 'Vertrag'  AND c.id = :id")
	Long countContract(@Param("id") Long id);

	@Query("SELECT e.boxnumber FROM Customer c, Contract con, Ebox e "
			+ "WHERE con.customer = c AND e.contract = con AND c.id = :id")
	List<Long> boxNumbers(@Param("id") Long id);

	@Query("SELECT con.id FROM Customer c, Contract con " + "WHERE con.customer = c AND c.id = :id")
	List<Long> contractIds(@Param("id") Long id);

	@Query("SELECT SUM(e.totalPriceBrutto), c.id FROM Customer c, Contract con, Ebox e "
			+ "WHERE con.customer = c AND e.contract = con AND c.id = :id " + "GROUP BY c.id")
	Double sumOfEboxGroupByCustomer(@Param("id") Long id);

	@Query(value = "SELECT * FROM customer c " + "WHERE c.status_text IN ('Kündigung')", nativeQuery = true)
	List<Customer> findRetiredCustomers();
	
	@Query(value = "SELECT * FROM customer c "
			+ "WHERE c.status_text NOT IN ('Kündigung', 'Intressent')", nativeQuery = true)
	List<Customer> findCustomers();
	
	@Query(value = "SELECT * FROM customer c "
			+ "WHERE c.status_text NOT IN ('Kündigung', 'Intressent')", nativeQuery = true)
	Page<Customer> findCustomersNotKündigungAndNotIntressent(Pageable pageable);
	
	@Query(value = "SELECT * FROM customer c "
			+ "WHERE c.status_text IN ('Kündigung', 'Intressent')", nativeQuery = true)
	Page<Customer> findCustomerstKündigungAndIntressent(Pageable pageable);
	
	@Query(value = "SELECT * FROM customer c " + "WHERE c.status_text IN ('Kündigung')", nativeQuery = true)
	Page<Customer> findRetiredCustomersPageable(Pageable pageable);
	
	@Query(value = "SELECT * FROM customer c "
			+ "WHERE c.status_text NOT IN ('Kündigung', 'Intressent')", nativeQuery = true)
	Page<Customer> findCustomersPageable(Pageable pageable);
	
	
	@Query(value = "SELECT * FROM customer c "
			+ "WHERE  MATCH(c.name, c.vorname, c.plz, c.strasse, c.ort, c.status_text, c.tel1) "
					+ "AGAINST(?1 IN BOOLEAN MODE)", nativeQuery = true)
	List<Customer> findCustomerFullSearch(String search);
	
//  Dient als Demozweck, damit auch in spätern Jahren noch nachvollzogen werden kann wie man es auch machen könnnte.
//  Ich habe mich entschieden dies über dein Full Text Suche zu machen, dann bruacht mann die untenstehende Möglichkeit hier nicht mehr.
	
//	Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
//	Page<Customer> findByVornameContainingIgnoreCase(String vorname,  Pageable pageable);
//	Page<Customer> findByStrasseContainingIgnoreCase(String strasse, Pageable pageable);
//	Page<Customer> findByOrtContainingIgnoreCase(String ort, Pageable pageable);
//	Page<Customer> findByPlzContainingIgnoreCase(String plz, Pageable pageable);
//	Page<Customer> findByStatusTextContainingIgnoreCase(String statusText, Pageable pagable);
//	
//	List<Customer> findByNameContainingIgnoreCaseOrVornameContainingIgnoreCaseOrStrasseContainingIgnoreCaseOrOrtContainingIgnoreCaseOrPlzContainingIgnoreCaseOrStatusTextContainingIgnoreCase(
//			String name, 
//			String vorname,
//			String strasse, 
//			String ort, 
//			String plz,
//			String statusText);
//		
//	List<Customer> findByNameContainingIgnoreCaseOrVornameContainingIgnoreCaseOrOrtContainingIgnoreCaseOrStatusTextContainingIgnoreCase(
//			String name, 
//			String vorname,
//			String ort,
//			String statusText);
	
}
