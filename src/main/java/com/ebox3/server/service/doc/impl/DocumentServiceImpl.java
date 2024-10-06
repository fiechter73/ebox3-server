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

		File file = new File(getOutputPath() + "outOffer.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "Offerte.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			Contract contract = getContract(id);

			while (!new GenerateContract().generateDocOffering(contract, template, outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after contract generation: " +
					(double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during contract document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadCustomerLetterWordResource(final String type, final Long id, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outMahnung.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "Mahnung.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			Customer customer = null;
			if ("mahnung".equalsIgnoreCase(type)) {
				customer = getCustomer(id);
			}

			if (customer == null) {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "Customer not found for id: " + id);
				return;
			}

			// Generate letter
			while (!new GenerateLetters().generateLetterMahung(customer, template, outputStream)) {
				logger.info("Wait....");
			}
			logger.info("Done....");

			// Datei im Response streamen
			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "File not found or not valid.");
			}

			logger.info("KB Letter: " +
					(double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

		} catch (final Exception ex) {
			logger.error("Error during document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadCustomerWordResource(final String type, final String id, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outKundenliste.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "Kundenliste.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			List<Customer> customers = null;
			if ("liste".equalsIgnoreCase(type)) {
				customers = getCustomerByStatusText(id);
			}

			if (customers == null || customers.isEmpty()) {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "No customers found for the given status.");
				return;
			}

			while (!new GenerateLists().generateDocList(customers, template, outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after customer list generation: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during customer list document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadElectricBillWordResource(final String ids, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outeBill.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "eBill.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			List<ElectricPeriod> periods = getElectricPeriods(ids);
			Customer customer = findAddress(periods); // Handle sub-leased addresses for electric bills

			if (periods == null || periods.isEmpty() || customer == null) {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid customer or periods.");
				return;
			}

			GenerateEBill ebill = new GenerateEBill();
			while (!ebill.generateDocEBill(customer, periods, template, outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			ElectricPeriod ep = ebill.getElectricPeriod();
			updateElectricPeriodPrice(ep.getId(), ep);

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after eBill generation: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during electric bill document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadCustomerSalesListWordResource(final String jahr, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outBillingList.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "billingList.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			List<AdditionalCosts> additionalCostsList = getAllAdditionalCosts(jahr);

			if (additionalCostsList == null || additionalCostsList.isEmpty()) {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "No additional costs found for the year.");
				return;
			}

			while (!new GenerateBillingList().generateCustomerSalesLetter(additionalCostsList, template,
					outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after billing list generation: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during billing list document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadCustomerBillWordResource(final String jahr, HttpServletResponse response) throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outMZahlungen.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "MZahlungen.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			List<Payment> listPayment = getPaymenByTenant(jahr);
			List<PaymentDatePrice> listPaymentDatePrice = getPaymenDatePricetByTenant(jahr);

			if (listPaymentDatePrice == null || listPaymentDatePrice.isEmpty()) {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "No payment data found for the year.");
				return;
			}

			while (!new GenerateTenantList().generateDocTenantList(listPaymentDatePrice, listPayment, template,
					outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after tenant list generation: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during tenant bill document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloaElectricOverwievBillingWordResource(final String jahr, final String status,
			final HttpServletResponse response) throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outEbillList.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "eBillList.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			List<ElectricPeriod> list = getElectricOverwievBilling(jahr, status);

			if (list == null || list.isEmpty()) {
				response.sendError(HttpStatus.BAD_REQUEST.value(),
						"No billing data found for the specified year and status.");
				return;
			}

			while (!new GenerateEBillOverviewList().generateEbillList(list, template, outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after generating E-Bill list: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during E-Bill overview document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadBillingLetterWordResource(final Long id, final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outBillingLetter.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "billingLetter.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			AdditionalCosts item = getAdditionalCosts(id);

			if (item == null) {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "Additional costs not found for the specified ID.");
				return;
			}

			while (!new GenerateBillingLetter().generateDocBillingLetter(item, template, outputStream)) {
				logger.info("Waiting for document generation...");
			}
			logger.info("Document generation complete.");

			if (file.exists() && file.isFile()) {
				response.setStatus(HttpStatus.OK.value());
				try (OutputStream responseStream = response.getOutputStream()) {
					Files.copy(file, responseStream);
					responseStream.flush();
				}
			} else {
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generated file not found or invalid.");
			}

			logger.info("Memory usage after generating billing letter: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

		} catch (Exception ex) {
			logger.error("Error during billing letter document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

	@Override
	public void downloadMwstWordResource(final String year, final Date from, final Date to,
			final HttpServletResponse response)
			throws IOException {

		response.setContentType("application/msword");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

		File file = new File(getOutputPath() + "outMwstLetter.docx");

		try (InputStream template = loadClassPathResource(getTemplatesPath() + "mwstLetter.docx");
				OutputStream outputStream = new FileOutputStream(file)) {

			List<MwstDTO> itemEbox = getMwstBoxen(from, to);
			List<MwstDTO> itemStrom = getMwstStrom(from, to);
			List<MwstDTO> itemRechnungen = getMwstRechnungen(from, to);

			itemEbox.forEach(item -> {
				logger.info(item.getBoxes() + " : " + item.getPayDate());
			});

			if (itemEbox != null && !itemEbox.isEmpty()) {
				while (!new GenerateMwStLetter().generateDocMwstLetter(itemEbox, itemStrom, itemRechnungen, from, to,
						template, outputStream)) {
					logger.info("Waiting for document generation...");
				}
				logger.info("Document generation complete.");

				response.setStatus(HttpStatus.OK.value());
				if (file.exists() && file.isFile()) {
					try (OutputStream responseStream = response.getOutputStream()) {
						Files.copy(file, responseStream);
						responseStream.flush();
					}
				} else {
					response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
							"Generated file not found or invalid.");
				}

				logger.info("Memory usage after generating MwSt letter: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024
						+ " KB");
			} else {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "No data found for the specified date range.");
			}

		} catch (Exception ex) {
			logger.error("Error during MwSt document generation: ", ex);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}

}