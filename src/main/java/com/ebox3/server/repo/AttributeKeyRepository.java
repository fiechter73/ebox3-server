package com.ebox3.server.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.AttributeKey;

  @Repository
  public interface AttributeKeyRepository extends JpaRepository<AttributeKey, Long> {
    Page<AttributeKey> findById(Long id, Pageable pageable);
    
	@Query(value="SELECT av.value AS value , av.description AS viewValue FROM attributekey ak, attributevalue av "
			+ "WHERE ak.id = av.attribute_key_id_fk "
			+ "AND ak.description = ? "
			+ "ORDER BY av.sort_Order asc", nativeQuery = true)
	List<Map<String, String>>valueSelection(@Param("name") String name);
    
    @Query("SELECT a FROM AttributeKey a WHERE a.description LIKE :description")
    AttributeKey findByDescription(@Param("description") String description);
    
    
	@Query("SELECT  av.description FROM AttributeKey ak, AttributeValue av "
			+ "WHERE ak = av.attributeKey  AND ak.description = :id ")
	String findMwstSatz(@Param("id") String id);
	
	
	List<AttributeKey> findByDescriptionContainingIgnoreCase(String description);


    
  }