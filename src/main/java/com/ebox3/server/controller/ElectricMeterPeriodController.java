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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;
import com.ebox3.server.model.dto.ElectricPeriodDTO;
import com.ebox3.server.service.ElectricMeterPeriodService;

@RestController
@RequestMapping("/api/v1/electricperiod")
public class ElectricMeterPeriodController {

	@Autowired
	private ElectricMeterPeriodService electricMeterPeriodService;

	@GetMapping("/print/{id}")
	public @ResponseBody Iterable<ElectricMeterPeriodDTO> printElPeriods(@PathVariable("id") Long id) {
		return electricMeterPeriodService.printElPeriods(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ElectricPeriodDTO> getElectricMeterPeriodstById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(electricMeterPeriodService.getElectricMeterPeriodstById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<ElectricPeriodDTO> create(@PathVariable("id") Long id,
			@Validated @RequestBody ElectricPeriodDTO electricPeriodDTO) {
		return ResponseEntity.ok(electricMeterPeriodService.create(id, electricPeriodDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ElectricPeriodDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody ElectricPeriodDTO electricPeriodDTO) {

		return ResponseEntity.ok(electricMeterPeriodService.update(id, electricPeriodDTO));
	}

	@PutMapping("/marging/{id}")
	public ResponseEntity<ElectricPeriodDTO> updateElectricPeriodMarging(@PathVariable("id") Long id,
			@Validated @RequestBody ElectricPeriodDTO electricPeriodDTO) {
		return ResponseEntity.ok(electricMeterPeriodService.updateElectricPeriodMarging(id, electricPeriodDTO));
	}

	@PutMapping("/status/{id}")
	public ResponseEntity<ElectricPeriodDTO> updateElectricPeriodStatus(@PathVariable("id") Long id,
			@Validated @RequestBody ElectricPeriodDTO electricPeriodDTO) {

		return ResponseEntity.ok(electricMeterPeriodService.updateElectricPeriodStatus(id, electricPeriodDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(electricMeterPeriodService.delete(id));
	}

}
