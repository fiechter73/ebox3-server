package com.ebox3.server.controller;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.service.doc.DocumentService;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@RequestMapping("/contract")
	public ResponseEntity<HttpStatus> downloadContractWordResource(@RequestParam("id") final Long id,
			final HttpServletResponse response) throws IOException {
		documentService.downloadContractWordResource(id, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/customerletter")
	public ResponseEntity<HttpStatus> downloadCustomerLetterWordResource(@RequestParam("type") final String type,
			@RequestParam("id") final Long id, final HttpServletResponse response) throws IOException {
		documentService.downloadCustomerLetterWordResource(type, id, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/customerlist")
	public ResponseEntity<HttpStatus> downloadCustomerWordResource(@RequestParam("type") final String type,
			@RequestParam("id") final String id, final HttpServletResponse response) throws IOException {
		documentService.downloadCustomerWordResource(type, id, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/electricbill")
	public ResponseEntity<HttpStatus> downloadElectricBillWordResource(@RequestParam("ids") final String ids,
			final HttpServletResponse response) throws IOException {
		documentService.downloadElectricBillWordResource(ids, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/billinglist")
	public ResponseEntity<HttpStatus> downloadCustomerSalesListWordResource(@RequestParam("jahr") final String jahr,
			final HttpServletResponse response) throws IOException {
		documentService.downloadCustomerSalesListWordResource(jahr, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/tenant")
	private ResponseEntity<HttpStatus> downloadCustomerBillWordResource(@RequestParam("jahr") final String jahr,
			HttpServletResponse response) throws IOException {
		documentService.downloadCustomerBillWordResource(jahr, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/electricoverviewbilling")
	public ResponseEntity<HttpStatus> downloaElectricOverwievBillingWordResource(
			@RequestParam("jahr") final String jahr, @RequestParam("status") final String status,
			final HttpServletResponse response) throws IOException {
		documentService.downloaElectricOverwievBillingWordResource(jahr, status, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/billingletter")
	public ResponseEntity<HttpStatus> downloadBillingLetterWordResource(@RequestParam("id") final Long id,
			final HttpServletResponse response) throws IOException {
		documentService.downloadBillingLetterWordResource(id, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping("/mwst")
	public ResponseEntity<HttpStatus> downloadMwstWordResource(@RequestParam("year") final String year,
			@RequestParam("from") final Date from, @RequestParam("to") final Date to,
			final HttpServletResponse response) throws IOException {
		documentService.downloadMwstWordResource(year, from, to, response);
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
