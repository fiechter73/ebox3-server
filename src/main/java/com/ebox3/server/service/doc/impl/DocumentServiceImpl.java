package com.ebox3.server.service.doc.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ebox3.server.doc.GenerateBillingLetter;
import com.ebox3.server.doc.GenerateBillingList;
import com.ebox3.server.doc.GenerateContract;
import com.ebox3.server.doc.GenerateEBill;
import com.ebox3.server.doc.GenerateEBillOverviewList;
import com.ebox3.server.doc.GenerateLetters;
import com.ebox3.server.doc.GenerateLists;
import com.ebox3.server.doc.GenerateMwStLetter;
import com.ebox3.server.doc.GenerateTenantList;
import com.ebox3.server.model.AdditionalCosts;
import com.ebox3.server.model.Contract;
import com.ebox3.server.model.Customer;
import com.ebox3.server.model.ElectricPeriod;
import com.ebox3.server.model.Payment;
import com.ebox3.server.model.PaymentDatePrice;
import com.ebox3.server.model.dto.MwstDTO;
import com.ebox3.server.service.doc.DocumentService;
import com.google.common.io.Files;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class DocumentServiceImpl extends CalcHelper implements DocumentService {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public void downloadContractWordResource(final Long id, final HttpServletResponse response) throws IOException {
		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;

		try {
			template = loadClassPathResource(getTemplatesPath() + "Offerte.docx");
			file = loadOutputFile(getOutputPath() + "outOffer.docx");
			outputStream = new FileOutputStream(file);

		} catch (final Exception ex) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		}

		if (file.isFile()) {
			try {
				Contract con = getContract(id);
				while (!new GenerateContract().generateDocOffering(con, template, outputStream)) {
					logger.info("Wait....");
				}
				response.setStatus(HttpStatus.OK.value());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
				template.close();
				outputStream.close();
				logger.info("KB Offerte: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			}

		}
	}

	@Override
	public void downloadCustomerLetterWordResource(final String type, final Long id, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;
		try {

			template = loadClassPathResource(getTemplatesPath() + "Mahnung.docx");
			file = loadOutputFile(getOutputPath() + "outManhnung.docx");
			outputStream = new FileOutputStream(file);

		} catch (final Exception ex) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}
		if (file.isFile()) {
			try {

				Customer cust = null;
				if (type.contentEquals("mahnung")) {
					cust = getCustomer(id);
				}

				while (!new GenerateLetters().generateLetterMahung(cust, template, outputStream)) {
					logger.info("Wait....");
				}
				logger.info("Done....");

				response.setStatus(HttpStatus.OK.value());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
				template.close();
				outputStream.close();

				logger.info("KB Letter: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}
	}

	@Override
	public void downloadCustomerWordResource(final String type, final String id, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");
		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;

		try {
			template = loadClassPathResource(getTemplatesPath() + "Kundenliste.docx");
			file = loadOutputFile(getOutputPath() + "outKundenliste.docx");
			outputStream = new FileOutputStream(file);

		} catch (final Exception ex) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}
		if (file.isFile()) {
			List<Customer> cust = null;
			if (type.contentEquals("liste")) {
				cust = getCustomerByStatusText(id);
			}
			try {

				while (!new GenerateLists().generateDocList(cust, template, outputStream)) {
					logger.info("Wait....");
				}
				logger.info("Done....");
				response.setStatus(HttpStatus.OK.value());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
				template.close();
				outputStream.close();
				logger.info("KB Kundenliste: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}
	}

	@Override
	public void downloadElectricBillWordResource(final String ids, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");
		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;
		try {
			template = loadClassPathResource(getTemplatesPath() + "eBill.docx");
			file = loadOutputFile(getOutputPath() + "outeBill.docx");
			outputStream = new FileOutputStream(file);
		} catch (Exception e) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}

		if (file.isFile()) {

			final List<ElectricPeriod> list = getElectricPeriods(ids);
			Customer cust = findAddress(list); // Falls die Box weitervermietet wurde muss die richtige Adresse bei der
												// Stromabrechnung berücksichtigt werden!

			GenerateEBill ebill = new GenerateEBill();
			try {

				while (!ebill.generateDocEBill(cust, list, template, outputStream)) {
					logger.info("Wait....");
				}
				logger.info("Done....");

				ElectricPeriod ep = ebill.getElectricPeriod();
				updateElectricPeriodPrice(ep.getId(), ep);

				response.setStatus(HttpStatus.OK.value());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
				template.close();
				outputStream.close();
				logger.info("KB EBill: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}
	}

	@Override
	public void downloadCustomerSalesListWordResource(final String jahr, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;
		try {
			template = loadClassPathResource(getTemplatesPath() + "billingList.docx");
			file = loadOutputFile(getOutputPath() + "outBillingList.docx");
			outputStream = new FileOutputStream(file);

		} catch (Exception e) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}
		if (file.isFile()) {
			try {
				List<AdditionalCosts> additionalCostsList = null;
				additionalCostsList = getAllAdditionalCosts(jahr);

				while (!new GenerateBillingList().generateCustomerSalesLetter(additionalCostsList, template,
						outputStream)) {
					logger.info("Wait....");
				}
				logger.info("Done....");

				response.setStatus(HttpStatus.OK.value());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
				template.close();
				outputStream.close();

				logger.info("KB BillingList: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
			} catch (final Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}

		}
	}

	@Override
	public void downloadCustomerBillWordResource(final String jahr, HttpServletResponse response) throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;
		try {
			file = loadOutputFile(getOutputPath() + "outMZahlungen.docx");
			template = loadClassPathResource(getTemplatesPath() + "MZahlungen.docx");
			outputStream = new FileOutputStream(file);
		} catch (Exception e) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}

		if (file.isFile()) {
			try {
				final List<Payment> listPayment = getPaymenByTenant(jahr);
				final List<PaymentDatePrice> listPaymentDatePrice = getPaymenDatePricetByTenant(jahr);
				if (!listPaymentDatePrice.isEmpty()) {
					while (!new GenerateTenantList().generateDocTenantList(listPaymentDatePrice, listPayment, template,
							outputStream)) {
						logger.info("Wait....");
					}
					logger.info("Done....");
					response.setStatus(HttpStatus.OK.value());
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
					logger.info("KB TenantList: "
							+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
				}
			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}
	}

	@Override
	public void downloaElectricOverwievBillingWordResource(final String jahr, final String status,
			final HttpServletResponse response) throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;

		try {
			template = loadClassPathResource(getTemplatesPath() + "eBillList.docx");
			file = loadOutputFile(getOutputPath() + "outEbillList.docx");
			outputStream = new FileOutputStream(file);

		} catch (Exception e) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}

		if (file.isFile()) {
			try {
				final List<ElectricPeriod> list = getElectricOverwievBilling(jahr, status);

				if (!list.isEmpty()) {
					while (!new GenerateEBillOverviewList().generateEbillList(list, template, outputStream)) {
						logger.info("Wait....");
					}
					logger.info("Done....");
					response.setStatus(HttpStatus.OK.value());
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
					template.close();
					outputStream.close();
					logger.info("KB EBillList: "
							+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
				}
			} catch (final Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}

	}

	@Override
	public void downloadBillingLetterWordResource(final Long id, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;

		try {
			template = loadClassPathResource(getTemplatesPath() + "billingLetter.docx");
			file = loadOutputFile(getOutputPath() + "outBillingLetter.docx");
			outputStream = new FileOutputStream(file);

		} catch (Exception e) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}
		if (file.isFile()) {
			try {

				final AdditionalCosts item = getAdditionalCosts(id);

				if (item != null) {
					while (!new GenerateBillingLetter().generateDocBillingLetter(item, template, outputStream)) {
						logger.info("Wait....");
					}
					response.setStatus(HttpStatus.OK.value());
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
					template.close();
					outputStream.close();
					logger.info("KB BillingLetter: "
							+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
				}
			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}
	}

	@Override
	public void downloadMwstWordResource(final String year, final Date from, final Date to,
			final HttpServletResponse response) throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = null;
		InputStream template = null;
		OutputStream outputStream = null;
		try {

			template = loadClassPathResource(getTemplatesPath() + "mwstLetter.docx");
			file = loadOutputFile(getOutputPath() + "outMwstLetter.docx");
			outputStream = new FileOutputStream(file);

		} catch (Exception e) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "File not found!");
		}
		if (file.isFile()) {
			try {

				final List<MwstDTO> itemEbox = getMwstBoxen(from, to);
				final List<MwstDTO> itemStrom = getMwstStrom(from, to);
				final List<MwstDTO> itemRechnungen = getMwstRechnungen(from, to);

				itemEbox.forEach(item -> {
					logger.info(item.getBoxes() + " : " + item.getPayDate());
				});

				if (itemEbox != null) {
					while (!new GenerateMwStLetter().generateDocMwstLetter(itemEbox, itemStrom, itemRechnungen, from,
							to, template, outputStream)) {
						logger.info("Wait....");
					}
					logger.info("Done....");
					response.setStatus(HttpStatus.OK.value());
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
					template.close();
					outputStream.close();
					logger.info("KB BillingLetter: "
							+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
				}
			} catch (Exception ex) {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
			}
		}
	}
}