package com.ebox3.server.service;

import java.util.Map;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.dto.EboxDTO;

public interface EBoxService {

	public Map<String, Object> getAllEBox(String search, int page, int size);

	public EboxDTO getEBoxById(Long id);

	public Iterable<EboxDTO> getAllElectricEBox();

	public Iterable<EboxDTO> getAllEBoxList();

	public EboxDTO create(EboxDTO eboxDTO);

	public EboxDTO update(Long id, EboxDTO eboxDTO) throws ResourceNotFoundException;

	public EboxDTO updateEboxByContractId(Long id, EboxDTO eboxDTO) throws ResourceNotFoundException;

	public Iterable<EboxDTO> updateAllPurchasingEboxByContractId(Long id, Ebox[] eboxs);

	public EboxDTO updateStatusEbox(Long id, EboxDTO eboxRequest);

	public Long delete(Long id) throws ResourceNotFoundException;

}
