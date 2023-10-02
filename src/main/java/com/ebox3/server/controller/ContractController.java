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
import com.ebox3.server.model.dto.ContractDTO;
import com.ebox3.server.model.dto.ContractHistoryDTO;
import com.ebox3.server.model.dto.ContractSelectionDTO;
import com.ebox3.server.service.ContractService;

@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

	@Autowired
	private ContractService contractService;

	@GetMapping("/list")
	public @ResponseBody Iterable<Map<Long, ContractSelectionDTO>> getContractList() {
		return contractService.getContractList();
	}

	@GetMapping("/ebox/list")
	public @ResponseBody Iterable<Map<Long, String>> getContractEboxList() {
		return contractService.getContractEboxList();
	}

	@GetMapping("/find")
	public ResponseEntity<Map<String, Object>> getAllContracts(@RequestParam(required = false) String search,
			@RequestParam(required = false) Boolean isContract, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		return ResponseEntity.ok(contractService.getAllContracts(search, isContract, page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContractDTO> getContractById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(contractService.getContractById(id));
	}

	@GetMapping("/history/{id}")
	public @ResponseBody Iterable<ContractHistoryDTO> getHistory(@PathVariable("id") Long id) {
		return contractService.getHistory(id);
	}

	@PostMapping
	public ResponseEntity<ContractDTO> create(@Validated @RequestBody ContractDTO contractDTO)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(contractService.create(contractDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContractDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody ContractDTO contractDTO) throws ResourceNotFoundException {
		return ResponseEntity.ok(contractService.update(id, contractDTO));
	}

	@PutMapping("/checkout/{id}")
	public ResponseEntity<ContractDTO> updateContractCheckout(@PathVariable("id") Long id,
			@Validated @RequestBody ContractDTO contractDTO) {
		return ResponseEntity.ok(contractService.updateContractCheckout(id, contractDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(contractService.delete(id));
	}

	@PutMapping("/change/{id}")
	public ResponseEntity<ContractDTO> updateContractChange(@PathVariable("id") Long id,
			@Validated @RequestBody ContractDTO contractDTO) {
		return ResponseEntity.ok(contractService.updateContractChange(id, contractDTO));
	}

}
