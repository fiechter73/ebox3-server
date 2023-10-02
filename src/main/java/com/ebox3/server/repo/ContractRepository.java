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

import com.ebox3.server.model.Contract;
import com.ebox3.server.model.dto.ContractSelectionDTO;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

	@Query(value = "SELECT c.id AS customerId, con.id AS contractId, c.name,\r\n"
			+ "CONCAT(c.name, ' ', c.vorname,' ',con.status_text,': ','Laufzeit: [', \r\n"
			+ "DATE_FORMAT(con.start_date, '%d.%c.%Y'),'-',DATE_FORMAT(con.end_date, '%d.%c.%Y'), ']: ', 'Box(en): ', IFNULL(GROUP_CONCAT(e.boxnumber),'-')) AS viewValue\r\n"
			+ "FROM customer c\r\n" + "INNER JOIN contract con ON c.id = con.customer_id_fk\r\n"
			+ "LEFT JOIN ebox e ON con.id = e.contract_id_fk\r\n" + "WHERE   con.status_text NOT IN ('Absage') \r\n"
			+ "GROUP BY con.id", nativeQuery = true)
	List<Map<Long, ContractSelectionDTO>> contractSelection();

	@Query(value = "SELECT c.id AS customerId, con.id AS contractId, c.name,\r\n"
			+ "CONCAT(c.name, ' ', c.vorname,' ',con.status_text,': ','Laufzeit: [', \r\n"
			+ "DATE_FORMAT(con.start_date, '%d.%c.%Y'),'-',DATE_FORMAT(con.end_date, '%d.%c.%Y'), ']: ', 'Box(en): ', IFNULL(GROUP_CONCAT(e.boxnumber),'-')) AS viewValue\r\n"
			+ "FROM customer c\r\n" + "INNER JOIN contract con ON c.id = con.customer_id_fk\r\n"
			+ "LEFT JOIN ebox e ON con.id = e.contract_id_fk\r\n"
			+ "WHERE   con.status_text NOT IN ('Kündigung','Absage') \r\n" + "GROUP BY con.id", nativeQuery = true)
	List<Map<Long, String>> contractSelectionEbox();

	@Query(value = "SELECT COUNT(*) FROM contract", nativeQuery = true)
	Long countContract();

	@Query("SELECT c FROM Contract c WHERE c.statusText LIKE :statusText")
	List<Contract> findByStatusText(@Param("statusText") String statusText);

	@Query("SELECT c FROM Contract c WHERE c.statusText LIKE :statusText AND c.startDate BETWEEN :startDate AND :endDate")
	List<Contract> findByStatusTexAndStartDate(@Param("statusText") String statusText,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(e.totalPriceBrutto), con.id FROM Contract con, Ebox e "
			+ "WHERE e.contract = con AND con.id = :id " + "GROUP BY con.id")
	Double sumOfEboxGroupByContract(@Param("id") Long id);

	@Query("SELECT COUNT(e) FROM Ebox e JOIN e.contract c WHERE c.id = :id")
	Long countBox(@Param("id") Long id);

	@Query("SELECT e.boxnumber FROM Ebox e JOIN e.contract c WHERE c.id = :id ORDER BY e.boxnumber ASC")
	List<Long> showBoxNumbers(@Param("id") Long id);

	@Query(value = "SELECT * FROM contract con "
			+ "WHERE con.status_text NOT IN ('Kündigung', 'Absage')", nativeQuery = true)
	Page<Contract> findContractNotKündigungAndAbsage(Pageable pageable);

	@Query(value = "SELECT * FROM contract con "
			+ "WHERE con.status_text IN ('Kündigung', 'Absage')", nativeQuery = true)
	Page<Contract> findContractKündigungAbsage(Pageable pageable);

}
