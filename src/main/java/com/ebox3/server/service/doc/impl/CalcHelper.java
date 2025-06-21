package com.ebox3.server.service.doc.impl;

import static java.lang.Long.valueOf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.ebox3.server.doc.Util;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.AdditionalCosts;
import com.ebox3.server.model.Contract;
import com.ebox3.server.model.Customer;
import com.ebox3.server.model.ElectricPeriod;
import com.ebox3.server.model.Payment;
import com.ebox3.server.model.PaymentDatePrice;
import com.ebox3.server.model.dto.MwstDTO;
import com.ebox3.server.repo.AdditionalCostsRepository;
import com.ebox3.server.repo.AttributeKeyRepository;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.CustomerRepository;
import com.ebox3.server.repo.ElectricMeterPeriodRepository;
import com.ebox3.server.repo.PaymentDatePriceRepository;
import com.ebox3.server.repo.PaymentRepository;

public class CalcHelper {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ElectricMeterPeriodRepository electricMeterPeriodsRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PaymentDatePriceRepository paymenteDatePriceRepository;

	@Autowired
	AdditionalCostsRepository additionalCostsRepository;

	@Autowired
	AttributeKeyRepository attributeKeyRepository;

	@Value("${ebox.templatesPath}")
	private String templatesPath;

	@Value("${ebox.outpath}")
	private String outputPath;

	public String getTemplatesPath() {
		return templatesPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	protected final Log logger = LogFactory.getLog(getClass());

	public Double calcMwst(Double sum, Double mwstSatz) {
		Double val = 1 + (mwstSatz / 100);
		Double mwst = sum - (sum / val);
		Double res = kaufRunden(mwst);
		return Math.round(res * 100) / 100.0;
	}

	public Double kaufRunden(Double x) { // Runde auf 5 Rappen genau!
		return Math.round(x / 0.05) * 0.05;
	}

	/**
	 * Mwst Boxen
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	protected List<MwstDTO> getMwstBoxen(Date from, Date to) {

		Date startDate = from;
		Date endDate = to;

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);

		Long year = (long) cal.get(Calendar.YEAR);

		ArrayList<MwstDTO> mwstList = new ArrayList<MwstDTO>();
		Double mwst = Double.valueOf(attributeKeyRepository.findMwstSatz("mwst", year));

		List<PaymentDatePrice> janList = paymenteDatePriceRepository.findJanByStartEndDate(startDate, endDate);
		janList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Januar");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoJanDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoJanPrice() != null ? pdp.getBruttoJanPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoJanPrice() != null ? pdp.getBruttoJanPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> febList = paymenteDatePriceRepository.findFebByStartEndDate(startDate, endDate);
		febList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Februar");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoFebDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoFebPrice() != null ? pdp.getBruttoFebPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoFebPrice() != null ? pdp.getBruttoFebPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> marList = paymenteDatePriceRepository.findMarByStartEndDate(startDate, endDate);
		marList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("März");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoMarDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoMarPrice() != null ? pdp.getBruttoMarPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoMarPrice() != null ? pdp.getBruttoMarPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> aprList = paymenteDatePriceRepository.findAprByStartEndDate(startDate, endDate);
		aprList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("April");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoAprDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoAprPrice() != null ? pdp.getBruttoAprPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoAprPrice() != null ? pdp.getBruttoAprPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> maiList = paymenteDatePriceRepository.findMaiByStartEndDate(startDate, endDate);
		maiList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Mai");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoMaiDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoMaiPrice() != null ? pdp.getBruttoMaiPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoMaiPrice() != null ? pdp.getBruttoMaiPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> junList = paymenteDatePriceRepository.findJunByStartEndDate(startDate, endDate);
		junList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Juni");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoJunDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoJunPrice() != null ? pdp.getBruttoJunPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoJunPrice() != null ? pdp.getBruttoJunPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> julList = paymenteDatePriceRepository.findJulByStartEndDate(startDate, endDate);
		julList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Juli");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoJulDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoJulPrice() != null ? pdp.getBruttoJulPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoJulPrice() != null ? pdp.getBruttoJulPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> augList = paymenteDatePriceRepository.findAugByStartEndDate(startDate, endDate);
		augList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("August");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoAugDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoAugPrice() != null ? pdp.getBruttoAugPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoAugPrice() != null ? pdp.getBruttoAugPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> sepList = paymenteDatePriceRepository.findSepByStartEndDate(startDate, endDate);
		sepList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("September");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoSepDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoSepPrice() != null ? pdp.getBruttoSepPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoSepPrice() != null ? pdp.getBruttoSepPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> octList = paymenteDatePriceRepository.findOctByStartEndDate(startDate, endDate);
		octList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Oktober");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoOctDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoOctPrice() != null ? pdp.getBruttoOctPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoOctPrice() != null ? pdp.getBruttoOctPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> novList = paymenteDatePriceRepository.findNovByStartEndDate(startDate, endDate);
		novList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("November");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoNovDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoNovPrice() != null ? pdp.getBruttoNovPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoNovPrice() != null ? pdp.getBruttoNovPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});

		List<PaymentDatePrice> decList = paymenteDatePriceRepository.findDecByStartEndDate(startDate, endDate);
		decList.forEach(pdp -> {
			MwstDTO mwstDTO = new MwstDTO();
			mwstDTO.setMonth("Dezember");
			mwstDTO.setCustomer(pdp.getPayment().getContract().getCustomer().getAnrede() + " "
					+ pdp.getPayment().getContract().getCustomer().getVorname() + " "
					+ pdp.getPayment().getContract().getCustomer().getName());
			mwstDTO.setBoxes(pdp.getPayment().getBoxNumbers());
			mwstDTO.setPayDate(pdp.getBruttoDecDate());
			mwstDTO.setBruttoPrice(pdp.getBruttoDecPrice() != null ? pdp.getBruttoDecPrice() : 0.00d);
			mwstDTO.setMwstPrice(calcMwst(pdp.getBruttoDecPrice() != null ? pdp.getBruttoDecPrice() : 0.00d, mwst));
			mwstDTO.setNettoPrice(mwstDTO.getBruttoPrice() - mwstDTO.getMwstPrice());
			mwstList.add(mwstDTO);
		});
		return mwstList;
	}

	/**
	 * Mwst Strom
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	protected List<MwstDTO> getMwstStrom(Date from, Date to) {

		Date startDate = from;
		Date endDate = to;

		ArrayList<MwstDTO> mwstList = new ArrayList<MwstDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		List<ElectricPeriod> epList = electricMeterPeriodsRepository.findPaymentDate(startDate, endDate);
		epList.forEach(pay -> {
			MwstDTO mwst = new MwstDTO();
			if (pay.getZaehlerFromPeriode() != null) {
				mwst.setMonth(sdf.format(pay.getZaehlerFromPeriode()) + "-" + sdf.format(pay.getZaehlerToPeriode()));
				mwst.setCustomer(pay.getPrintAnschrift() != null ? pay.getPrintAnschrift() : "");
				mwst.setBoxes(pay.getStatusText() != null ? pay.getStatusText() : " ");
				mwst.setPayDate(pay.getZahlungEingegangen() != null ? pay.getZahlungEingegangen() : new Date());
				Double mwstPrice = pay.getMwstPrice() != null ? pay.getMwstPrice() : 0.00d;
				mwst.setMwstPrice(mwstPrice);
				Double bruttoPrice = pay.getStromPriceBrutto() != null ? pay.getStromPriceBrutto() : 0.00d;
				mwst.setBruttoPrice(bruttoPrice);
				mwst.setNettoPrice(bruttoPrice - mwstPrice);
			}
			mwstList.add(mwst);
		});
		return mwstList;
	}

	/**
	 * Mwst Rechnungen
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	protected List<MwstDTO> getMwstRechnungen(Date from, Date to) {

		Date startDate = from;
		Date endDate = to;

		ArrayList<MwstDTO> mwstList = new ArrayList<MwstDTO>();

		List<AdditionalCosts> epList = additionalCostsRepository.findPaymentDate(startDate, endDate);
		epList.forEach(pay -> {
			MwstDTO mwst = new MwstDTO();
			mwst.setMonth(pay.getRechnungsart());
			mwst.setCustomer(pay.getContract().getCustomer().getName());
			mwst.setBoxes(pay.getStatusText());
			mwst.setPayDate(pay.getBillingDate());
			mwst.setMwstPrice(pay.getMwstPrice() != null ? pay.getMwstPrice() : 0.00d);
			mwst.setBruttoPrice(pay.getSumBruttoPrice() != null ? pay.getSumBruttoPrice() : 0.00d);
			mwst.setNettoPrice(mwst.getBruttoPrice() - mwst.getMwstPrice());
			mwstList.add(mwst);
		});
		return mwstList;
	}

	/**
	 * Find Address
	 * 
	 * @param electricPeriodList
	 * @return
	 */
	protected Customer findAddress(List<ElectricPeriod> electricPeriodList) {

		Optional<ElectricPeriod> eP = electricPeriodList.stream()
				.filter(electricPeriod -> electricPeriod.getStatusText().contentEquals("offen")).findFirst();

		Customer cust = null;
		if (eP.isPresent()) {
			ElectricPeriod e = eP.get();
			cust = getCustomer(e.getCustomerId());

		}
		return cust;
	}

	/**
	 * Get Contract
	 * 
	 * @param id
	 * @return
	 */
	protected Contract getContract(final Long id) {
		final Optional<Contract> con = contractRepository.findById(id);
		Contract contract = null;
		try {
			contract = con.get();
		} catch (final NoSuchElementException ex) {
			logger.error("NoSuchElementException: " + ex.toString());
		}
		return contract;
	}

	/**
	 * Get Customer
	 * 
	 * @param id
	 * @return
	 */
	protected Customer getCustomer(final Long id) {
		final Optional<Customer> con = customerRepository.findById(id);
		Customer customer = null;
		try {
			customer = con.get();
		} catch (final NoSuchElementException ex) {
			logger.error("NoSuchElementException: " + ex.toString());
		}
		return customer;
	}

	/**
	 * Update Electric Period Pirce
	 * 
	 * @param periodId
	 * @param electricPeriodsRequest
	 * @return
	 */
	protected ElectricPeriod updateElectricPeriodPrice(Long periodId, ElectricPeriod electricPeriodsRequest) {

		if (!electricMeterPeriodsRepository.existsById(periodId)) {
			throw new ResourceNotFoundException("ElectricPeriod Id " + periodId + " not found");
		}
		return electricMeterPeriodsRepository.findById(periodId).map(electricPeriod -> {
			electricPeriod.setStromPriceBrutto(electricPeriodsRequest.getStromPriceBrutto());
			electricPeriod.setStromPriceNetto(electricPeriodsRequest.getStromPriceNetto());
			electricPeriod.setMwstPrice(electricPeriodsRequest.getMwstPrice());
			electricPeriod.setStatusText("verrechnet");
			electricPeriod.setPrintDate(Util.getCurrentDate());
			electricPeriod.setInfo(electricPeriodsRequest.getInfo());
			electricPeriod.setZaehlerFromPeriode(electricPeriodsRequest.getZaehlerFromPeriode());

			return electricMeterPeriodsRepository.save(electricPeriod);
		}).orElseThrow(() -> new ResourceNotFoundException("PeriodId " + periodId + " not found"));
	}

	/**
	 * Get Additional Costs
	 * 
	 * @param id
	 * @return
	 */
	protected AdditionalCosts getAdditionalCosts(final Long id) {
		Optional<AdditionalCosts> list = additionalCostsRepository.findById(id);

		AdditionalCosts aCost = null;
		if (list.isPresent()) {
			aCost = list.get();
		}
		return aCost;
	}

	/**
	 * Get Eelctric Period
	 * 
	 * @param ids
	 * @return
	 */

	protected List<ElectricPeriod> getElectricPeriods(final String ids) {

		final List<ElectricPeriod> epList = new ArrayList<ElectricPeriod>();
		final List<String> myList = Arrays.asList(ids.split(","));
		myList.forEach(item -> {
			final Optional<ElectricPeriod> eP = electricMeterPeriodsRepository.findById(valueOf(item));
			if (eP.isPresent()) {
				epList.add(eP.get());
			}
		});
		return epList;
	}

	/**
	 * Get All Additional Costs
	 * 
	 * @param jahr
	 * @return
	 */
	protected List<AdditionalCosts> getAllAdditionalCosts(String jahr) {
		final List<AdditionalCosts> addCosts = additionalCostsRepository.findAll();
		List<AdditionalCosts> newAddCosts = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy");

		newAddCosts = addCosts.stream().filter(str -> str.getCreatedAt() != null)
				.filter(str -> df.format(str.getCreatedAt()).contentEquals(jahr))
				.sorted(Comparator.comparing(AdditionalCosts::getId)).collect(Collectors.toList());

		return newAddCosts;
	}

	/**
	 * Get Overview Billing
	 * 
	 * @param jahr
	 * @param status
	 * @return
	 */
	protected List<ElectricPeriod> getElectricOverwievBilling(final String jahr, final String status) {
		List<ElectricPeriod> electricPeriod = electricMeterPeriodsRepository.findAll();
		List<ElectricPeriod> list = null;
		List<ElectricPeriod> newList1 = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy");

		list = electricPeriod.stream().filter(str -> str.getZaehlerToPeriode() != null)
				.filter(str -> df.format(str.getZaehlerToPeriode()).contentEquals(jahr))
				.sorted(Comparator.comparingLong(ElectricPeriod::getCustomerId)).collect(Collectors.toList());

		if (status.contentEquals("offen")) {
			newList1 = list.stream()
					.filter(str -> str.getStatusText().equals("offen") || str.getStatusText().equals("verrechnet"))
					.sorted(Comparator.comparingLong(ElectricPeriod::getCustomerId)).distinct()
					.collect(Collectors.toList());

		} else if (status.contentEquals("bezahlt")) {
			newList1 = list.stream().filter(str -> str.getStatusText().equals("bezahlt"))
					.sorted(Comparator.comparingLong(ElectricPeriod::getCustomerId)).distinct()
					.collect(Collectors.toList());
		} else if (status.contentEquals("alle")) {
			newList1 = list.stream().sorted(Comparator.comparingLong(ElectricPeriod::getCustomerId)).distinct()
					// .collect(Collectors.toList()
					.sorted(Comparator.comparingLong(ElectricPeriod::getCustomerId)).distinct()
					.collect(Collectors.toList());
		}
		return newList1;
	}

	protected void createSheetWithDataForMwStCalculation(Workbook workbook, String sheetName, List<MwstDTO> data) {

		Sheet sheet = workbook.createSheet(sheetName);

		int rowIndex = 0;

		// === Stil für Header-Zelle ===
		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		// === Stil für Betrag als Währung ===
		CellStyle currencyStyle = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		currencyStyle.setDataFormat(format.getFormat("#,##0.00 \"CHF\""));

		// === Header-Zeile ===
		Row headerRow = sheet.createRow(rowIndex++);
		String[] headers = { "Mieter", "Box(en)", "Monat", "Bezahlt am", "Netto Preis", "Brutto Preis", "MwST" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		// === Datenzeilen ===
		for (MwstDTO dto : data) {
			Row row = sheet.createRow(rowIndex++);

			// Mieter
			Cell customer = row.createCell(0);
			customer.setCellValue(dto.getCustomer() != null ? dto.getCustomer() : "");

			// Box
			Cell box = row.createCell(1);
			box.setCellValue(dto.getBoxes() != null ? dto.getBoxes() : "");

			// Month
			Cell month = row.createCell(2);
			month.setCellValue(dto.getMonth() != null ? dto.getMonth() : "");

			// Payed date
			Cell pay = row.createCell(3);
			Date payDate = dto.getPayDate();
			String formattedDate = payDate != null ? payDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "";
			pay.setCellValue(formattedDate);

			// Netto price
			Cell netPrice = row.createCell(4);
			if (dto.getNettoPrice() != null) {
				netPrice.setCellValue(dto.getNettoPrice().doubleValue());
				netPrice.setCellStyle(currencyStyle);
			}

			// Brutto price
			Cell brutPrice = row.createCell(5);
			if (dto.getBruttoPrice() != null) {
				brutPrice.setCellValue(dto.getBruttoPrice().doubleValue());
				brutPrice.setCellStyle(currencyStyle);
			}
			// Mwst price
			Cell mwst = row.createCell(6);
			if (dto.getMwstPrice() != null) {
				mwst.setCellValue(dto.getMwstPrice().doubleValue());
				mwst.setCellStyle(currencyStyle);
			}
		}

		// === Spaltenbreite automatisch anpassen ===
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	protected void createSheetWithCombinedPaymentData(Workbook workbook, String sheetName, List<Payment> payments,
			List<PaymentDatePrice> prices) {

		DataFormat dataFormat = workbook.createDataFormat();

		// === Styles ===
		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle currencyStyle = workbook.createCellStyle();
		currencyStyle.setDataFormat(dataFormat.getFormat("#,##0.00 \"CHF\""));

		Sheet sheet = workbook.createSheet(sheetName);
		int rowIdx = 0;

		// === Kopfzeile ===
		Row headerRow = sheet.createRow(rowIdx++);
		headerRow.createCell(0).setCellValue("Mieter");
		headerRow.createCell(1).setCellValue("Boxen");
		headerRow.createCell(2).setCellValue("Kation");
		headerRow.createCell(3).setCellValue("Kaution");
		headerRow.createCell(4).setCellValue("Rück. Kaution");
		headerRow.createCell(5).setCellValue("Rück. Kaution");
		headerRow.createCell(6).setCellValue("Monatlich");
		headerRow.createCell(7).setCellValue("Dauer");
		headerRow.createCell(8).setCellValue("Jan.");
		headerRow.createCell(9).setCellValue("Feb.");
		headerRow.createCell(10).setCellValue("Mar.");
		headerRow.createCell(11).setCellValue("Apr.");
		headerRow.createCell(12).setCellValue("Mai.");
		headerRow.createCell(13).setCellValue("Jun.");
		headerRow.createCell(14).setCellValue("Jul.");
		headerRow.createCell(15).setCellValue("Aug.");
		headerRow.createCell(16).setCellValue("Sep.");
		headerRow.createCell(17).setCellValue("Okt.");
		headerRow.createCell(18).setCellValue("Nov.");
		headerRow.createCell(19).setCellValue("Dez.");
		headerRow.createCell(20).setCellValue("Total");
		headerRow.createCell(21).setCellValue("Status");

		for (int i = 0; i <= 21; i++) {
			headerRow.getCell(i).setCellStyle(headerStyle);
		}

		// === Zahlungen einfügen ===
		for (Payment p : payments) {
			Row row = sheet.createRow(rowIdx++);

			// Mieter

			Cell customer = row.createCell(0);
			Customer cust = p.getContract() != null ? p.getContract().getCustomer() : null;
			if (cust != null) {
				String name = cust.getName() != null ? cust.getName() : "";
				String vorname = cust.getVorname() != null ? cust.getVorname() : "";
				String strasse = cust.getStrasse() != null ? cust.getStrasse() : "";
				String plz = cust.getPlz() != null ? cust.getPlz() : "";
				String ort = cust.getOrt() != null ? cust.getOrt() : "";
				String address = String.format("%s %s, %s, %s %s", name, vorname, strasse, plz, ort).trim();
				customer.setCellValue(address);
			} else {
				customer.setCellValue("");
			}

			Cell boxen = row.createCell(1);
			boxen.setCellValue(p.getBoxNumbers() != null ? p.getBoxNumbers() : "");
			boxen.setCellStyle(currencyStyle);

			Cell dateKaution = row.createCell(2);
			if (p.getAktKautionDate() != null) {
				dateKaution.setCellValue(p.getAktKautionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			} else {
				dateKaution.setCellValue("");
			}

			Cell kaution = row.createCell(3);
			kaution.setCellValue(p.getAktKautionPrice() != null ? p.getAktKautionPrice().doubleValue() : 0.0);
			kaution.setCellStyle(currencyStyle);

			Cell dateRueckKaution = row.createCell(4);
			if (p.getRetourKautionDate() != null) {
				dateRueckKaution.setCellValue(p.getRetourKautionDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			} else {
				dateRueckKaution.setCellValue("");
			}

			Cell rueckKaution = row.createCell(5);
			rueckKaution
					.setCellValue(p.getRetourKautionPrice() != null ? p.getRetourKautionPrice().doubleValue() : 0.0);
			rueckKaution.setCellStyle(currencyStyle);

			Cell monPrice = row.createCell(6);
			monPrice.setCellValue(p.getAktBruttoPrice() != null ? p.getAktBruttoPrice().doubleValue() : 0.0);
			monPrice.setCellStyle(currencyStyle);

			Cell info = row.createCell(7);
			Contract con = p.getContract() != null ? p.getContract() : null;

			if (con != null) {
				String startDate = con.getStartDate() != null ? con.getStartDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: "";
				String endDate = con.getEndDate() != null ? con.getEndDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "";
				String startEnd = String.format("Laufzeit: %s - %s", startDate, endDate).trim();
				info.setCellValue(startEnd);
			} else {
				info.setCellValue("");
			}

			// Monatswerte
			Cell jan = row.createCell(8);
			Cell feb = row.createCell(9);
			Cell mar = row.createCell(10);
			Cell apr = row.createCell(11);
			Cell mai = row.createCell(12);
			Cell jun = row.createCell(13);
			Cell jul = row.createCell(14);
			Cell aug = row.createCell(15);
			Cell sep = row.createCell(16);
			Cell okt = row.createCell(17);
			Cell nov = row.createCell(18);
			Cell dez = row.createCell(19);

			Double bruttoJanPrice = 0.0;
			Double bruttoFebPrice = 0.0;
			Double bruttoMarPrice = 0.0;
			Double bruttoAprPrice = 0.0;
			Double bruttoMaiPrice = 0.0;
			Double bruttoJunPrice = 0.0;
			Double bruttoJulPrice = 0.0;
			Double bruttoAugPrice = 0.0;
			Double bruttoSepPrice = 0.0;
			Double bruttoOktPrice = 0.0;
			Double bruttoNovPrice = 0.0;
			Double bruttoDezPrice = 0.0;

			if (p.getPaymentDatePrice() != null && !p.getPaymentDatePrice().isEmpty()) {
				PaymentDatePrice firstPrice = p.getPaymentDatePrice().iterator().next();
				if (firstPrice.getBruttoJanPrice() != null) {
					bruttoJanPrice = firstPrice.getBruttoJanPrice().doubleValue();
				}
				if (firstPrice.getBruttoFebPrice() != null) {
					bruttoFebPrice = firstPrice.getBruttoFebPrice().doubleValue();
				}
				if (firstPrice.getBruttoMarPrice() != null) {
					bruttoMarPrice = firstPrice.getBruttoMarPrice().doubleValue();
				}
				if (firstPrice.getBruttoAprPrice() != null) {
					bruttoAprPrice = firstPrice.getBruttoAprPrice().doubleValue();
				}
				if (firstPrice.getBruttoMaiPrice() != null) {
					bruttoMaiPrice = firstPrice.getBruttoMaiPrice().doubleValue();
				}
				if (firstPrice.getBruttoJunPrice() != null) {
					bruttoJunPrice = firstPrice.getBruttoJunPrice().doubleValue();
				}
				if (firstPrice.getBruttoJulPrice() != null) {
					bruttoJulPrice = firstPrice.getBruttoJulPrice().doubleValue();
				}
				if (firstPrice.getBruttoAugPrice() != null) {
					bruttoAugPrice = firstPrice.getBruttoAugPrice().doubleValue();
				}
				if (firstPrice.getBruttoSepPrice() != null) {
					bruttoSepPrice = firstPrice.getBruttoSepPrice().doubleValue();
				}
				if (firstPrice.getBruttoOctPrice() != null) {
					bruttoOktPrice = firstPrice.getBruttoOctPrice().doubleValue();
				}
				if (firstPrice.getBruttoNovPrice() != null) {
					bruttoNovPrice = firstPrice.getBruttoNovPrice().doubleValue();
				}
				if (firstPrice.getBruttoDecPrice() != null) {
					bruttoDezPrice = firstPrice.getBruttoDecPrice().doubleValue();
				}
			}

			jan.setCellValue(bruttoJanPrice);
			jan.setCellStyle(currencyStyle);

			feb.setCellValue(bruttoFebPrice);
			feb.setCellStyle(currencyStyle);

			mar.setCellValue(bruttoMarPrice);
			mar.setCellStyle(currencyStyle);

			apr.setCellValue(bruttoAprPrice);
			apr.setCellStyle(currencyStyle);

			mai.setCellValue(bruttoMaiPrice);
			mai.setCellStyle(currencyStyle);

			jun.setCellValue(bruttoJunPrice);
			jun.setCellStyle(currencyStyle);

			jul.setCellValue(bruttoJulPrice);
			jul.setCellStyle(currencyStyle);

			aug.setCellValue(bruttoAugPrice);
			aug.setCellStyle(currencyStyle);

			sep.setCellValue(bruttoSepPrice);
			sep.setCellStyle(currencyStyle);

			okt.setCellValue(bruttoOktPrice);
			okt.setCellStyle(currencyStyle);

			nov.setCellValue(bruttoNovPrice);
			nov.setCellStyle(currencyStyle);

			dez.setCellValue(bruttoDezPrice);
			dez.setCellStyle(currencyStyle);

			// Total
			Cell total = row.createCell(20);
			Double totalPrice = 0.0;
			if (p.getSumBruttoPrice() != null) {
				totalPrice = p.getSumBruttoPrice().doubleValue();
			}
			total.setCellValue(totalPrice);
			total.setCellStyle(currencyStyle);

			// Status
			Cell status = row.createCell(21);
			String aktStatus = "";
			if (p.getStatus() != null) {
				aktStatus = p.getStatus();
			}
			status.setCellValue(aktStatus);
			status.setCellStyle(currencyStyle);

		}
		// === Spaltenbreite anpassen ===
		for (int i = 0; i <= 21; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	protected void createSheetWithElectricPeriods(Workbook workbook, String sheetName, List<ElectricPeriod> list) {
		Sheet sheet = workbook.createSheet(sheetName);
		DataFormat dataFormat = workbook.createDataFormat();

		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		CellStyle wrapStyle = workbook.createCellStyle();
		wrapStyle.setWrapText(true); // Zeilenumbruch aktivieren

		CellStyle currencyStyle = workbook.createCellStyle();
		currencyStyle.setDataFormat(dataFormat.getFormat("#,##0.00 \"CHF\""));

		// Kopfzeile
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Zähler Nr.");
		headerRow.createCell(1).setCellValue("Mieter");
		headerRow.createCell(2).setCellValue("Verbrauch [kWatt]");
		headerRow.createCell(3).setCellValue("Periode");
		headerRow.createCell(4).setCellValue("Brutto Stromkosten");
		headerRow.createCell(5).setCellValue("Bezahlt am");
		headerRow.createCell(6).setCellValue("Status");
		headerRow.createCell(7).setCellValue("Bemerkungen");

		for (int i = 0; i <= 7; i++) {
			headerRow.getCell(i).setCellStyle(headerStyle);
		}

		// Datenzeilen
		int rowIdx = 1;
		for (ElectricPeriod ep : list) {
			Row row = sheet.createRow(rowIdx++);
			row.createCell(0)
					.setCellValue(ep.getElectricMeter().getElectricMeterNumber() != null
							? ep.getElectricMeter().getElectricMeterNumber().toString()
							: "");
			row.createCell(1).setCellValue(ep.getPrintAnschrift() != null ? ep.getPrintAnschrift() : "-");
			row.createCell(2).setCellValue(ep.getDiffKwatt() != null ? ep.getDiffKwatt() : 0);

			if (ep.getZaehlerFromPeriode() != null && ep.getZaehlerToPeriode() != null) {
				String fromDate = ep.getZaehlerFromPeriode() != null ? ep.getZaehlerFromPeriode().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: "";
				String toDate = ep.getZaehlerToPeriode() != null ? ep.getZaehlerToPeriode().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: "";
				String fromToDate = String.format("%s - %s", fromDate, toDate).trim();
				row.createCell(3).setCellValue(fromToDate);
			} else {
				row.createCell(3).setCellValue("");
			}

			Cell priceCell = row.createCell(4);
			priceCell.setCellValue(ep.getStromPriceBrutto() != null ? ep.getStromPriceBrutto().doubleValue() : 0.0);
			priceCell.setCellStyle(currencyStyle);

			String eingangZahlung = ep.getZahlungEingegangen() != null ? ep.getZahlungEingegangen().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
					: "";
			row.createCell(5).setCellValue(eingangZahlung);

			row.createCell(6).setCellValue(ep.getStatusText() != null ? ep.getStatusText() : "");

			Cell bemerkungCell = row.createCell(7);
			bemerkungCell.setCellValue(ep.getBemerkungen() != null ? ep.getBemerkungen() : "");
			bemerkungCell.setCellStyle(wrapStyle);

		}

		for (int i = 0; i <= 7; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	protected void createSheetWithAdditionalCosts(Workbook workbook, String sheetName,
			List<AdditionalCosts> costsList) {

		Sheet sheet = workbook.createSheet(sheetName);

		// Header-Style
		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);

		// Währungsstil für CHF
		CellStyle currencyStyle = workbook.createCellStyle();
		DataFormat dataFormat = workbook.createDataFormat();
		currencyStyle.setDataFormat(dataFormat.getFormat("#,##0.00 \"CHF\""));


		// Header-Zeile
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Mieter");
		header.createCell(1).setCellValue("Rechnungsart");
		header.createCell(2).setCellValue("Datum Schaden");
		header.createCell(3).setCellValue("Brutto Betrag");
		header.createCell(4).setCellValue("Status");
		header.createCell(5).setCellValue("Text");

		for (int i = 0; i <= 5; i++) {
			header.getCell(i).setCellStyle(headerStyle);
		}

		// Datenzeilen
		int rowNum = 1;
		for (AdditionalCosts cost : costsList) {
			Row row = sheet.createRow(rowNum++);
			
			
			Cell customer = row.createCell(0);
			Customer cust = cost.getContract() != null ? cost.getContract().getCustomer() : null;
			if (cust != null) {
				String name = cust.getName() != null ? cust.getName() : "";
				String vorname = cust.getVorname() != null ? cust.getVorname() : "";
				String strasse = cust.getStrasse() != null ? cust.getStrasse() : "";
				String plz = cust.getPlz() != null ? cust.getPlz() : "";
				String ort = cust.getOrt() != null ? cust.getOrt() : "";
				String address = String.format("%s %s, %s, %s %s", name, vorname, strasse, plz, ort).trim();
				customer.setCellValue(address);
			} else {
				customer.setCellValue("");
			}

			row.createCell(1).setCellValue(cost.getRechnungsart() != null ? cost.getRechnungsart() : "");

			if (cost.getCreatedAt() != null) {
				String billingDate = cost.getCreatedAt() != null ? cost.getCreatedAt().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
						: "";
				String billingDateFormated = String.format("%s", billingDate).trim();
				row.createCell(2).setCellValue(billingDateFormated);
			} else {
				row.createCell(2).setCellValue("");
			}
			
			
			
			Cell amountCell = row.createCell(3);
			amountCell.setCellValue(cost.getSumBruttoPrice() != null ? cost.getSumBruttoPrice().doubleValue() : 0.0);
			amountCell.setCellStyle(currencyStyle);

			Cell remarkCell = row.createCell(4);
			remarkCell.setCellValue(cost.getStatusText() != null ? cost.getStatusText() : "");
			
			row.createCell(5).setCellValue(cost.getTitle() != null ? cost.getTitle() : "");
		}
	}

	protected List<Customer> getCustomerByStatusText(final String statusText) {
		return customerRepository.findByStatusText(statusText);
	}

	protected List<PaymentDatePrice> getPaymenDatePricetByTenant(final String jahr) {
		return paymenteDatePriceRepository.findByYear(jahr);
	}

	protected List<Payment> getPaymenByTenant(final String jahr) {
		return paymentRepository.findByYear(jahr);
	}

	public File loadOutputFile(String path) throws IOException {
		// String currentDirectory = System.getProperty("user.dir");
		// logger.info("Aktueller Pfad: " +currentDirectory);
		// String absolutePath = currentDirectory + File.separator + path;
		logger.info("Erkannter Pfad loadOutputFile: " + path);
		return new File(path);
	}

	public InputStream loadClassPathResource(String path) throws IOException {
		Resource resource = new ClassPathResource(path);
		logger.info("Erkannter Pfad loadClassPathResource: " + resource.getURL().getPath());
		return resource.getInputStream();
	}

	public String generateQRReference() {
		// Länge der Zufallszahl
		int randomPartLength = 10; // Zufallszahl ist 10-stellig
		int totalLength = 26; // QR-Referenz ohne Prüfziffer ist 26-stellig

		// Zufallszahl generieren
		String randomPart = generateRandomNumber(randomPartLength);
		// logger.info("randomPart: " + randomPart);

		// Restliche Stellen mit Nullen auffüllen
		String paddedPart = String.format("%0" + (totalLength - randomPartLength) + "d%s", 0, randomPart);
		// logger.info("paddedPart: " + paddedPart);

		// Prüfziffer berechnen
		int checksum = mod10(paddedPart);
		// logger.info("checksum: " + checksum);

		// Vollständige Referenz (Rohreferenz + Prüfziffer)
		String finalReference = paddedPart + checksum;
		logger.info("finalReference: " + finalReference);

		// Validierung der finalen Referenz (muss 27 Zeichen haben)
		if (finalReference.length() != 27) {
			throw new IllegalArgumentException("Die generierte QR-Referenz ist ungültig: " + finalReference);
		}

		return finalReference;
	}

	private String generateRandomNumber(int length) {
		Random random = new Random();
		StringBuilder number = new StringBuilder();

		for (int i = 0; i < length; i++) {
			number.append(random.nextInt(10)); // Zufällige Ziffer (0–9)
		}

		return number.toString();
	}

	private static int mod10(String input) {

		// Tabelle für die Modulo-10-Berechnung
		int[] table = { 0, 9, 4, 6, 8, 2, 7, 1, 3, 5 };
		int carry = 0;

		// Iteriere durch jede Ziffer des Strings
		for (int i = 0; i < input.length(); i++) {
			int digit = Character.getNumericValue(input.charAt(i));
			carry = table[(carry + digit) % 10];
		}

		// Berechne die Prüfziffer
		int checksum = (10 - carry) % 10;

		return checksum;
	}

}
