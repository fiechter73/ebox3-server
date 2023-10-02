package com.ebox3.server.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.ebox3.server.model.Ebox;

@Repository
public interface EBoxRepository extends JpaRepository<Ebox, Long> {
	Page<Ebox> findById(Long id, Pageable pageable);

	@Query("SELECT e FROM Ebox e WHERE e.statusText LIKE :statusText AND e.startDate BETWEEN :startDate AND :endDate")
	List<Ebox> findByStatusTexAndStartDate(@Param("statusText") String statusText, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	List<Ebox> findByStatus(boolean status);

	List<Ebox> findByStatusText(String statusText);

	List<Ebox> findByBoxnumber(Long boxnumber);

	List<Ebox> findByBoxtypeContainingIgnoreCaseOrStatusTextContainingIgnoreCase(String boxType, String statusText);

}
