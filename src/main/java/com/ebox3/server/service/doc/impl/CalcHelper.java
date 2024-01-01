package com.ebox3.server.service.doc.impl;

import static java.lang.Long.valueOf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

}
