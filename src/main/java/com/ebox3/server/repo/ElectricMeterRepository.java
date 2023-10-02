package com.ebox3.server.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ebox3.server.model.ElectricMeter;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;

@Repository
public interface ElectricMeterRepository extends JpaRepository<ElectricMeter, Long> {
  Page<ElectricMeter> findById(Long id, Pageable pageable);
  
	@Query(value="SELECT * FROM electric_meter e, electric_period ep\r\n"
			+ "WHERE e.id = ep.electric_meter_id_fk\r\n"
			+ "AND ep.status_text  IN ( 'offen', 'verrechnet', 'bezahlt')\r\n"
			+ "AND e.electric_meter_number = 1\r\n"
			+ "ORDER BY ep.zaehler_stand ASC", nativeQuery = true)
	List<Map<Long, ElectricMeterPeriodDTO>>findAllElectricMeterDetail1();
  
	
	@Query("SELECT e AS electricMeter FROM ElectricMeter e JOIN e.electricPeriods p ORDER BY e.electricMeterNumber ASC, p.zaehlerStand ASC")
	List <ElectricMeter> findAllElectricMeterDetail();
	
	@Query("SELECT e AS electricMeter FROM ElectricMeter e JOIN e.electricPeriods p WHERE p.statusText IN ('offen', 'verrechnet') ORDER BY e.electricMeterNumber ASC, p.zaehlerStand ASC")
	List <ElectricMeter> findOpenElectricMeterDetail();
	
	List<ElectricMeter> findByElectricMeterNumber(Long electricMeterNumber);

	List<ElectricMeter> findByElectricMeterName(String electricMeterName);

	List<ElectricMeter> findByElectricMeterBemerkungen(String electricMeterBemerkungen);

	List<ElectricMeter> findByElectricMeterNameContainingIgnoreCaseOrElectricMeterBemerkungenContainingIgnoreCase(String electricMeterName, String electricMeterBemerkungen);
	   
}
