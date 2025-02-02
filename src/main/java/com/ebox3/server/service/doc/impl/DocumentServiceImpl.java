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
import org.springframework.web.server.ResponseStatusException;

import com.ebox3.server.doc.*;
import com.ebox3.server.model.*;
import com.ebox3.server.model.dto.MwstDTO;
import com.ebox3.server.service.doc.DocumentService;
import com.google.common.io.Files;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class DocumentServiceImpl extends CalcHelper implements DocumentService {

    private final Log logger = LogFactory.getLog(getClass());

    private void validateAndWriteResponse(File file, HttpServletResponse response) throws IOException {
        if (file.exists() && file.isFile()) {
            response.setStatus(HttpStatus.OK.value());
            try (OutputStream responseStream = response.getOutputStream()) {
                Files.copy(file, responseStream);
                responseStream.flush();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Generated file not found or invalid.");
        }
    }

    @Override
    public void downloadContractWordResource(final Long id, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outOffer.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "Offerte.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            Contract contract = getContract(id);
            if (contract == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contract not found for ID: " + id);
            }

            while (!new GenerateContract().generateDocOffering(contract, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating Offerte list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during contract document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during contract document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

    @Override
    public void downloadCustomerLetterWordResource(final String type, final Long id, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outMahnung.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "Mahnung.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            Customer customer = "mahnung".equalsIgnoreCase(type) ? getCustomer(id) : null;

            if (customer == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found for ID: " + id);
            }

            while (!new GenerateLetters().generateLetterMahung(customer, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating Mahnung list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during customer letter document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during customer letter document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

    @Override
    public void downloadCustomerWordResource(final String type, final String id, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outKundenliste.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "Kundenliste.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            List<Customer> customers = "liste".equalsIgnoreCase(type) ? getCustomerByStatusText(id) : null;

            if (customers == null || customers.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customers found for the given status.");
            }

            while (!new GenerateLists().generateDocList(customers, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating Kundenliste list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during customer list document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during customer list document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
    @Override
	public void downloaElectricOverwievBillingWordResource(final String jahr, final String status, final HttpServletResponse response) throws IOException {
	    response.setContentType("application/msword");
	    response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

	    File file = new File(getOutputPath() + "outEbillList.docx");

	    try (InputStream template = loadClassPathResource(getTemplatesPath() + "eBillList.docx");
	         OutputStream outputStream = new FileOutputStream(file)) {

	        // Datenabruf und Validierung
	        List<ElectricPeriod> list = getElectricOverwievBilling(jahr, status);
	        if (list == null || list.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No billing data found for the specified year and status.");
	        }

	        // Dokumentgenerierung
	        while (!new GenerateEBillOverviewList().generateEbillList(list, template, outputStream)) {
	            logger.info("Waiting for document generation...");
	        }
	        logger.info("Document generation complete.");
	        
	        validateAndWriteResponse(file, response);
	     
	        logger.info("Memory usage after generating E-Bill list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");
	        
	    } catch (IOException ex) {
	        logger.error("I/O error during E-Bill overview document generation: ", ex);
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
	    } catch (ResponseStatusException ex) {
	        // Weiterleiten der ResponseStatusException
	        throw ex;
	    } catch (Exception ex) {
	        logger.error("Unexpected error during E-Bill overview document generation: ", ex);
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
	    }
	}

    @Override
    public void downloadElectricBillWordResource(final String ids, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outeBill.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "eBill.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            List<ElectricPeriod> periods = getElectricPeriods(ids);
            Customer customer = findAddress(periods);

            if (periods == null || periods.isEmpty() || customer == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer or periods.");
            }

            GenerateEBill ebill = new GenerateEBill();
            while (!ebill.generateDocEBill(customer, periods, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            ElectricPeriod ep = ebill.getElectricPeriod();
            updateElectricPeriodPrice(ep.getId(), ep);

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating Bill list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during electric bill document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during electric bill document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
    
   



    @Override
    public void downloadCustomerSalesListWordResource(final String jahr, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outBillingList.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "billingList.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            List<AdditionalCosts> additionalCostsList = getAllAdditionalCosts(jahr);

            if (additionalCostsList == null || additionalCostsList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No additional costs found for the year.");
            }

            while (!new GenerateBillingList().generateCustomerSalesLetter(additionalCostsList, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating BillingList list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during customer sales list generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during customer sales list generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No payment data found for the year.");
            }

            while (!new GenerateTenantList().generateDocTenantList(listPaymentDatePrice, listPayment, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating MZahlungen list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during customer bill document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during customer bill document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

    @Override
    public void downloadBillingLetterWordResource(final Long id, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outBillingLetter.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "billingLetter.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            AdditionalCosts item = getAdditionalCosts(id);

            if (item == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Additional costs not found for the specified ID.");
            }

            while (!new GenerateBillingLetter().generateDocBillingLetter(item, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating BillingLetter list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during billing letter document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during billing letter document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

    @Override
    public void downloadMwstWordResource(final String year, final Date from, final Date to, final HttpServletResponse response) throws IOException {
        response.setContentType("application/msword");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/msword; charset=UTF-8");

        File file = new File(getOutputPath() + "outMwstLetter.docx");

        try (InputStream template = loadClassPathResource(getTemplatesPath() + "mwstLetter.docx");
             OutputStream outputStream = new FileOutputStream(file)) {

            List<MwstDTO> itemEbox = getMwstBoxen(from, to);
            List<MwstDTO> itemStrom = getMwstStrom(from, to);
            List<MwstDTO> itemRechnungen = getMwstRechnungen(from, to);

            if (itemEbox == null || itemEbox.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data found for the specified date range.");
            }

            while (!new GenerateMwStLetter().generateDocMwstLetter(itemEbox, itemStrom, itemRechnungen, from, to, template, outputStream)) {
                logger.info("Waiting for document generation...");
            }
            logger.info("Document generation complete.");

            validateAndWriteResponse(file, response);
         
	        logger.info("Memory usage after generating MwstLetter list: "
	                + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB");

        } catch (IOException ex) {
            logger.error("I/O error during MwSt document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading or writing files.");
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Unexpected error during MwSt document generation: ", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
}
