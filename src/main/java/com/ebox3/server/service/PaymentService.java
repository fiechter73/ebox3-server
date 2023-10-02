package com.ebox3.server.service;


import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.PaymentDTO;
import com.ebox3.server.model.dto.PaymentDatePriceDTO;

public interface PaymentService {

	public Long copyRecord(String year, PaymentDTO paymentDTO);

	public PaymentDatePriceDTO findById(Long id);

	public PaymentDatePriceDTO create(Long id, PaymentDatePriceDTO paymentDatePriceDTO);

	public Long delete(Long id) throws ResourceNotFoundException;

	public PaymentDTO sumPriceByYear(String year);

	public PaymentDTO sumPriceByMonth(String year);

	public Iterable<PaymentDTO> findByYear(String year);

	public PaymentDatePriceDTO update(Long id, PaymentDatePriceDTO paymentDatePriceDTO);

	public PaymentDTO clearPayment(Long id, PaymentDTO paymentDTO);
}
