package com.ebox3.server.service;

import java.util.Map;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.ContractDTO;
import com.ebox3.server.model.dto.ContractHistoryDTO;
import com.ebox3.server.model.dto.ContractSelectionDTO;

public interface ContractService {

	public Iterable<Map<Long, ContractSelectionDTO>> getContractList();

	public Iterable<Map<Long, String>> getContractEboxList();

	public Map<String, Object> getAllContracts(String search, Boolean isContract, int page, int size);

	public ContractDTO getContractById(Long id);

	public Iterable<ContractHistoryDTO> getHistory(Long id);

	public ContractDTO create(ContractDTO contractDTO) throws ResourceNotFoundException;

	public ContractDTO update(Long id, ContractDTO contractDTO) throws ResourceNotFoundException;

	public ContractDTO updateContractCheckout(Long id, ContractDTO contractDTO);

	public Long delete(Long id) throws ResourceNotFoundException;

	public ContractDTO updateContractChange(Long id, ContractDTO contractDTO);
}
