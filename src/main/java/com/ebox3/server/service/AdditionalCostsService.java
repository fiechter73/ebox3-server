package com.ebox3.server.service;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.AdditionalCostsDTO;

public interface AdditionalCostsService {

	public Iterable<AdditionalCostsDTO> getAllAdditionalCosts(Boolean value);

	public AdditionalCostsDTO getAdditionalCostsById(Long id);

	public AdditionalCostsDTO create(Long id, AdditionalCostsDTO addCostDTO) throws ResourceNotFoundException;

	public AdditionalCostsDTO update(Long id, AdditionalCostsDTO additionalCostsDTO) throws ResourceNotFoundException;

	public Long delete(Long id) throws ResourceNotFoundException;
}
