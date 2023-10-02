package com.ebox3.server.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.ContractHistory;

@Repository
public interface ContractHistoryRepository extends JpaRepository<ContractHistory, Long> {
  Page<ContractHistory> findById(Long id, Pageable pageable);
}
