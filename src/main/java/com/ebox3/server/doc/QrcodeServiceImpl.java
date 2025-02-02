package com.ebox3.server.doc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Contract;
import com.ebox3.server.model.Customer;
import com.ebox3.server.model.ElectricPeriod;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.CustomerRepository;
import com.ebox3.server.repo.ElectricMeterPeriodRepository;
import com.ebox3.server.service.QrcodeService;
import com.ebox3.server.service.doc.impl.CalcHelper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.codecrete.qrbill.generator.Address;
import net.codecrete.qrbill.generator.Bill;
import net.codecrete.qrbill.generator.BillFormat;
import net.codecrete.qrbill.generator.GraphicsFormat;
import net.codecrete.qrbill.generator.Language;
import net.codecrete.qrbill.generator.OutputSize;
import net.codecrete.qrbill.generator.QRBill;

@Service
public class QrcodeServiceImpl extends CalcHelper implements QrcodeService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	ElectricMeterPeriodRepository electricMeterPeriodRepository;

	@Override
	public void generateElectricRentDepositQrBillCode(final Long id, final String mode,
			final HttpServletResponse response) throws IOException {

		response.setContentType("application/pdf");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/pdf; charset=UTF-8");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=qrcodeBill.pdf");

		// Vertrag abrufen
		Contract contract = null;
		contract = contractRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						String.format("Contract by id %d not found", id)));

		Double totPriceBrutto = null;
		if (mode.equals("Miete")) {

			totPriceBrutto = contract.getEboxs().stream().mapToDouble(ebox -> ebox.getTotalPriceBrutto()).sum();
		} else if (mode.equals("Kaution")) {
			totPriceBrutto = contract.getEboxs().stream().mapToDouble(ebox -> ebox.getDepot()).sum();

		} else {
			totPriceBrutto = 0d;

		}

		String boxNumbers = contract.getEboxs().stream().map(ebox -> String.valueOf(ebox.getBoxNumber()))
				.collect(Collectors.joining(", ")); // Werte mit ", " trennen

		// Setup Bill
		// Setup Bill
		Bill bill = new Bill();
		if (mode.equals("Miete")) {
			bill.setAccount("CH373000526213737501Y"); // QR-IBAN-Miete
		} else if (mode.equals("Kaution")) {
			bill.setAccount("CH923000526213737502B"); // QR-IBAN-Kaution
		} else {
			bill.setAccount(null); // QR-IBAN
		}
		// Set Price
		bill.setAmountFromDouble(totPriceBrutto);
		bill.setCurrency("CHF");

		// Kreditor setzen
		Address creditor = new Address();
		creditor.setName("einstellbox.ch ag");
		creditor.setStreet("Hauptstrasse");
		creditor.setHouseNo("215");
		creditor.setPostalCode("4565");
		creditor.setTown("Recherswil");
		creditor.setCountryCode("CH");
		bill.setCreditor(creditor);

		// Referenz generieren
		try {
			Contract con = updateQrReferenceContract(id, mode);
			logger.debug("QR-Code Rent: ");
			if (mode.equals("Miete")) {
				bill.setReference(con.getQrReferenceCodeRent());
			} else if (mode.equals("Kaution")) {
				bill.setReference(con.getQrReferenceCodeDeposit());
			} else {
				bill.setReference("");
			}

		} catch (RuntimeException e) {
			new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(e.getMessage()));
		}

		bill.setUnstructuredMessage(mode + ": Objekte: " + boxNumbers);

		Customer customer = contract.getCustomer();

		// Debitor setzen
		Address debtor = new Address();
		if (customer.isUseCompanyAddress()) {
			// Versuche Firmenadresse zu parsen
			Optional<Address> optionalDebtor = parseCompanyAddress(customer);
			if (optionalDebtor.isPresent()) {
				debtor = optionalDebtor.get();
			} else {
				// Fallback auf private Adresse
				debtor = parsePrivateAddress(customer);
			}
		} else {
			debtor = parsePrivateAddress(customer);
		}
		bill.setDebtor(debtor);

		// Formatierung der Rechnung
		BillFormat format = new BillFormat();
		format.setGraphicsFormat(GraphicsFormat.PDF);
		format.setOutputSize(OutputSize.A4_PORTRAIT_SHEET);
		format.setLanguage(Language.DE);
		bill.setFormat(format);

		// PDF generieren
		byte[] pdfContent = null;
		try {
			pdfContent = QRBill.generate(bill);
		} catch (RuntimeException e) {
			new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					String.format("Fehler beim Generieren des QR-Codes bzw. PDF.", e.getMessage()));
			logger.error("Fehler beim Generieren des QR-Codes bzw. PDF.", e);
		}

		// PDF in den Response schreiben
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ServletOutputStream out = response.getOutputStream()) {
			byteArrayOutputStream.write(pdfContent);
			byteArrayOutputStream.writeTo(out);
			out.flush();
		} catch (IOException e) {
			new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					String.format("Fehler beim Schreiben der PDF-Datei in den OutputStream.", e.getMessage()));
			logger.error("Fehler beim Schreiben der PDF-Datei in den OutputStream.", e);
		}

	}

	@Override
	public void generateElectricQrBillCode(final Long idElectricMeter, final Long id, final Double amount,
			final String uMessage, final String mode, final HttpServletResponse response) throws IOException {
		// Eingabevalidierung
		if (amount == null || amount <= 0) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Keine QR-Rechnung möglich, da Betrag 0 CHF oder NEGATIV ist!");
		}
		// Optional: uMessage auf Null prüfen und ggf. einen Standardwert setzen
		String message = (uMessage != null) ? uMessage : "";

		response.setContentType("application/pdf");
		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/pdf; charset=UTF-8");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=qrcodeBill.pdf");

		// Kunde abrufen
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						String.format("Customer by id %d not found", id)));

		// Setup Bill
		Bill bill = new Bill();
		if (mode.equals("Miete")) {
			bill.setAccount("CH373000526213737501Y"); // QR-IBAN-Miete
		} else if (mode.equals("Kaution")) {
			bill.setAccount("CH923000526213737502B"); // QR-IBAN-Kaution
		} else {
			bill.setAccount(null); // QR-IBAN
		}

		bill.setAmountFromDouble(amount);
		bill.setCurrency("CHF");

		// Kreditor setzen
		Address creditor = new Address();
		creditor.setName("einstellbox.ch ag");
		creditor.setStreet("Hauptstrasse");
		creditor.setHouseNo("215");
		creditor.setPostalCode("4565");
		creditor.setTown("Recherswil");
		creditor.setCountryCode("CH");
		bill.setCreditor(creditor);

		// Referenz generieren
		try {
			ElectricPeriod ep = updateQrReference(idElectricMeter);
			bill.setReference(ep.getQrReferenceCode());
		} catch (RuntimeException e) {
			new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(e.getMessage()));
		}

		// logger.info("QR-Rechnungsreferenz: " + refCode);
		bill.setUnstructuredMessage(message);

		// Debitor setzen
		Address debtor = new Address();
		if (customer.isUseCompanyAddress()) {
			// Versuche Firmenadresse zu parsen
			Optional<Address> optionalDebtor = parseCompanyAddress(customer);
			if (optionalDebtor.isPresent()) {
				debtor = optionalDebtor.get();
			} else {
				// Fallback auf private Adresse
				debtor = parsePrivateAddress(customer);
			}
		} else {
			debtor = parsePrivateAddress(customer);
		}
		bill.setDebtor(debtor);

		// Formatierung der Rechnung
		BillFormat format = new BillFormat();
		format.setGraphicsFormat(GraphicsFormat.PDF);
		format.setOutputSize(OutputSize.A4_PORTRAIT_SHEET);
		format.setLanguage(Language.DE);
		bill.setFormat(format);

		// PDF generieren
		byte[] pdfContent = null;
		try {
			pdfContent = QRBill.generate(bill);
		} catch (RuntimeException e) {
			new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					String.format("Fehler beim Generieren des QR-Codes bzw. PDF.", e.getMessage()));
			logger.error("Fehler beim Generieren des QR-Codes bzw. PDF.", e);
		}

		// PDF in den Response schreiben
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ServletOutputStream out = response.getOutputStream()) {
			byteArrayOutputStream.write(pdfContent);
			byteArrayOutputStream.writeTo(out);
			out.flush();
		} catch (IOException e) {
			new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					String.format("Fehler beim Schreiben der PDF-Datei in den OutputStream.", e.getMessage()));
			logger.error("Fehler beim Schreiben der PDF-Datei in den OutputStream.", e);
			throw e;
		}
	}

	private ElectricPeriod updateQrReference(Long epId) throws ResourceNotFoundException {
		ElectricPeriod ep = electricMeterPeriodRepository.findById(epId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("ElectricPeriod by id %d not found", epId)));
		ep.setQrReferenceCode(generateQRReference());
		return electricMeterPeriodRepository.save(ep);
	}

	private Contract updateQrReferenceContract(Long cId, String mode) throws ResourceNotFoundException {
		Contract contract = contractRepository.findById(cId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Contract by id %d not found", cId)));
		if (mode.equals("Miete")) {
			contract.setQrReferenceCodeRent(generateQRReference());
		} else if (mode.equals("Kaution")) {
			contract.setQrReferenceCodeDeposit(generateQRReference());
		}
		return contractRepository.save(contract);
	}

	// Beispiel für die Auslagerung der Adressverarbeitung
	private Optional<Address> parseCompanyAddress(Customer customer) {
		Pattern pattern = Pattern.compile("^([\\p{L}\\s]+)\\s(\\d+),?\\s(\\d+)\\s(.+)$");
		Matcher matcher = pattern.matcher(customer.getFirmenAnschrift());
		if (matcher.matches()) {
			Address debtor = new Address();
			debtor.setName(customer.getFirmenName());
			debtor.setStreet(matcher.group(1));
			debtor.setHouseNo(matcher.group(2));
			debtor.setPostalCode(matcher.group(3));
			debtor.setTown(matcher.group(4));
			debtor.setCountryCode("CH");
			return Optional.of(debtor);
		}
		return Optional.empty();
	}

	private Address parsePrivateAddress(Customer customer) {
		Address debtor = new Address();
		Pattern pattern = Pattern.compile("^([\\p{L}\\s]+)\\s(\\d+)$");
		Matcher matcher = pattern.matcher(customer.getStrasse());
		debtor.setName(customer.getVorname() + " " + customer.getName());
		if (matcher.matches()) {
			debtor.setStreet(matcher.group(1));
			debtor.setHouseNo(matcher.group(2));
		} else {
			debtor.setStreet(customer.getStrasse());
			debtor.setHouseNo(""); // oder einen Standardwert
		}
		debtor.setPostalCode(customer.getPlz());
		debtor.setTown(customer.getOrt());
		debtor.setCountryCode("CH");
		return debtor;
	}
}
