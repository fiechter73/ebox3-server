package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
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
import com.ebox3.server.model.dto.CustomerDTO;
import com.ebox3.server.model.dto.OverviewDTO;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.CustomerRepository;
import com.ebox3.server.repo.EBoxRepository;

import com.ebox3.server.service.CustomerService1;

@Service
public class CustomerService1Impl extends HelpFunctions implements CustomerService1 {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	EBoxRepository eBoxRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Map<String,Object> getCustomersByList(String search, Boolean isNotCustomer, int page, int size) {

		List<OverviewDTO> listOfOvierviewDTO = new ArrayList<OverviewDTO>();
		List<Customer> custList = null;
		List<Customer> custFilterList = null;
		List<Customer> custListByEBoxNumber = new ArrayList<Customer>();
		Page<Customer> custPagable = null;
		Pageable requestedPage = PageRequest.of(page, size, Sort.by("name"));
		PagedListHolder<OverviewDTO> overviewDTOs = null;
		Map<String, Object> response = new HashMap<>();

		try {
			if (search == null) {
				if (isNotCustomer) {
					custPagable = customerRepository.findRetiredCustomersPageable(requestedPage);
				} else {
					custPagable = customerRepository.findCustomersPageable(requestedPage);
				}
				custPagable.getContent().forEach(customer -> {
					OverviewDTO ovDTO = mapper.map(customer, OverviewDTO.class);
					ovDTO.setBoxNumbers(customerRepository.boxNumbers(customer.getId()));
					listOfOvierviewDTO.add(ovDTO);
				});

				listOfOvierviewDTO.stream().sorted(Comparator.comparing(OverviewDTO::getName)).distinct()
						.collect(Collectors.toList());

				response.put("overviews", listOfOvierviewDTO);
				response.put("currentPage", custPagable.getNumber());
				response.put("totalItems", custPagable.getTotalElements());
				response.put("totalPages", custPagable.getTotalPages());

			} else {
				if (isNotCustomer) {
					custList = customerRepository.findCustomerFullSearch(search);

					custFilterList = custList.stream().filter(str -> str.getStatusText().equals("Kündigung"))
							.sorted(Comparator.comparing(Customer::getName)).distinct().collect(Collectors.toList());

				} else {
					custList = customerRepository.findCustomerFullSearch(search);
					if (custList == null) {
						return response;
					}

					if (NumberUtils.isCreatable(search)) {

						List<Ebox> eboxs = eBoxRepository.findByBoxnumber(Long.valueOf(search));
						eBoxRepository.findByBoxnumber(Long.valueOf(search));
						eboxs.forEach(ebox -> {
							custListByEBoxNumber.add(ebox.getContract().getCustomer());
						});
					}
					if (custListByEBoxNumber.size() > 0) {
						custList.addAll(custListByEBoxNumber);
					}

					custFilterList = custList.stream()
							.filter(str -> str.getStatusText().equals("Vertragsnehmer")
									|| str.getStatusText().equals("Offertnehmer"))
							.sorted(Comparator.comparing(Customer::getName)).distinct().collect(Collectors.toList());
				}

				custFilterList.forEach(customer -> {
					OverviewDTO ovDTO = mapper.map(customer, OverviewDTO.class);
					ovDTO.setBoxNumbers(customerRepository.boxNumbers(customer.getId()));
					listOfOvierviewDTO.add(ovDTO);
				});
				List<OverviewDTO> listOfOverviewSorted = listOfOvierviewDTO.stream()
						.sorted(Comparator.comparing(OverviewDTO::getName)).collect(Collectors.toList());

				overviewDTOs = new PagedListHolder<OverviewDTO>(listOfOverviewSorted);
				overviewDTOs.setPageSize(size);
				overviewDTOs.setPage(page);

				response.put("overviews", overviewDTOs.getPageList());
				response.put("currentPage", overviewDTOs.getPage());
				response.put("totalItems", overviewDTOs.getNrOfElements());
				response.put("totalPages", overviewDTOs.getPageCount());

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
	public CustomerDTO updatePartial(Long id,
			CustomerDTO customerDTO) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Customer by id %d not found", id)));
		customer.setAnrede(customerDTO.getAnrede());
		customer.setName(customerDTO.getName());
		customer.setVorname(customerDTO.getVorname());
		customer.setEmail(customerDTO.getEmail());
		customer.setTel1(customerDTO.getTel1());
		customer.setFirmenName(customerDTO.getFirmenName());
		customer.setFirmenAnschrift(customerDTO.getFirmenAnschrift());
		customer.setBemerkungen(customerDTO.getBemerkungen());
		return mapper.map(customerRepository.save(customer), CustomerDTO.class);
	}

}
