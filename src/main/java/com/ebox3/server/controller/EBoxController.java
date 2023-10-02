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
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.service.EBoxService;
import com.ebox3.server.service.impl.HelpFunctions;

@RestController
@RequestMapping("/api/v1/ebox")
public class EBoxController extends HelpFunctions {

	@Autowired
	private EBoxService eBoxService;

	@GetMapping("/find")
	public ResponseEntity<Map<String, Object>> getAllEBox(@RequestParam(required = false) String search,
			@RequestParam("page") int page, @RequestParam("size") int size) {
		return ResponseEntity.ok(eBoxService.getAllEBox(search, page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EboxDTO> getEBoxById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(eBoxService.getEBoxById(id));
	}

	@GetMapping("/electric/getall")
	public @ResponseBody Iterable<EboxDTO> getAllElectricEBox() {
		return eBoxService.getAllElectricEBox();
	}

	@GetMapping("/list/getall")
	public @ResponseBody Iterable<EboxDTO> getAllEBoxList() {
		return eBoxService.getAllEBoxList();
	}

	@PostMapping
	public ResponseEntity<EboxDTO> create(@Validated @RequestBody EboxDTO eboxDTO) {
		return ResponseEntity.ok(eBoxService.create(eboxDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EboxDTO> update(@PathVariable("id") Long id, @Validated @RequestBody EboxDTO eboxDTO) {
		return ResponseEntity.ok(eBoxService.update(id, eboxDTO));
	}

	@PutMapping("/contract/update/{id}")
	public ResponseEntity<EboxDTO> updateEboxByContractId(@PathVariable("id") Long id,
			@Validated @RequestBody EboxDTO eboxDTO) throws ResourceNotFoundException {
		return ResponseEntity.ok(eBoxService.updateEboxByContractId(id, eboxDTO));
	}

	@PutMapping("/purchasing/updateAll/{id}")
	public @ResponseBody Iterable<EboxDTO> updateAllPurchasingEboxByContractId(@PathVariable("id") Long id,
			@Validated @RequestBody Ebox[] eboxs) {
		return eBoxService.updateAllPurchasingEboxByContractId(id, eboxs);
	}

	@PutMapping("/contract/change/{id}")
	public ResponseEntity<EboxDTO> updateStatusEbox(@PathVariable("id") Long id,
			@Validated @RequestBody EboxDTO eboxRequest) {
		return ResponseEntity.ok(eBoxService.updateStatusEbox(id, eboxRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(eBoxService.delete(id));
	}

}
