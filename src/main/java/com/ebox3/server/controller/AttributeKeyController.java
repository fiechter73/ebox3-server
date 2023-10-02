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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.model.AttributeKey;
import com.ebox3.server.model.dto.AttributeKeyDTO;
import com.ebox3.server.service.AttributeKeyService;

@RestController
@RequestMapping("/api/v1/attributekey")
public class AttributeKeyController {

	@Autowired
	private AttributeKeyService attributeKeyService;

	@GetMapping("/find")
	public ResponseEntity<Map<String, Object>> getAllAttributeKeys(@RequestParam(required = false) String search,
			@RequestParam("page") int page, @RequestParam("size") int size) {
		return ResponseEntity.ok(attributeKeyService.getAllAttributeKeys(search, page, size));
	}

	@GetMapping("/id")
	public ResponseEntity<Map<String, Object>> getById(@RequestParam("id") Long id, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		return ResponseEntity.ok(attributeKeyService.getById(id, page, size));
	}

	@GetMapping("/getall")
	public @ResponseBody Iterable<AttributeKeyDTO> getAttributeKeys() {
		return attributeKeyService.getAttributeKeys();
	}

	@GetMapping("/name")
	public @ResponseBody Iterable<Map<String, String>> getAttributeByName(@RequestParam("name") String name) {
		return attributeKeyService.getAttributeByName(name);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AttributeKeyDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody AttributeKey attributeKeyRequest) {
		return ResponseEntity.ok(attributeKeyService.update(id, attributeKeyRequest));

	}

}
