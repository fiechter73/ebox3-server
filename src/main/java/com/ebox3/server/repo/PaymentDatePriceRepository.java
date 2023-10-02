package com.ebox3.server.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.PaymentDatePrice;

@Repository
public interface PaymentDatePriceRepository extends JpaRepository<PaymentDatePrice, Long> {
	Page<PaymentDatePrice> findById(Long id, Pageable pageable);

	@Query("SELECT e FROM PaymentDatePrice e, Payment p  WHERE e.payment = p  AND p.jahr LIKE :jahr%")
	List<PaymentDatePrice> findByYear(@Param("jahr") String jahr);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoJanDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findJanByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoFebDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findFebByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoMarDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findMarByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoAprDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findAprByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoMaiDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findMaiByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoJunDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findJunByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoJulDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findJulByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoAugDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findAugByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoSepDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findSepByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoOctDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findOctByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoNovDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findNovByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT p FROM PaymentDatePrice p WHERE p.bruttoDecDate BETWEEN :startDate AND :endDate")
	List<PaymentDatePrice> findDecByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
