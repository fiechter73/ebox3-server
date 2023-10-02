package com.ebox3.server.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.AdditionalCosts;

  @Repository
  public interface AdditionalCostsRepository extends JpaRepository<AdditionalCosts, Long> {
    Page<AdditionalCosts> findById(Long id, Pageable pageable);
    
    
    @Query("SELECT e FROM AdditionalCosts e WHERE e.statusText LIKE :statusText")
    List<AdditionalCosts> findAdditionalCost(@Param("statusText") String statusText);
    
    
    
    @Query("SELECT e FROM AdditionalCosts e WHERE e.statusText LIKE :statusText AND e.billingDate BETWEEN :startDate AND :endDate")
    List<AdditionalCosts> findAdditionalCostsByStartEndDate(@Param("startDate") Date startDate, 
                                                            @Param("endDate")   Date endDate,
                                                            @Param("statusText") String statusText);
    
    @Query("SELECT e FROM AdditionalCosts e WHERE e.billingDate BETWEEN :startDate AND :endDate")
    List<AdditionalCosts> findPaymentDate(@Param("startDate") Date startDate,
                                         @Param("endDate")   Date endDate);
    
    
    @Query("SELECT e FROM AdditionalCosts e WHERE e.statusText NOT IN (:status)")
    List<AdditionalCosts>findAllWithoutBezhalt(@Param("status")String status);
    
    
  }