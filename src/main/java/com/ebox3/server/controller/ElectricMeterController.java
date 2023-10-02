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
import com.ebox3.server.model.dto.ElectricMeterDTO;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;
import com.ebox3.server.service.ElectricMeterService;

@RestController
@RequestMapping("/api/v1/electricmeter")
public class ElectricMeterController {

	@Autowired
	private ElectricMeterService electricMeterService;

	@GetMapping("/find")
	public ResponseEntity<Map<String, Object>> getAllElectricMeters(@RequestParam(required = false) String search,
			@RequestParam("page") int page, @RequestParam("size") int size) {
		return ResponseEntity.ok(electricMeterService.getAllElectricMeters(search, page, size));
	}

	@GetMapping("/findAll")
	public ResponseEntity<Map<String, Object>> getElectricMeterDetails(@RequestParam("showAll") boolean showAll,
			@RequestParam(required = false) Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
		return ResponseEntity.ok(electricMeterService.getElectricMeterDetails(showAll, id, page, size));
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<ElectricMeterPeriodDTO> getAllElectricMeterDetails(
			@RequestParam("showAll") boolean showAll) {
		return electricMeterService.getAllElectricMeterDetails(showAll);
	}

	@GetMapping("/electricmeterlist")
	public @ResponseBody Iterable<ElectricMeterDTO> getAllElectricMeterList() {

		return electricMeterService.getAllElectricMeterList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ElectricMeterDTO> getElectricMetertById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(electricMeterService.getElectricMetertById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<ElectricMeterDTO> create(@PathVariable("id") Long id,
			@Validated @RequestBody ElectricMeterDTO electricMeterDTO) {
		return ResponseEntity.ok(electricMeterService.create(id, electricMeterDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ElectricMeterDTO> updateElectricMeterByEboxId(@PathVariable("id") Long id,
			@Validated @RequestBody ElectricMeterDTO electricMeterDTO) {
		return ResponseEntity.ok(electricMeterService.updateElectricMeterByEboxId(id, electricMeterDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(electricMeterService.delete(id));
	}
}
