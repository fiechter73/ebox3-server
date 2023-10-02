package com.ebox3.server.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.exception.ResourceNotFoundException;

import com.ebox3.server.model.dto.AdditionalCostsDTO;
import com.ebox3.server.service.AdditionalCostsService;

@RestController
@RequestMapping("/api/v1/additionalcosts")
public class AdditionalCostsController {

	@Autowired
	private AdditionalCostsService additionalCostsService;

	@GetMapping("/getall/{value}")
	public Iterable<AdditionalCostsDTO> getAllAdditionalCosts(@PathVariable("value") Boolean value) {
		return additionalCostsService.getAllAdditionalCosts(value);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AdditionalCostsDTO> getAdditionalCostsById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(additionalCostsService.getAdditionalCostsById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<AdditionalCostsDTO> create(@PathVariable("id") Long id,
			@Validated @RequestBody AdditionalCostsDTO addCostDTO) throws ResourceNotFoundException {
		return ResponseEntity.ok(additionalCostsService.create(id, addCostDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AdditionalCostsDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody AdditionalCostsDTO additionalCostsDTO) throws ResourceNotFoundException {

		return ResponseEntity.ok(additionalCostsService.update(id, additionalCostsDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(additionalCostsService.delete(id));
	}
}
