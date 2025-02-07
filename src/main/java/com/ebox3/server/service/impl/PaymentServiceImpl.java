package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Payment;
import com.ebox3.server.model.PaymentDatePrice;
import com.ebox3.server.model.dto.PaymentDTO;
import com.ebox3.server.model.dto.PaymentDatePriceDTO;
import com.ebox3.server.repo.PaymentDatePriceRepository;
import com.ebox3.server.repo.PaymentRepository;
import com.ebox3.server.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PaymentDatePriceRepository paymentDatePriceRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Long copyRecord(String year, PaymentDTO paymentDTO) {
		if (!paymentRepository.existsById(paymentDTO.getId())) {
			throw new ResourceNotFoundException("Payment " + paymentDTO.getId() + " not found");
		}
		List<Payment> list = paymentRepository.findByIdAndYear(paymentDTO.getId(), year);

		if (list.size() > 1) {
			new ResourceNotFoundException(
					String.format("Mehere Ressourcen gefunden, sollte aber nur eine Ressource haben!"));
		} else {
			Payment newPayment = new Payment();
			Payment payment = list.get(0);
			if (!payment.isCopyFlag()) {
				Long newJahr = Long.parseLong(payment.getJahr()) + 1;
				newPayment.setJahr(newJahr.toString());
				newPayment.setContractDetails(payment.getContractDetails());
				newPayment.setBoxNumbers(payment.getBoxNumbers());
				newPayment.setAktBruttoPrice(payment.getAktBruttoPrice());
				newPayment.setAktKautionPrice(payment.getAktKautionPrice());
				newPayment.setAktKautionDate(payment.getAktKautionDate());
				newPayment.setLastKautionPrice(payment.getLastKautionPrice());
				newPayment.setLastBruttoPrice(payment.getLastBruttoPrice());
				newPayment.setStatus(payment.getStatus());
				newPayment.setContract(payment.getContract());
				paymentRepository.save(newPayment);
				payment.setCopyFlag(true);
				paymentRepository.save(payment);
			}
		}
		return paymentDTO.getId();
	}

	@Override
	public PaymentDatePriceDTO findById(Long id) {
		PaymentDatePriceDTO pdpDTO = new PaymentDatePriceDTO();
		PaymentDatePrice paymentDatePrice = paymentDatePriceRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Payment Date Price id %d not found", id)));
		mapper.typeMap(PaymentDatePrice.class, PaymentDatePriceDTO.class).addMappings(mapper -> {
		}).map(paymentDatePrice, pdpDTO);

		pdpDTO.setAktBruttoPrice(paymentDatePrice.getPayment().getAktBruttoPrice());
		pdpDTO.setAktKautionDate(paymentDatePrice.getPayment().getAktKautionDate());
		pdpDTO.setAktKautionPrice(paymentDatePrice.getPayment().getAktKautionPrice());
		pdpDTO.setAktKautionDate(paymentDatePrice.getPayment().getAktKautionDate());
		pdpDTO.setRetourKautionPrice(paymentDatePrice.getPayment().getRetourKautionPrice());
		pdpDTO.setRetourKautionDate(paymentDatePrice.getPayment().getRetourKautionDate());
		pdpDTO.setBoxNumbers(paymentDatePrice.getPayment().getBoxNumbers());
		pdpDTO.setContractDetails(paymentDatePrice.getPayment().getContractDetails());
		pdpDTO.setCopyFlag(paymentDatePrice.getPayment().isCopyFlag());
		pdpDTO.setLastBruttoPrice(paymentDatePrice.getPayment().getLastBruttoPrice());
		pdpDTO.setLastKautionPrice(paymentDatePrice.getPayment().getLastKautionPrice());
		pdpDTO.setStatus(paymentDatePrice.getPayment().getStatus());
		pdpDTO.setSumBruttoPrice(paymentDatePrice.getPayment().getSumBruttoPrice());
		pdpDTO.setTerminateDate(paymentDatePrice.getPayment().getTerminateDate());
		return pdpDTO;
	}

	@Override
	public PaymentDatePriceDTO create(Long id, PaymentDatePriceDTO paymentDatePriceDTO) {
		return paymentRepository.findById(id).map(payment -> {
			PaymentDatePrice pdp = mapper.map(paymentDatePriceDTO, PaymentDatePrice.class);
			pdp.setPayment(payment);
			paymentDatePriceRepository.save(pdp);
			return mapper.map(pdp, PaymentDatePriceDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("PaymentId " + id + " not found"));
	}

	@Override
	public Long delete(Long id) throws ResourceNotFoundException {
		return paymentDatePriceRepository.findById(id).map(paymentDatePrice -> {
			Payment payment = paymentDatePrice.getPayment();
			paymentDatePriceRepository.delete(paymentDatePrice);
			payment.setSumBruttoPrice(calcSum(payment));
			paymentRepository.save(payment);
			return id;
		}).orElseThrow(() -> new ResourceNotFoundException("PaymentDatePrice Object with id: " + id + " not found"));

	}

	@Override
	public PaymentDTO sumPriceByYear(String year) {

		List<Payment> list = paymentRepository.findByYear(year);

		PaymentDTO dto = new PaymentDTO();

		dto.setSumBruttoPrice(list.stream() // sumBruttoPrice
				.filter(pay -> pay != null && pay.getSumBruttoPrice() != null).mapToDouble(Payment::getSumBruttoPrice)
				.sum());

		dto.setAktKautionPrice(list.stream() // aktKautionPrice
				.filter(pay -> pay != null && pay.getAktKautionPrice() != null).mapToDouble(Payment::getAktKautionPrice)
				.sum());

		dto.setRetourKautionPrice(list.stream() // retourKautionPrice
				.filter(pay -> pay != null && pay.getRetourKautionPrice() != null)
				.mapToDouble(Payment::getRetourKautionPrice).sum());

		dto.setAktBruttoPrice(list.stream() // aktBruttoPrice
				.filter(pay -> pay != null && pay.getAktBruttoPrice() != null).mapToDouble(Payment::getAktBruttoPrice)
				.sum());

		return dto;
	}

	@Override
	public PaymentDTO sumPriceByMonth(String year) {

		List<PaymentDatePrice> list = paymentDatePriceRepository.findByYear(year);

		PaymentDTO payDTO = new PaymentDTO();

		payDTO.setSumJanBrutto(list.stream() // Jan.
				.filter(pdp -> pdp != null && pdp.getBruttoJanPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoJanPrice).sum());

		payDTO.setSumFebBrutto(list.stream() // Feb.
				.filter(pdp -> pdp != null && pdp.getBruttoFebPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoFebPrice).sum());

		payDTO.setSumMarBrutto(list.stream() // Mar.
				.filter(pdp -> pdp != null && pdp.getBruttoMarPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoMarPrice).sum());

		payDTO.setSumAprBrutto(list.stream() // Apr.
				.filter(pdp -> pdp != null && pdp.getBruttoAprPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoAprPrice).sum());

		payDTO.setSumMaiBrutto(list.stream() // Mai.
				.filter(pdp -> pdp != null && pdp.getBruttoMaiPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoMaiPrice).sum());

		payDTO.setSumJunBrutto(list.stream() // Jun.
				.filter(pdp -> pdp != null && pdp.getBruttoJunPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoJunPrice).sum());

		payDTO.setSumJulBrutto(list.stream() // Jul.
				.filter(pdp -> pdp != null && pdp.getBruttoJulPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoJulPrice).sum());

		payDTO.setSumAugBrutto(list.stream() // Aug.
				.filter(pdp -> pdp != null && pdp.getBruttoAugPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoAugPrice).sum());

		payDTO.setSumSepBrutto(list.stream() // Sep.
				.filter(pdp -> pdp != null && pdp.getBruttoSepPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoSepPrice).sum());

		payDTO.setSumOctBrutto(list.stream() // Oct.
				.filter(pdp -> pdp != null && pdp.getBruttoOctPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoOctPrice).sum());

		payDTO.setSumNovBrutto(list.stream() // Nov.
				.filter(pdp -> pdp != null && pdp.getBruttoNovPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoNovPrice).sum());

		payDTO.setSumDecBrutto(list.stream() // Dec.
				.filter(pdp -> pdp != null && pdp.getBruttoDecPrice() != null)
				.mapToDouble(PaymentDatePrice::getBruttoDecPrice).sum());

		payDTO.setJahr(list.get(0).getPayment().getJahr() != null ? list.get(0).getPayment().getJahr() : "");

		return payDTO;

	}

	@Override
	public Iterable<PaymentDTO> findByYear(String year) {
		List<Payment> list = paymentRepository.findByYear(year);

		List<PaymentDTO> newList = new ArrayList<PaymentDTO>();

		list.forEach(payment -> {
			PaymentDTO dto = new PaymentDTO();
			dto.setId(payment.getId());
			dto.setJahr(payment.getJahr());
			dto.setStatus(payment.getStatus());
			dto.setAktKautionPrice(payment.getAktKautionPrice());
			dto.setAktKautionDate(payment.getAktKautionDate());
			dto.setRetourKautionDate(payment.getRetourKautionDate());
			dto.setRetourKautionPrice(payment.getRetourKautionPrice());
			dto.setLastKautionPrice(payment.getLastKautionPrice());
			dto.setSumBruttoPrice(payment.getSumBruttoPrice());
			dto.setCustomerName(payment.getContract().getCustomer().getAnrede() + " "
					+ payment.getContract().getCustomer().getVorname() + " "
					+ payment.getContract().getCustomer().getName());
			dto.setCustomerAddress(payment.getContract().getCustomer().getStrasse() + " "
					+ payment.getContract().getCustomer().getPlz() + " "
					+ payment.getContract().getCustomer().getOrt() + " Tel: " 
			        + payment.getContract().getCustomer().getTel1());
	
			dto.setCustomerCompany(payment.getContract().getCustomer().getFirmenName() != null
					? payment.getContract().getCustomer().getFirmenName()
					: null);
			dto.setCustomerCompanyAddress(payment.getContract().getCustomer().getFirmenAnschrift() != null
					? payment.getContract().getCustomer().getFirmenAnschrift()
					: null);
			dto.setTerminateDate(payment.getTerminateDate());
			dto.setAktBruttoPrice(payment.getAktBruttoPrice());
			dto.setLastBruttoPrice(payment.getLastBruttoPrice());
			dto.setContractDetails(payment.getContractDetails());
			dto.setBoxNumbers(payment.getBoxNumbers());

			List<PaymentDatePriceDTO> pdpListDTO = payment.getPaymentDatePrice().stream()
					.sorted(Comparator.comparingLong(PaymentDatePrice::getId))
					.map(pdp -> mapper.map(pdp, PaymentDatePriceDTO.class)).collect(Collectors.toList());

			dto.setPaymentDatePrice(pdpListDTO);
			dto.setCopyFlag(payment.isCopyFlag());
			dto.setLastName(payment.getContract().getCustomer().getName());
			newList.add(dto);
		});
		List<PaymentDTO> itemList = newList.stream().sorted(Comparator.comparing(PaymentDTO::getLastName))
				.collect(Collectors.toList());
		return itemList;
	}

	@Override
	public PaymentDatePriceDTO update(Long id, PaymentDatePriceDTO paymentDatePriceDTO) {
		if (!paymentDatePriceRepository.existsById(id)) {
			throw new ResourceNotFoundException("PaymentDatePrice id " + id + " not found");
		}

		return paymentDatePriceRepository.findById(id).map(paymentDatePrice -> {

			paymentDatePrice.setBruttoJanPrice(paymentDatePriceDTO.getBruttoJanPrice());
			paymentDatePrice.setBruttoJanDate(paymentDatePriceDTO.getBruttoJanDate());
			paymentDatePrice.setBruttoFebPrice(paymentDatePriceDTO.getBruttoFebPrice());
			paymentDatePrice.setBruttoFebDate(paymentDatePriceDTO.getBruttoFebDate());
			paymentDatePrice.setBruttoMarPrice(paymentDatePriceDTO.getBruttoMarPrice());
			paymentDatePrice.setBruttoMarDate(paymentDatePriceDTO.getBruttoMarDate());
			paymentDatePrice.setBruttoAprPrice(paymentDatePriceDTO.getBruttoAprPrice());
			paymentDatePrice.setBruttoAprDate(paymentDatePriceDTO.getBruttoAprDate());
			paymentDatePrice.setBruttoMaiPrice(paymentDatePriceDTO.getBruttoMaiPrice());
			paymentDatePrice.setBruttoMaiDate(paymentDatePriceDTO.getBruttoMaiDate());
			paymentDatePrice.setBruttoJunPrice(paymentDatePriceDTO.getBruttoJunPrice());
			paymentDatePrice.setBruttoJunDate(paymentDatePriceDTO.getBruttoJunDate());
			paymentDatePrice.setBruttoJulPrice(paymentDatePriceDTO.getBruttoJulPrice());
			paymentDatePrice.setBruttoJulDate(paymentDatePriceDTO.getBruttoJulDate());
			paymentDatePrice.setBruttoAugPrice(paymentDatePriceDTO.getBruttoAugPrice());
			paymentDatePrice.setBruttoAugDate(paymentDatePriceDTO.getBruttoAugDate());
			paymentDatePrice.setBruttoSepPrice(paymentDatePriceDTO.getBruttoSepPrice());
			paymentDatePrice.setBruttoSepDate(paymentDatePriceDTO.getBruttoSepDate());
			paymentDatePrice.setBruttoOctPrice(paymentDatePriceDTO.getBruttoOctPrice());
			paymentDatePrice.setBruttoOctDate(paymentDatePriceDTO.getBruttoOctDate());
			paymentDatePrice.setBruttoNovPrice(paymentDatePriceDTO.getBruttoNovPrice());
			paymentDatePrice.setBruttoNovDate(paymentDatePriceDTO.getBruttoNovDate());
			paymentDatePrice.setBruttoDecPrice(paymentDatePriceDTO.getBruttoDecPrice());
			paymentDatePrice.setBruttoDecDate(paymentDatePriceDTO.getBruttoDecDate());
			paymentDatePrice.setExcludeInPaymentList(paymentDatePriceDTO.isExcludeInPaymentList());

			paymentDatePriceRepository.save(paymentDatePrice);

			paymentDatePrice.getPayment().setAktKautionDate(paymentDatePriceDTO.getAktKautionDate());
			paymentDatePrice.getPayment().setAktKautionPrice(paymentDatePriceDTO.getAktKautionPrice());
			paymentDatePrice.getPayment().setRetourKautionDate(paymentDatePriceDTO.getRetourKautionDate());
			paymentDatePrice.getPayment().setRetourKautionPrice(paymentDatePriceDTO.getRetourKautionPrice());
			paymentDatePrice.getPayment().setSumBruttoPrice(calcSum(paymentDatePrice.getPayment()));

			PaymentDatePriceDTO val = mapper.map(paymentDatePriceRepository.save(paymentDatePrice),
					PaymentDatePriceDTO.class);

			val.setAktKautionDate(paymentDatePrice.getPayment().getAktKautionDate());
			val.setAktKautionPrice(paymentDatePrice.getPayment().getAktKautionPrice());
			val.setRetourKautionDate(paymentDatePrice.getPayment().getRetourKautionDate());
			val.setRetourKautionPrice(paymentDatePrice.getPayment().getRetourKautionPrice());
			val.setAktBruttoPrice(paymentDatePrice.getPayment().getAktBruttoPrice());
			val.setLastBruttoPrice(paymentDatePrice.getPayment().getLastBruttoPrice());
			val.setStatus(paymentDatePrice.getPayment().getStatus());

			return val;

		}).orElseThrow(() -> new ResourceNotFoundException("PaymentDatePrice " + id + " not found"));
	}

	@Override
	public PaymentDTO clearPayment(Long id, PaymentDTO paymentDTO) {

		return paymentRepository.findById(id).map(payment -> {
			payment.setTerminateDate(paymentDTO.getTerminateDate());
			payment.setLastBruttoPrice(paymentDTO.getLastBruttoPrice());
			return mapper.map(paymentRepository.save(payment), PaymentDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("Payment " + id + " not found"));
	}

	private Double calcSum(Payment payment) {

		Double calcSum = 0D;

		calcSum = payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoJanPrice() != null ? x.getBruttoJanPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoFebPrice() != null ? x.getBruttoFebPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoMarPrice() != null ? x.getBruttoMarPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoAprPrice() != null ? x.getBruttoAprPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoMaiPrice() != null ? x.getBruttoMaiPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoJunPrice() != null ? x.getBruttoJunPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoJulPrice() != null ? x.getBruttoJulPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoAugPrice() != null ? x.getBruttoAugPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoSepPrice() != null ? x.getBruttoSepPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoOctPrice() != null ? x.getBruttoOctPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoNovPrice() != null ? x.getBruttoNovPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		calcSum += payment.getPaymentDatePrice().stream()
				.map(x -> x.getBruttoDecPrice() != null ? x.getBruttoDecPrice() : 0D)
				.collect(Collectors.summingDouble(Double::doubleValue));

		return calcSum;
	}

}
