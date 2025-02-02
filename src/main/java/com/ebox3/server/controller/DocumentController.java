package com.ebox3.server.controller;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.service.QrcodeService;
import com.ebox3.server.service.doc.DocumentService;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private QrcodeService qrcodeService;

	@RequestMapping("/contract")
	public void downloadContractWordResource(@RequestParam("id") final Long id, final HttpServletResponse response)
			throws IOException {
		documentService.downloadContractWordResource(id, response);
	}

	@RequestMapping("/customerletter")
	public void downloadCustomerLetterWordResource(@RequestParam("type") final String type,
			@RequestParam("id") final Long id, final HttpServletResponse response) throws IOException {
		documentService.downloadCustomerLetterWordResource(type, id, response);
	}

	@RequestMapping("/customerlist")
	public void downloadCustomerWordResource(@RequestParam("type") final String type,
			@RequestParam("id") final String id, final HttpServletResponse response) throws IOException {
		documentService.downloadCustomerWordResource(type, id, response);
	}

	@RequestMapping("/electricbill")
	public void downloadElectricBillWordResource(@RequestParam("ids") final String ids,
			final HttpServletResponse response) throws IOException {
		documentService.downloadElectricBillWordResource(ids, response);
	}
	
	@RequestMapping("/electricbillqrcode")
	public void downloadElectricBillQrCodeResource(@RequestParam("idElectricMeter") Long idElectricMeter, @RequestParam("id") final Long id, 
			@RequestParam("amount")final Double amount, @RequestParam("unstructuredMessage") final String uMessage, @RequestParam("mode") final String mode,
			final HttpServletResponse response) throws IOException {
		qrcodeService.generateElectricQrBillCode(idElectricMeter, id, amount, uMessage, mode, response);
	}
	
	@RequestMapping("/vertrag/electricbillqrcode")
	public void downloadElectricBillRentQrCodeResource(@RequestParam("id") final Long id, 
			@RequestParam("mode") final String mode,
			final HttpServletResponse response) throws IOException {
		qrcodeService.generateElectricRentDepositQrBillCode(id, mode, response);
	}
	

	@RequestMapping("/billinglist")
	public void downloadCustomerSalesListWordResource(@RequestParam("jahr") final String jahr,
			final HttpServletResponse response) throws IOException {
		documentService.downloadCustomerSalesListWordResource(jahr, response);
	}

	@GetMapping("/tenant")
	private void downloadCustomerBillWordResource(@RequestParam("jahr") final String jahr, HttpServletResponse response)
			throws IOException {
		documentService.downloadCustomerBillWordResource(jahr, response);
	}

	@RequestMapping("/electricoverviewbilling")
	public void downloaElectricOverwievBillingWordResource(@RequestParam("jahr") final String jahr,
			@RequestParam("status") final String status, final HttpServletResponse response) throws IOException {
		documentService.downloaElectricOverwievBillingWordResource(jahr, status, response);
	}

	@RequestMapping("/billingletter")
	public void downloadBillingLetterWordResource(@RequestParam("id") final Long id, final HttpServletResponse response)
			throws IOException {
		documentService.downloadBillingLetterWordResource(id, response);
	}

	@RequestMapping("/mwst")
	public void downloadMwstWordResource(@RequestParam("year") final String year, @RequestParam("from") final Date from,
			@RequestParam("to") final Date to, final HttpServletResponse response) throws IOException {
		documentService.downloadMwstWordResource(year, from, to, response);
	}

}
