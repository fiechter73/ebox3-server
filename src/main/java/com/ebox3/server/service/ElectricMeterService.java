package com.ebox3.server.service;

import java.util.Map;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.ElectricMeterDTO;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;

public interface ElectricMeterService {

	public Map<String, Object> getAllElectricMeters(String search, int page, int size);

	public Map<String, Object> getElectricMeterDetails(boolean showAll, Long id, int page, int size);

	public Iterable<ElectricMeterPeriodDTO> getAllElectricMeterDetails(boolean showAll);

	public Iterable<ElectricMeterDTO> getAllElectricMeterList();

	public ElectricMeterDTO getElectricMetertById(Long id);

	public ElectricMeterDTO create(Long id, ElectricMeterDTO electricMeterDTO);

	public ElectricMeterDTO updateElectricMeterByEboxId(Long id, ElectricMeterDTO electricMeterDTO);

	public Long delete(Long id) throws ResourceNotFoundException;

}
