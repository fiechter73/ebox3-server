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
import com.ebox3.server.model.dto.AttributeValueDTO;
import com.ebox3.server.service.AttributeValueService;

@RestController
@RequestMapping("/api/v1/attributevalue")
public class AttributeValueController {

	@Autowired
	private AttributeValueService attributeValueService;

	@GetMapping("/{id}")
	public ResponseEntity<AttributeValueDTO> getAttributeValue(@PathVariable("id") Long id) {
		return ResponseEntity.ok(attributeValueService.getAttributeValue(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(attributeValueService.delete(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<AttributeValueDTO> create(@PathVariable("id") Long id,
			@Validated @RequestBody AttributeValueDTO attributeValueDTO) {
		return ResponseEntity.ok(attributeValueService.create(id, attributeValueDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AttributeValueDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody AttributeValueDTO attributeValueDTO) {
		return ResponseEntity.ok(attributeValueService.update(id, attributeValueDTO));
	}
}
