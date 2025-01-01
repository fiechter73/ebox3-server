package com.ebox3.server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.CustomerDTO;
import com.ebox3.server.service.CustomerService1;
import com.ebox3.server.service.impl.HelpFunctions;

@RestController
@RequestMapping("/api/v1/customer3")
public class CustomerController1 extends HelpFunctions {

	@Autowired
	private CustomerService1 customerService1;

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getCustomersByList(@RequestParam(required = false) String search,
			@RequestParam("isNotCustomer") Boolean isNotCustomer, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		return ResponseEntity.ok(customerService1.getCustomersByList(search, isNotCustomer, page, size));
	}

	@PutMapping("/tanent/update/{id}")
	public ResponseEntity<CustomerDTO> updatePartial(@PathVariable("id") Long id,
			@Validated @RequestBody CustomerDTO customerDTO) throws ResourceNotFoundException {
	return ResponseEntity.ok(customerService1.updatePartial(id, customerDTO));
	}

}