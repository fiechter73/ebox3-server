package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ebox3.server.exception.InternalServerException;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Customer;
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.Payment;

import com.ebox3.server.model.dto.AdditionalCostsDTO;
import com.ebox3.server.model.dto.ContractDTO;
import com.ebox3.server.model.dto.ContractHistoryDTO;
import com.ebox3.server.model.dto.CustomerDTO;
import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.model.dto.OverviewDTO;
import com.ebox3.server.model.dto.PaymentDTO;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.CustomerRepository;
import com.ebox3.server.service.CustomerService;

@Service
public class CustomerServiceImpl extends HelpFunctions implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	private ModelMapper mapper;

	public Iterable<Map<Long, Object>> getCustomerList() {
		List<Map<Long, Object>> res = customerRepository.customerSelection();
		return res.stream().distinct().collect(Collectors.toList());
	}
	
	public Iterable<Map<Long, Object>> getCustomerWithDismissalList() {
		List<Map<Long, Object>> res = customerRepository.customerSelectionWithDismissal();
		return res.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> getAllCustomers(String search, Boolean isCustomer, int page, int size)

	{
		Map<String, Object> response = new HashMap<>();
		try {
			List<CustomerDTO> listOfCustomers = new ArrayList<CustomerDTO>();
			Pageable requestedPage = PageRequest.of(page, size, Sort.by("name"));
			PagedListHolder<CustomerDTO> customersDTO = null;

			if (search == null) {
				Page<Customer> customers = null;
				if (isCustomer) {
					customers = customerRepository.findCustomersNotKündigungAndNotIntressent(requestedPage);
				} else {
					customers = customerRepository.findCustomerstKündigungAndIntressent(requestedPage);
				}
				customers.getContent().forEach(customer -> {
					CustomerDTO obj = mapper.map(customer, CustomerDTO.class);
					obj.setCountedBox(customerRepository.countEbox(customer.getId()));
					obj.setCountedContract(customerRepository.countContract(customer.getId()));
					listOfCustomers.add(obj);
				});

				listOfCustomers.stream().sorted(Comparator.comparing(CustomerDTO::getName)).distinct()
						.collect(Collectors.toList());

				response.put("customers", listOfCustomers);
				response.put("currentPage", customers.getNumber());
				response.put("totalItems", customers.getTotalElements());
				response.put("totalPages", customers.getTotalPages());

			} else {
				List<Customer> customers = customerRepository.findCustomerFullSearch(search);

//              Lasse ich zu Demozwecken im Code drin. Andere Art der Suche.
//				List<Customer> customers = customerRepository
//						.findByNameContainingIgnoreCaseOrVornameContainingIgnoreCaseOrStrasseContainingIgnoreCaseOrOrtContainingIgnoreCaseOrPlzContainingIgnoreCaseOrStatusTextContainingIgnoreCase(
//								search, search, search, search, search, search);

				customers.forEach(customer -> {
					CustomerDTO obj = mapper.map(customer, CustomerDTO.class);
					obj.setCountedBox(customerRepository.countEbox(customer.getId()));
					obj.setCountedContract(customerRepository.countContract(customer.getId()));
					listOfCustomers.add(obj);
				});

				List<CustomerDTO> listOfCustomerSorted = listOfCustomers.stream()
						.sorted(Comparator.comparing(CustomerDTO::getName)).collect(Collectors.toList());

				customersDTO = new PagedListHolder<CustomerDTO>(listOfCustomerSorted);
				customersDTO.setPageSize(size);
				customersDTO.setPage(page);

				response.put("customers", customersDTO.getPageList());
				response.put("currentPage", customersDTO.getPage());
				response.put("totalItems", customersDTO.getNrOfElements());
				response.put("totalPages", customersDTO.getPageCount());

			}

			return response;
		} catch (Exception e) {
			if (e instanceof InvalidDataAccessResourceUsageException) {
				// Hier wird bewusst ein leerer Response zurückgegeben
				// dies im Fall, dass Steuerzeichen FullSearch ohne
				// Paramenterwert verwendet wird; z.b. +/-/>/<
				return response;
			} else {
				throw new InternalServerException("Internal Server Error", e);
			}

		}

	}

	@Override
	public Map<String, Object> getAllCustomer(int page, int size) {

		try {
			List<CustomerDTO> listOfCustomers = new ArrayList<CustomerDTO>();
			Pageable requestedPage = PageRequest.of(page, size, Sort.by("name"));
			Page<Customer> customers = customerRepository.findAll(requestedPage);

			customers.getContent().forEach(customer -> {
				CustomerDTO obj = mapper.map(customer, CustomerDTO.class);
				obj.setCountedBox(customerRepository.countEbox(customer.getId()));
				obj.setCountedContract(customerRepository.countContract(customer.getId()));
				listOfCustomers.add(obj);
			});

			listOfCustomers.stream().sorted(Comparator.comparing(CustomerDTO::getName)).distinct()
					.collect(Collectors.toList());

			Map<String, Object> response = new HashMap<>();
			response.put("customers", listOfCustomers);
			response.put("currentPage", customers.getNumber());
			response.put("totalItems", customers.getTotalElements());

			return response;
		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Customer by id %d not found", id)));
		return mapper.map(customer, CustomerDTO.class);
	}

	@Override
	public Iterable<OverviewDTO> getOverview(Boolean value) {
		List<OverviewDTO> list = new ArrayList<OverviewDTO>();
		List<Customer> customers = null;
		if (value) {
			customers = customerRepository.findRetiredCustomers();
		} else {
			customers = customerRepository.findCustomers();
		}

		customers.forEach(customer -> {
			OverviewDTO ovDTO = mapper.map(customer, OverviewDTO.class);
			ovDTO.setBoxNumbers(customerRepository.boxNumbers(customer.getId()));
			list.add(ovDTO);
		});

		return list.stream()
				.filter(str -> str.getStatusTextCustomer().equals("Vertragsnehmer")
						|| str.getStatusTextCustomer().equals("Offertnehmer")
						|| str.getStatusTextCustomer().equals("Kündigung"))
				.sorted(Comparator.comparing(OverviewDTO::getName)).distinct().collect(Collectors.toList());

	}

	@Override
	public CustomerDTO getCustomerContractDetails(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Customer by id %d not found", id)));

		CustomerDTO cuDTO = mapper.map(customer, CustomerDTO.class);
		cuDTO.setSumBoxPrice(customerRepository.sumOfEboxGroupByCustomer(customer.getId()) != null
				? customerRepository.sumOfEboxGroupByCustomer(customer.getId())
				: 0D);
		cuDTO.setCountedBox(
				customerRepository.countEbox(customer.getId()) != null ? customerRepository.countEbox(customer.getId())
						: 0L);

		List<ContractDTO> listContract = new ArrayList<ContractDTO>();

		customer.getContracts().forEach(contract -> {
			if (contract.getCheckOutDate() == null) {
				ContractDTO coDTO = mapper.map(contract, ContractDTO.class);
				coDTO.setCustId(customer.getId());
				coDTO.setSumEbox(contractRepository.sumOfEboxGroupByContract(contract.getId()) != null
						? contractRepository.sumOfEboxGroupByContract(contract.getId())
						: 0D);

				List<AdditionalCostsDTO> addCostsDTO = contract.getAdditionalCosts().stream()
						.map(ada -> mapper.map(ada, AdditionalCostsDTO.class)).collect(Collectors.toList());

				List<ContractHistoryDTO> conHistoryDTO = contract.getContractHistory().stream()
						.map(cth -> mapper.map(cth, ContractHistoryDTO.class)).collect(Collectors.toList());

				coDTO.setAdditionalCosts(new ArrayList<>(addCostsDTO));
				coDTO.setContractHistory(new ArrayList<>(conHistoryDTO));
				coDTO.setFristDate(
						!contract.getFrist().equals("keine") ? calcLastRentDay(new Date(), contract.getFrist())
								: new Date());
				// Kündigungsfrist als Datum
				// basierend auf dem aktuellen
				// Datum
				// berechnet
				coDTO.setMinDurationDate(
						calcLastRentDay(contract.getStartDate(), contract.getMinDuration().toString())); // Mindestmietdauer
																											// als
																											// Datum

				coDTO.setPayment(contract.getPayment().stream().sorted(Comparator.comparing(Payment::getJahr))
						.map(pay -> mapper.map(pay, PaymentDTO.class)).collect(Collectors.toList()));

				coDTO.setEboxs(contract.getEboxs().stream().sorted(Comparator.comparingLong(Ebox::getBoxNumber))
						.map(ebox -> mapper.map(ebox, EboxDTO.class)).collect(Collectors.toList()));
				listContract.add(coDTO);
			}

		});

		cuDTO.setContracts(listContract.stream().sorted(Comparator.comparingLong(ContractDTO::getId))
				.collect(Collectors.toList()));

		return cuDTO;
	}

	@Override
	public CustomerDTO create(CustomerDTO customerDTO) {
		Customer customer = customerRepository.save(mapper.map(customerDTO, Customer.class));
		return mapper.map(customer, CustomerDTO.class);
	}

	@Override
	public CustomerDTO update(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Customer by id %d not found", id)));
		mapper.typeMap(CustomerDTO.class, Customer.class).addMappings(mapper -> {
			mapper.skip(Customer::setId);
			mapper.skip(Customer::setContracts);
		}).map(customerDTO, customer);

		return mapper.map(customerRepository.save(customer), CustomerDTO.class);
	}

	@Override
	public Long delete(Long id) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found on :: " + id));
		customerRepository.delete(customer);
		return id;
	}

}
