package com.ebox3.server.repo;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  Page<Payment> findById(Long id, Pageable pageable);

  
  @Query("SELECT e FROM Payment e WHERE e.id = " + ":id AND e.jahr LIKE :jahr%")
  List<Payment> findByIdAndYear(@Param("id") Long id, @Param("jahr") String jahr);


  @Query("SELECT e FROM Payment e WHERE e.jahr LIKE :jahr%")
  List<Payment> findByYear(@Param("jahr") String jahr);
  

}
