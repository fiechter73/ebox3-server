package com.ebox3.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.model.ContractHistory;
import com.ebox3.server.service.ContractHistoryService;

@RestController
@RequestMapping("/api/v1/history")
public class ContractHistoryController {

	@Autowired
	ContractHistoryService contractHistoryService;

	@GetMapping("/{id}")
	public ResponseEntity<ContractHistory> getHistoryById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(contractHistoryService.getHistoryById(id));
	}

}
