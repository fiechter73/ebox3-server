package com.ebox3.server.service;

import java.util.Map;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.CustomerDTO;

public interface CustomerService1 {

	public Map<String, Object> getCustomersByList(String search, Boolean isNotCustomer, int page, int size);

	public CustomerDTO updatePartial(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException;

}
