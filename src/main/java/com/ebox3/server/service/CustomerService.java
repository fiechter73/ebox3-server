package com.ebox3.server.service;

import java.util.Map;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.CustomerDTO;
import com.ebox3.server.model.dto.OverviewDTO;

public interface CustomerService {

	public Iterable<Map<Long, Object>> getCustomerList();
	
	public Iterable <Map<Long, Object>> getCustomerWithDismissalList();

	public Map<String, Object> getAllCustomers(String search, Boolean isCustomer, int page, int size);

	public Map<String, Object> getAllCustomer(int page, int size);

	public CustomerDTO getCustomerById(Long id);

	public Iterable<OverviewDTO> getOverview(Boolean value);

	public CustomerDTO getCustomerContractDetails(Long id);

	public CustomerDTO create(CustomerDTO customerDTO);

	public CustomerDTO update(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException;

	public Long delete(Long id) throws ResourceNotFoundException;

}
