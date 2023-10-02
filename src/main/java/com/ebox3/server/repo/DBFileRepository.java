package com.ebox3.server.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.DBFile;

@Repository
  public interface DBFileRepository extends JpaRepository<DBFile, Long> {

  }
