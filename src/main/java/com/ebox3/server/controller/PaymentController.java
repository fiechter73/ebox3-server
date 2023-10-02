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
import com.ebox3.server.model.dto.PaymentDTO;
import com.ebox3.server.model.dto.PaymentDatePriceDTO;
import com.ebox3.server.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@PostMapping("/copy/{year}")
	public ResponseEntity<Long> copyRecord(@PathVariable("year") String year,
			@Validated @RequestBody PaymentDTO paymentDTO) {
		return ResponseEntity.ok(paymentService.copyRecord(year, paymentDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentDatePriceDTO> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(paymentService.findById(id));
	}

	@PostMapping("/{id}")
	public ResponseEntity<PaymentDatePriceDTO> create(@PathVariable("id") Long id,
			@Validated @RequestBody PaymentDatePriceDTO paymentDatePriceDTO) {
		return ResponseEntity.ok(paymentService.create(id, paymentDatePriceDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(paymentService.delete(id));

	}

	@GetMapping("/sumyear/{year}")
	public ResponseEntity<PaymentDTO> sumPriceByYear(@PathVariable("year") String year) {
		return ResponseEntity.ok(paymentService.sumPriceByYear(year));

	}

	@GetMapping("/summonth/{year}")
	public ResponseEntity<PaymentDTO> sumPriceByMonth(@PathVariable("year") String year) {
		return ResponseEntity.ok(paymentService.sumPriceByMonth(year));
	}

	@GetMapping("/year/{year}")
	public @ResponseBody Iterable<PaymentDTO> findByYear(@PathVariable("year") String year) {
		return paymentService.findByYear(year);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaymentDatePriceDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody PaymentDatePriceDTO paymentDatePriceDTO) {
		return ResponseEntity.ok(paymentService.update(id, paymentDatePriceDTO));
	}

	@PutMapping("/clear/{id}")
	public ResponseEntity<PaymentDTO> clearPayment(@PathVariable("id") Long id,
			@Validated @RequestBody PaymentDTO paymentDTO) {
		return ResponseEntity.ok(paymentService.clearPayment(id, paymentDTO));
	}

}