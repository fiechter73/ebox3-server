package com.ebox3.server.service.impl;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.ContractHistory;
import com.ebox3.server.repo.ContractHistoryRepository;
import com.ebox3.server.service.ContractHistoryService;

@Service
public class ContractHistoryServiceImpl implements ContractHistoryService {

	@Autowired
	ContractHistoryRepository historyRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ContractHistory getHistoryById(@PathVariable("id") Long id) {
		ContractHistory history = historyRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Contract History by id %d not found", id)));
		return mapper.map(history, ContractHistory.class);
	}
}
