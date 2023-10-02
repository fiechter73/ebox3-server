package com.ebox3.server.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebox3.server.doc.Util;
import com.ebox3.server.exception.ForbiddenException;
import com.ebox3.server.model.Contract;
import com.ebox3.server.model.ContractHistory;
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.Payment;


public class HelpFunctions {

	private final Log logger = LogFactory.getLog(getClass());

	public ContractHistory copyToHistory(Contract contract) {
		ContractHistory conHistory = new ContractHistory();
		ArrayList<Long> boxNrList = new ArrayList<Long>();
		ArrayList<Double> sumBoxList = new ArrayList<Double>();

		// Boxennummern
		Map<Object, List<Long>> eboxNumbers = contract.getEboxs().stream()
				.collect(Collectors.groupingBy(eBox -> eBox.getContract().getId(),
						Collectors.mapping(ebox -> ebox.getBoxNumber(), Collectors.toList())));

		// Summe
		Map<Long, Double> sum = contract.getEboxs().stream().collect(Collectors.groupingBy(Contract -> contract.getId(),
				Collectors.summingDouble(Ebox::getTotalPriceBrutto)));

		if (eboxNumbers.get(contract.getId()) != null) {
			boxNrList.addAll(eboxNumbers.get(contract.getId()));
		} else {
			boxNrList.add(0L);
		}

		if (sum.get(contract.getId()) != null) {
			sumBoxList.add(sum.get(contract.getId()));
		} else {
			sumBoxList.add(0D);
		}

		conHistory.setObjekteDetails(boxNrList.toString());
		conHistory.setTotalSumPriceBrutto(sumBoxList.stream().collect(Collectors.summingDouble(Double::doubleValue)));
		conHistory.setStartDate(contract.getStartDate());
		conHistory.setEndDate(contract.getEndDate());
		conHistory.setContract(contract);
		conHistory.setContractDetails(contract.getStatusText());
		conHistory.setStatusText(contract.getStatusText().contentEquals("Kündigung") ? "gekündigt" : "gebucht");
		conHistory.setTerminateDate(contract.getTerminateDate());
		return conHistory;
	}

	public ContractHistory copyToHistory(Ebox ebox) {
		ContractHistory conHistory = new ContractHistory();
		conHistory.setObjekteDetails(ebox.getBoxNumber().toString());
		conHistory.setTotalSumPriceBrutto(ebox.getTotalPriceBrutto().doubleValue());
		conHistory.setStartDate(ebox.getContract().getStartDate());
		conHistory.setEndDate(ebox.getContract().getEndDate());
		conHistory.setContract(ebox.getContract());
		conHistory.setContractDetails(ebox.getContract().getStatusText());
		conHistory.setStatusText(ebox.getStatusText());
		conHistory.setTerminateDate(ebox.getTerminateDate());
		return conHistory;
	}

	public Payment updatePaymentRecordByStartDate(Contract contract) throws Exception { // Anpassung Vertragsdatum

		Payment payment = new Payment();
		SimpleDateFormat formatDatum = new SimpleDateFormat("dd.MM.yyyy");
		List<Payment> list = new ArrayList<Payment>();
		// Prüfen ob bereiets ein Payment-Element besteht
		contract.getPayment().forEach(item -> {
			if (!item.isCopyFlag()) { // nur Verträge nehmen, die noch nicht kopiert wurden = nicht kopiert heisst
										// aktuelles Jahr
				list.add(item);
			}
		});

		if (list.size() == 1) {
			payment = list.get(0);
			// update
			List<Long> numbers = new ArrayList<Long>();
			contract.getEboxs().forEach(ebox -> {
				numbers.add(ebox.getBoxNumber());
			});
			payment.setContractDetails("Vertragsmutation Laufzeit: " + formatDatum.format(contract.getStartDate())
					+ " - " + formatDatum.format(contract.getEndDate()));
			payment.setStatus(payment.getStatus() + "Vertrag Box(en): " + numbers.toString()
					+ " Vertragsmutation Laufzeit per: " + formatDatum.format(contract.getStartDate()) + "\n");
			payment.setContract(contract);
		} else {
			// Es kann auch nicht mehr als ein aktiver Vertrag bestehen.
			logger.error("Error...Merhrere oder kein aktiver Vertrag vorhanden (Stufe Contract) !");
			throw new ForbiddenException("Error...Merhrere oder kein aktiver Vertrag vorhanden (Stufe Contract) !");
		}
		return payment;
	}

	public Payment updatePaymentRecordByContract(Contract contract) throws Exception {

		Payment payment = new Payment();
		SimpleDateFormat formatDatum = new SimpleDateFormat("dd-MM-yyyy");
		List<Payment> list = new ArrayList<Payment>();
		// Prüfen ob bereiets ein Payment-Element besteht
		contract.getPayment().forEach(item -> {
			if (!item.isCopyFlag()) { // nur Verträge nehmen, die noch nicht kopiert wurden = nicht kopiert heisst
										// aktuelles Jahr
				list.add(item);
			}
		});

		if (list.size() == 1) {
			payment = list.get(0);
			// update
			List<Long> numbers = new ArrayList<Long>();
			contract.getEboxs().forEach(ebox -> {
				numbers.add(ebox.getBoxNumber());
			});
			payment.setLastBruttoPrice(payment.getAktBruttoPrice()); // Alter Preis
			payment.setAktBruttoPrice(calculateTotalSum(contract)); // Aktueller Preis
			payment.setStatus(payment.getStatus() + "Vertrag Box(en): " + numbers.toString() + " update Preis alt: "
					+ payment.getLastBruttoPrice() + " auf Preis neu:  " + payment.getAktBruttoPrice() + "\n");
			payment.setContractDetails("Laufzeit: " + formatDatum.format(contract.getStartDate()) + " - "
					+ formatDatum.format(contract.getEndDate()));

			payment.setContract(contract);

		} else {
			// Es kann auch nicht mehr als ein aktiver Vertrag bestehen.
			logger.error("Error...Merhrere oder kein aktiver Vertrag vorhanden (Stufe Contract) !");
			throw new ForbiddenException("Error...Merhrere oder kein aktiver Vertrag vorhanden (Stufe Contract) !");
		}
		return payment;
	}

	public Payment finalizePaymentRecordByContract(Contract contract, Double retKaution) throws Exception {

		Payment payment = new Payment();
		SimpleDateFormat formatDatum = new SimpleDateFormat("dd-MM-yyyy");
		List<Payment> list = new ArrayList<Payment>();
		// Prüfen ob bereiets ein Payment-Element besteht
		contract.getPayment().forEach(item -> {
			if (!item.isCopyFlag()) { // nur Verträge nehmen, die noch nicht kopiert wurden = nicht kopiert heisst
										// aktuelles Jahr
				list.add(item);
			}
		});

		if (list.size() == 1) {
			payment = list.get(0);
			payment.setAktBruttoPrice(0D); // Preis kann auf 0.- CHF gesetzt werden
			payment.setLastKautionPrice(payment.getAktKautionPrice());
			payment.setRetourKautionPrice(retKaution);
			// payment.setAktKautionPrice(0D);
			// payment.setRetourKautionPrice(checkOutReturnPrice);

			payment.setRetourKautionDate(contract.getCheckOutDate()); // Kaution wurde zurückbezahlt.
			String datum = formatDatum.format(Util.getCurrentDate());
			payment.setStatus(payment.getStatus() + "Box(en) ausgebucht am: " + datum + "\n");
			payment.setContract(contract);
		} else {
			// Es kann auch nicht mehr als ein aktiver Vertrag bestehen.
			logger.error("Error...Merhrere oder kein aktiver Vertrag vorhanden (Stufe Contract) !");
			throw new ForbiddenException("Error...Merhrere oder kein aktiver Vertrag vorhanden (Stufe Contract) !");
		}
		return payment;
	}

	public Payment addPaymentRecordByContract(Contract contract, Date lastRentDay) throws Exception {

		Payment payment = new Payment();
		SimpleDateFormat formatDatum = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dateJahr = new SimpleDateFormat("yyyy");
		List<Payment> list = new ArrayList<Payment>();

		// Prüfen ob bereiets ein Payment-Element besteht
		contract.getPayment().forEach(p -> {
			if (!p.isCopyFlag()) { // nur Verträge nehmen, die noch nicht kopiert wurden = nicht kopiert heisst
									// aktuelles Jahr
				list.add(p);
			}
		});

		if (list.size() == 0) {
			// add new Payment Element
			payment.setJahr(dateJahr.format(contract.getStartDate()));
			List<Long> numbers = new ArrayList<Long>();
			contract.getEboxs().forEach(ebox -> {
				numbers.add(ebox.getBoxNumber());
			});

			payment.setStatus("Vertrag Box(en): " + numbers.toString() + " per:"
					+ formatDatum.format(contract.getStartDate()) + "\n");
			payment.setAktBruttoPrice(calculateTotalSum(contract));

			payment.setBoxNumbers(listOfBoxNumbersByContract(contract).stream().map(l -> l.toString())
					.collect(Collectors.joining(",")));

			payment.setContractDetails("Laufzeit: " + formatDatum.format(contract.getStartDate()) + " - "
					+ formatDatum.format(contract.getEndDate()));

			payment.setContract(contract);

		} else if (list.size() == 1) {
			payment = list.get(0);
			// update
			List<Long> numbers = new ArrayList<Long>();
			contract.getEboxs().forEach(ebox -> {
				numbers.add(ebox.getBoxNumber());
			});
			if (contract.getStatusText().equals("Kündigung")) {
				payment.setTerminateDate(contract.getTerminateDate());
				// String lastDay =
				// formatDatum.format(calcLastRentDay(contract.getTerminateDate(),
				// contract.getFrist()));
				String lastDay = formatDatum.format(lastRentDay);
				payment.setContractDetails("L.M. " + lastDay);
				payment.setLastBruttoPrice(calculateTotalSum(contract)); // Den früheren Preis setzen
				payment.setStatus(payment.getStatus() + "Kündigung Box(en): " + numbers.toString() + " Letzer Miettag: "
						+ lastDay + "\n");

			} else if (contract.getStatusText().equals("Vertrag")) {
				payment.setLastBruttoPrice(payment.getAktBruttoPrice()); // Alter Preis
				payment.setAktBruttoPrice(calculateTotalSum(contract)); // Aktueller Preis
				payment.setStatus("Vertrag Box(en): " + numbers.toString() + " per:"
						+ formatDatum.format(contract.getStartDate()) + "\n");
				payment.setBoxNumbers(listOfBoxNumbersByContract(contract).stream().map(l -> l.toString())
						.collect(Collectors.joining(",")));
				payment.setContractDetails("Laufzeit: " + formatDatum.format(contract.getStartDate()) + " - "
						+ formatDatum.format(contract.getEndDate()));
				payment.setJahr(dateJahr.format(contract.getStartDate()));

			}
			payment.setContract(contract);
		} else {
			// Es kann auch nicht mehr als ein aktiver Vertrag bestehen.
			logger.error("Error...Merhrere aktive Verträge vorhanden (Stufe Contract) !");
			throw new ForbiddenException("Error...Merhrere aktive Verträge vorhanden (Stufe Contract) !");
		}
		return payment;
	}

	public Payment addPaymentRecordByEbox(Ebox ebox) throws Exception {

		Payment payment = null;
		List<Payment> list = new ArrayList<Payment>();
		SimpleDateFormat formatDatum = new SimpleDateFormat("dd.MM.yyyy");

		// Prüfen ob bereiets ein Payment-Element besteht
		ebox.getContract().getPayment().forEach(item -> {
			if (!item.isCopyFlag()) { // nur Verträge nehmen, die noch nicht kopiert wurden = nicht kopiert heisst
										// aktuelles Jahr
				list.add(item);
			}
		});

		if (list.size() == 0) {
			// Falls eine Box hinzukommt oder gekündigt wird, so sollte immer bereits, ein
			// aktiver Vertrag bestehen. Daher wird dieser Fall nicht weiter behandelt.
			logger.debug("Es besteht kein aktiver Vertrag. Somit kein Eintrag möglich.");
			throw new ForbiddenException("Es besteht kein aktiver Vertrag. Somit kein Eintrag möglich.");

		} else if (list.size() == 1) {
			payment = list.get(0);
			// update
			Double newPrice = null;
			List<Long> newBoxCount = null;

			if (ebox.getStatusText().equals("gekündigt")) {
				payment.setTerminateDate(ebox.getTerminateDate());
				payment.setStatus(payment.getStatus() + " Kündigung Ebox - " + ebox.getBoxNumber() + " - per: "
						+ formatDatum.format(ebox.getTerminateDate()) + " (Letzer Miettag) " + "\n");
				newPrice = updateTotalSumOfBox(calculateTotalSum(ebox.getContract()), ebox.getTotalPriceBrutto(), true);
				newBoxCount = updateEBoxNumbers(listOfBoxNumbersByContract(ebox.getContract()), ebox.getBoxNumber(),
						true);

			} else if (ebox.getStatusText().equals("gebucht")) {
				payment.setStatus(payment.getStatus() + " Buchung Ebox - " + ebox.getBoxNumber() + " - per: "
						+ formatDatum.format(ebox.getStartDate()) + "\n");
				payment.setContractDetails(
						"Vertragsmutation Laufzeit: " + formatDatum.format(ebox.getContract().getStartDate()) + " - "
								+ formatDatum.format(ebox.getContract().getEndDate()));
				newPrice = calculateTotalSum(ebox.getContract());
				newBoxCount = listOfBoxNumbersByContract(ebox.getContract());
			}
			// payment.setJahr(formatJahr.format(ebox.getContract().getStartDate())); Das
			// Jahr bleibt auf dem Payment Objekt gleich. Es soll ja das aktive Payment
			// Element geupdated werden.
			payment.setLastBruttoPrice(payment.getAktBruttoPrice());// alter Preis
			payment.setAktBruttoPrice(newPrice); // neuer Preis
			payment.setBoxNumbers(newBoxCount.stream().map(l -> l.toString()).collect(Collectors.joining(",")));

		} else {
			// Es kann auch nicht mehr als ein aktiver Vertrag bestehen.
			logger.debug("Error...Merhrere aktive Verträge vorhanden (Stufe Contract) !");
			throw new ForbiddenException("Error...Merhrere aktive Verträge vorhanden (Stufe Contract) !");
		}

		return payment;
	}

	public Double calculateTotalSum(Contract contract) {
		ArrayList<Double> sumBoxList = new ArrayList<Double>();
		Map<Long, Double> sum = contract.getEboxs().stream().collect(Collectors.groupingBy(Contract -> contract.getId(),
				Collectors.summingDouble(Ebox::getTotalPriceBrutto)));

		if (sum.get(contract.getId()) != null) {
			sumBoxList.add(sum.get(contract.getId()));
		} else {
			sumBoxList.add(0D);
		}
		return sumBoxList.stream().collect(Collectors.summingDouble(Double::doubleValue));
	}

	public List<Long> listOfBoxNumbersByContract(Contract contract) {
		ArrayList<Long> boxNrList = new ArrayList<Long>();
		Map<Object, List<Long>> eboxNumbers = contract.getEboxs().stream()
				.collect(Collectors.groupingBy(eBox -> eBox.getContract().getId(),
						Collectors.mapping(ebox -> ebox.getBoxNumber(), Collectors.toList())));

		if (eboxNumbers.get(contract.getId()) != null) {
			boxNrList.addAll(eboxNumbers.get(contract.getId()));
		} else {
			boxNrList.add(0L);
		}
		return boxNrList.stream().sorted().collect(Collectors.toList());
	}

	public List<Long> updateEBoxNumbers(List<Long> actValues, Long modVale, boolean isRemoved) {
		if (isRemoved) {
			actValues.remove(modVale);
		} else {
			actValues.add(modVale);
		}
		return actValues;
	}

	public Double updateTotalSumOfBox(Double actTotalSum, Double actTotalBox, boolean isRemoved) {
		if (isRemoved) {
			actTotalSum = actTotalSum - actTotalBox;
		} else {
			actTotalSum = actTotalSum + actTotalBox;
		}
		return actTotalSum;
	}

	public Date calcLastRentDay(Date terminatedDate, String months) {

		if (months == null) {
			months = "3"; // default
		}

		LocalDate localDate = terminatedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate lcoalDateAddedMonths = localDate.plusMonths(Long.valueOf(months));

		// Last day of Month
		LocalDate newLocalDate = lcoalDateAddedMonths
				.withDayOfMonth(lcoalDateAddedMonths.getMonth().length(lcoalDateAddedMonths.isLeapYear()));
		return Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
