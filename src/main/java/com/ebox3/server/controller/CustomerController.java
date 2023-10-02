package com.ebox3.server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.CustomerDTO;
import com.ebox3.server.model.dto.OverviewDTO;
import com.ebox3.server.service.CustomerService;
import com.ebox3.server.service.impl.HelpFunctions;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController extends HelpFunctions {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public @ResponseBody Iterable<Map<Long, Object>> getCustomerList() {
		return customerService.getCustomerList();
	}

	@GetMapping("/find")
	public ResponseEntity<Map<String, Object>> getAllCustomers(@RequestParam(required = false) String search,
			Boolean isCustomer, @RequestParam("page") int page, @RequestParam("size") int size) {
		return ResponseEntity.ok(customerService.getAllCustomers(search, isCustomer, page, size));
	}

	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllCustomer(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		return ResponseEntity.ok(customerService.getAllCustomer(page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(customerService.getCustomerById(id));
	}

	@GetMapping("/overviewlist/getall/{value}")
	public @ResponseBody Iterable<OverviewDTO> getOverview(@PathVariable("value") Boolean value) {
		return customerService.getOverview(value);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<CustomerDTO> getCustomerContractDetails(@PathVariable("id") Long id) {
		return ResponseEntity.ok(customerService.getCustomerContractDetails(id));
	}

	@PostMapping
	public ResponseEntity<CustomerDTO> create(@Validated @RequestBody CustomerDTO customerDTO) {
		return ResponseEntity.ok(customerService.create(customerDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody CustomerDTO customerDTO) throws ResourceNotFoundException {
		return ResponseEntity.ok(customerService.update(id, customerDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {

		return ResponseEntity.ok(customerService.delete(id));
	}

}
