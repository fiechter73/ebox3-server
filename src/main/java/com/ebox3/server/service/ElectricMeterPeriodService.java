package com.ebox3.server.service;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;
import com.ebox3.server.model.dto.ElectricPeriodDTO;

public interface ElectricMeterPeriodService {

	public Iterable<ElectricMeterPeriodDTO> printElPeriods(Long id);

	public ElectricPeriodDTO getElectricMeterPeriodstById(Long id);

	public ElectricPeriodDTO create(Long id, ElectricPeriodDTO electricPeriodDTO);

	public ElectricPeriodDTO update(Long id, ElectricPeriodDTO electricPeriodDTO);

	public ElectricPeriodDTO updateElectricPeriodMarging(Long id, ElectricPeriodDTO electricPeriodDTO);

	public ElectricPeriodDTO updateElectricPeriodStatus(Long id, ElectricPeriodDTO electricPeriodDTO);

	public Long delete(Long id) throws ResourceNotFoundException;
}
