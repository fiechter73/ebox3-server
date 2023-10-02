package com.ebox3.server.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.ElectricPeriod;

@Repository
public interface ElectricMeterPeriodRepository extends JpaRepository<ElectricPeriod, Long> {
	Page<ElectricPeriod> findById(Long id, Pageable pageable);

	@Query("SELECT e FROM ElectricPeriod e WHERE e.customerId = :customerId")
	List<ElectricPeriod> findByCustomerId(@Param("customerId") Long customerId);

	@Query("SELECT e FROM ElectricPeriod e WHERE e.statusText LIKE :statusText AND e.zaehlerToPeriode BETWEEN :startDate AND :endDate")
	List<ElectricPeriod> findElectricPeriodByStartEndDate(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("statusText") String statusText);

	@Query("SELECT SUM(e.diffKwatt) FROM ElectricPeriod e WHERE e.statusText LIKE :statusText AND e.zaehlerToPeriode BETWEEN :startDate AND :endDate")
	Double findByKWatt(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("statusText") String statusText);

	@Query("SELECT SUM(e.stromPriceBrutto) FROM ElectricPeriod e WHERE  e.statusText LIKE :statusText AND e.zaehlerToPeriode BETWEEN :startDate AND :endDate AND e.diffKwatt IS NOT  NULL ")
	Double findByBruttoPrice(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("statusText") String statusText);

	@Query("SELECT SUM(e.stromPriceNetto) FROM ElectricPeriod e WHERE e.statusText LIKE :statusText AND e.zaehlerToPeriode BETWEEN :startDate AND :endDate AND e.diffKwatt IS NOT  NULL")
	Double findByNettoPrice(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("statusText") String statusText);

	@Query("SELECT SUM(e.mwstPrice) FROM ElectricPeriod e WHERE e.statusText LIKE :statusText AND e.zaehlerToPeriode BETWEEN :startDate AND :endDate AND e.diffKwatt IS NOT  NULL")
	Double findByMwStPrice(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("statusText") String statusText);

	@Query("SELECT SUM(e.preisBearbeitungsgebuehr) FROM ElectricPeriod e WHERE e.statusText LIKE :statusText AND e.zaehlerToPeriode BETWEEN :startDate AND :endDate AND e.diffKwatt IS NOT  NULL")
	Double findByBearbeitungsGebuehrPrice(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("statusText") String statusText);
	

	@Query("SELECT e FROM ElectricPeriod e WHERE e.zahlungEingegangen BETWEEN :startDate AND :endDate")
	List<ElectricPeriod> findPaymentDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
